package com.blend.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;

public class StatusBarUtils {

    /**
     * 设置状态栏颜色
     *
     * @param context 上下文，尽量使用Activity
     * @param color   状态栏颜色
     */
    public static void setColor(Context context, @ColorInt int color) {
        try {
            if (context instanceof Activity) {
                setColor(((Activity) context).getWindow(), color);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param window 窗口，可用于Activity和Dialog等
     * @param color  状态栏颜色
     */
    public static void setColor(@NonNull Window window, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(color);
            setTextDark(window, !isDarkColor(color));
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param context 上下文，尽量使用Activity
     */
    public static void setTransparent(Context context) {
        if (context instanceof Activity) {
            setTransparent(((Activity) context).getWindow());
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param window 窗口，可用于Activity和Dialog等
     */
    public static void setTransparent(@NonNull Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 设置状态栏是否为黑色文字
     *
     * @param context 上下文，尽量使用Activity
     * @param isDark  是否为黑色文字
     */
    public static void setTextDark(Context context, boolean isDark) {
        if (context instanceof Activity) {
            setTextDark(((Activity) context).getWindow(), isDark);
        }
    }

    /**
     * 设置状态栏是否为黑色文字
     *
     * @param window 窗口，可用于Activity和全屏Dialog
     * @param isDark 是否为黑色文字
     */
    private static void setTextDark(Window window, boolean isDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (isDark) {
                decorView.setSystemUiVisibility(systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 判断颜色是否为深色
     *
     * @param color 要判断的颜色
     * @return 是否为深色
     */
    public static boolean isDarkColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) < 0.5;
    }
}
