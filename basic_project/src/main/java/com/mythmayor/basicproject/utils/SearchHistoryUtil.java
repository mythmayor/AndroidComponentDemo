package com.mythmayor.basicproject.utils;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.mythmayor.basicproject.BasicApplication;
import com.mythmayor.basicproject.bean.SearchHistoryBean;
import com.mythmayor.basicproject.utils.http.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mythmayor on 2020/7/15.
 * 搜索历史工具类
 */
public class SearchHistoryUtil {

    private final int MAX_COUNT = 10;//搜索历史最多存储条数

    private static SearchHistoryUtil ourInstance = new SearchHistoryUtil();

    public static SearchHistoryUtil getInstance() {
        return ourInstance;
    }

    private SearchHistoryUtil() {
    }

    public void add(SearchHistoryBean bean, String historyKey) {
        if (bean == null || TextUtils.isEmpty(bean.getName())) {
            return;
        }
        if (TextUtils.isEmpty(historyKey)) historyKey = PrefUtil.SP_SEARCH_HISTORY;
        String history = PrefUtil.getString(BasicApplication.getInstance().getContext(), historyKey, "");
        List<SearchHistoryBean> list;
        if (TextUtils.isEmpty(history)) {//搜索历史记录为空，直接存储搜索内容
            list = new ArrayList<>();
            list.add(bean);
        } else {//搜索历史记录不为空，判断条数如果小于10则添加该条
            list = HttpUtil.mGson.fromJson(history, new TypeToken<List<SearchHistoryBean>>() {
            }.getType());
            if (list != null && list.size() > 0) {
                if (list.contains(bean)) {//判断List是否包含该对象
                    list.remove(bean);
                } else {
                    if (list.size() < MAX_COUNT) {//List存储数量小于最大存储数

                    } else {
                        list.remove(list.size() - 1);
                    }
                }
                list.add(0, bean);
            }
        }
        String result = HttpUtil.mGson.toJson(list);
        PrefUtil.putString(BasicApplication.getInstance().getContext(), historyKey, result);
    }

    public List<SearchHistoryBean> get(String historyKey) {
        if (TextUtils.isEmpty(historyKey)) historyKey = PrefUtil.SP_SEARCH_HISTORY;
        String history = PrefUtil.getString(BasicApplication.getInstance().getContext(), historyKey, "");
        List<SearchHistoryBean> list = null;
        if (!TextUtils.isEmpty(history)) {
            list = HttpUtil.mGson.fromJson(history, new TypeToken<List<SearchHistoryBean>>() {
            }.getType());
        }
        return list;
    }

    public void clear(String historyKey) {
        if (TextUtils.isEmpty(historyKey)) historyKey = PrefUtil.SP_SEARCH_HISTORY;
        PrefUtil.putString(BasicApplication.getInstance().getContext(), historyKey, "");
    }

    private List<String> array2List(String[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        List<String> list = new ArrayList<>();
        //倒序展示，最新搜索的在前面
        for (int i = array.length - 1; i >= 0; i--) {
            String content = array[i];
            if (!TextUtils.isEmpty(content)) {
                list.add(content);
            }
        }
        return list;
    }
}
