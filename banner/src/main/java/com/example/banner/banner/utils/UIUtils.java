package com.example.banner.banner.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2017/2/23.
 */

public class UIUtils {
    /**
     * 从资源文件获取一个view
     */
    public static View getInflaterView(Context context, int res) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(res, null);
        return view;
    }

    /**
     * 从资源文件获取一个view
     */
    public static View getInflaterView(Context context, int res, ViewGroup parent) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(res, parent);
        return view;
    }

    /**
     * 从资源文件获取一个view
     */
    public static View getInflaterView(Context context, int res, ViewGroup parent, boolean attachToRoot) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(res, parent, attachToRoot);
        return view;
    }
}
