package com.example.module.chat.communicate.base;

import com.example.module.chat.base.database.select.DataItem;

public interface ItemActionListener {
    void onItemClick(DataItem item, int position);  // 点击事件
    void onLongClick(DataItem item, int position);  // 长按事件
}