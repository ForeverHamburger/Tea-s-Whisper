package com.example.module.chat.communicate.base;

import com.example.module.chat.base.database.select.DataItem;

public interface ItemActionListener<T> {
    void onItemClick(T item, int position);  // 点击事件
    void onLongClick(T item, int position);  // 长按事件
}