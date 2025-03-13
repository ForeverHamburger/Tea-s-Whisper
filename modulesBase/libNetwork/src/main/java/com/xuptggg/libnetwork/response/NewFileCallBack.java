package com.xuptggg.libnetwork.response;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;

import com.xuptggg.libnetwork.exception.MyHttpException;
import com.xuptggg.libnetwork.listener.DownloadListener;
import com.xuptggg.libnetwork.listener.MyDataHandle;

import okhttp3.*;
import java.io.*;
import java.lang.ref.WeakReference;

/**
 * 增强型文件下载回调处理
 * 改进特性：
 * 1. 内存泄漏防护（弱引用+静态Handler）
 * 2. 精确错误类型分类
 * 3. 进度更新节流控制
 * 4. 自动资源管理
 * 5. 路径安全处理
 */
public class NewFileCallBack implements Callback {

    // region 常量
    private static final int PROGRESS_WHAT = 0x1001;
    private static final long UPDATE_INTERVAL = 1000L; // 进度更新间隔
    private static final int BUFFER_SIZE = 8192; // 8KB缓冲区
    // endregion

    // region 成员变量
    private final SafeHandler safeHandler;
    private final WeakReference<DownloadListener> listenerRef;
    private final String filePath;
    private long lastUpdateTime;
    private volatile long currentBytes; // 当前下载量（支持断点续传）
    // endregion

    // region 初始化
    public NewFileCallBack(MyDataHandle handle) {
        this.listenerRef = new WeakReference<>((DownloadListener) handle.mListener);
        this.filePath = handle.mSource;
        this.safeHandler = new SafeHandler(Looper.getMainLooper(), (DownloadListener) handle.mListener);
    }
    // endregion

    // region 网络回调
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        notifyFailure(DownloadError.NETWORK_FAILURE, e.getMessage());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        try {
            processResponse(response);
        } catch (DownloadException e) {
            notifyFailure(e.errorType, e.getMessage());
        }
    }
    // endregion

    // region 核心处理
    private void processResponse(Response response) throws DownloadException {
        validateResponse(response);

        try (InputStream input = response.body().byteStream();
             FileOutputStream output = createFileStream()) {

            long totalBytes = response.body().contentLength() + currentBytes;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                checkStorageSpace(bytesRead);
                output.write(buffer, 0, bytesRead);
                currentBytes += bytesRead;
                throttleProgressUpdate(totalBytes);
            }

            notifySuccess();
        } catch (IOException e) {
            throw new DownloadException(DownloadError.IO_FAILURE, e.getMessage());
        }
    }

    private FileOutputStream createFileStream() throws DownloadException {
        try {
            File targetFile = prepareFile();
            return new FileOutputStream(targetFile, currentBytes > 0);
        } catch (IOException e) {
            throw new DownloadException(DownloadError.FILE_CREATE_FAIL, "文件创建失败: " + e.getMessage());
        }
    }

    private File prepareFile() throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IOException("目录创建失败: " + parent.getAbsolutePath());
        }
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("文件创建失败: " + filePath);
        }
        return file;
    }
    // endregion

    // region 辅助方法
    private void validateResponse(Response response) throws DownloadException {
        if (response == null || !response.isSuccessful() || response.body() == null) {
            throw new DownloadException(DownloadError.INVALID_RESPONSE, "无效响应");
        }
    }

    private void checkStorageSpace(int required) throws DownloadException {
        File file = new File(filePath);
        if (file.getFreeSpace() < required) {
            throw new DownloadException(DownloadError.STORAGE_FULL, "存储空间不足");
        }
    }

    private void throttleProgressUpdate(long total) {
        long now = System.currentTimeMillis();
        if (now - lastUpdateTime > UPDATE_INTERVAL || total == currentBytes) {
            int progress = total > 0 ? (int) (currentBytes * 100 / total) : 0;
            safeHandler.sendMessage(safeHandler.obtainMessage(PROGRESS_WHAT, progress));
            lastUpdateTime = now;
        }
    }
    // endregion

    // region 通知方法
    private void notifyFailure(DownloadError error, String detail) {
        safeHandler.post(() -> {
            DownloadListener listener = listenerRef.get();
            if (listener != null) {
                listener.onFailure(new MyHttpException(error.code, detail != null ? detail : error.message));
            }
        });
    }

    private void notifySuccess() {
        safeHandler.post(() -> {
            DownloadListener listener = listenerRef.get();
            if (listener != null) {
                listener.onSuccess(new File(filePath));
            }
        });
    }
    // endregion

    // region 内部组件
    private static class SafeHandler extends Handler {
        private final WeakReference<DownloadListener> listenerRef;

        SafeHandler(Looper looper, DownloadListener listener) {
            super(looper);
            this.listenerRef = new WeakReference<>(listener);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == PROGRESS_WHAT) {
                DownloadListener listener = listenerRef.get();
                if (listener != null) {
                    listener.onProgress((int) msg.obj);
                }
            }
        }
    }

    private static class DownloadException extends Exception {
        final DownloadError errorType;

        DownloadException(DownloadError errorType, String message) {
            super(message);
            this.errorType = errorType;
        }
    }

    public enum DownloadError {
        NETWORK_FAILURE(1001, "网络连接失败"),
        IO_FAILURE(1002, "IO操作异常"),
        FILE_CREATE_FAIL(1003, "文件创建失败"),
        INVALID_RESPONSE(1004, "无效响应"),
        STORAGE_FULL(1005, "存储空间不足");

        final int code;
        final String message;

        DownloadError(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}