package com.xuptggg.libnetwork.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.xuptggg.libnetwork.exception.MyHttpException;
import com.xuptggg.libnetwork.listener.DownloadListener;
import com.xuptggg.libnetwork.listener.MyDataHandle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * • 文件下载回调处理类
 * • 功能：处理文件下载的进度回调、结果通知、异常处理，支持跨线程更新UI
 */
public class FileCallBack implements Callback {
    // 网络错误码（非HTTP协议错误）
    protected final int NETWORK_ERROR = -1;
    // 本地IO错误码
    protected final int IO_ERROR = -2;
    protected final int INVALID_RESPONSE = -3;
    // 空错误信息
    protected final String EMPTY_MSG = "Invalid server response";

    // 进度消息标识（用于Handler消息机制）
    private static final int PROGRESS_MESSAGE = 0x01;

    // 主线程Handler（用于跨线程更新UI）
    private Handler mDeliveryHandler;

    // 下载进度监听接口
    private DownloadListener mListener;

    // 文件存储路径
    private String mFilePath;

    // 当前下载进度百分比
    private int mProgress;

    public FileCallBack(MyDataHandle handle) {
        this.mListener = (DownloadListener) handle.mListener;
        this.mFilePath = handle.mSource;

        // 初始化主线程Handler
        this.mDeliveryHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == PROGRESS_MESSAGE) {// 触发进度更新回调
                    mListener.onProgress((int) msg.obj);
                }
            }
        };
    }

    /**
     * ◦ 网络请求失败回调
     * ◦ （在后台线程执行）
     */
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        // 切换到主线程通知失败
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new MyHttpException(NETWORK_ERROR, e));
            }
        });
    }

    /**
     * ◦ 收到响应回调
     * ◦ （在后台线程执行）
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        // 处理响应数据并生成文件
        final File file = handleResponse(response);

        // 切换到主线程通知结果
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (file != null) {
                    mListener.onSuccess(file);
                } else {
                    mListener.onFailure(new MyHttpException(IO_ERROR, EMPTY_MSG));
                }
            }
        });
    }

    /**
     * ◦ 处理响应数据核心方法
     * ◦ 功能：
     * ◦ 1. 创建本地文件路径
     * ◦ 2. 将输入流写入文件
     * ◦ 3. 计算并更新下载进度
     * <p>
     * ◦ @param response 网络响应对象
     * ◦ @return 成功写入的文件对象，失败返回null
     */
    private File handleResponse(Response response) {
        if (response == null || response.body() == null) {
            mListener.onFailure(new MyHttpException(INVALID_RESPONSE, EMPTY_MSG));
            return null;
        }
        InputStream inputStream = null;
        File file = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[2048]; // 2KB缓冲区
        int length;
        int currentLength = 0; // 当前已下载字节数
        double sumLength;      // 总字节数

        try {
            // 检查并创建本地文件路径
            checkLocalFilePath(mFilePath);
            file = new File(mFilePath);
            fos = new FileOutputStream(file);

            // 获取响应体数据流
            inputStream = response.body().byteStream();
            sumLength = response.body().contentLength();

            // 分段读取数据
            while ((length = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
                currentLength += length;

                // 计算下载进度百分比
                mProgress = (int) (currentLength / sumLength * 100);

                // 发送进度更新消息
                mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
            }

            // 确保缓冲区数据写入磁盘
            fos.flush();
        } catch (Exception e) {
            file = null; // 发生异常时标记为失败
        } finally {
            // 资源释放
            try {
                if (fos != null) fos.close();
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * ◦ 创建本地文件存储路径
     * ◦ 功能：
     * ◦ 1. 检查父目录是否存在，不存在则创建
     * ◦ 2. 检查目标文件是否存在，不存在则创建
     * <p>
     * ◦ @param localFilePath 完整文件路径（包含文件名）
     */
    private void checkLocalFilePath(String localFilePath) {
        // 提取父目录路径
        File path = new File(localFilePath.substring(0,
                localFilePath.lastIndexOf("/") + 1));

        // 创建父目录（如果不存在）
        if (!path.exists()) {
            path.mkdirs();
        }

        // 创建目标文件（如果不存在）
        File file = new File(localFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}