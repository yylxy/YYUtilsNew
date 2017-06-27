package com.example.banner.banner.holder;

/**
 * Created by Sai on 15/12/14.
 *
 * @param <T> 任何你指定的对象
 */

import android.content.Context;
import android.view.View;

import com.example.banner.banner.ConvenientBanner;


public interface Holder<T> {
    View createView(ConvenientBanner banner, Context context);

    void UpdateUI(Context context, int position, T data);
}