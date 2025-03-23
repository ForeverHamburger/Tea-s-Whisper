package com.xuptggg.search.base;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchHistoryUtils {
    private static final String MMKV_ID = "Tea's Whisper.Search History";
    private static final String KEY_SEARCH_HISTORY = "Search History";
    private static LinkedList<String> searchHistoryCache;
    static Gson gson = new Gson();
    private static MMKV mmkv = MMKV.mmkvWithID(MMKV_ID);
    public static List<String> getSearchHistory() {
        if (searchHistoryCache == null) {
            loadSearchHistoryFromMMKV();
        }
        return new ArrayList<>(searchHistoryCache);
    }

    public static void addSearchHistory(String searchText) {
        if (searchHistoryCache == null) {
            loadSearchHistoryFromMMKV();
        }
        searchHistoryCache.remove(searchText); // 移除已存在的元素，保证新元素在顶部
        searchHistoryCache.add(0, searchText);
        saveSearchHistoryToMMKV();
    }

    private static void loadSearchHistoryFromMMKV() {
        String jsonString = mmkv.getString(KEY_SEARCH_HISTORY, null);
        if (jsonString != null) {
            searchHistoryCache = new LinkedList<>(gson.fromJson(jsonString, new ArrayList<String>().getClass()));
        } else {
            searchHistoryCache = new LinkedList<>();
        }
    }

    private static void saveSearchHistoryToMMKV() {
        String jsonString = gson.toJson(new ArrayList<>(searchHistoryCache));
        mmkv.putString(KEY_SEARCH_HISTORY, jsonString);
    }
}