package lyxs916.ui_utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;


/**
 * 说明：Snackbar 工具类
 * 作者　　: 杨阳
 * 创建时间: 2017/1/10 9:41
 */
public class SnackbarUtils {
    /**
     * 红色
     */
    public static int red = 0xfff44336;
    /**
     * 绿色
     */
    public static int green = 0xff4caf50;
    /**
     * 蓝色
     */
    public static int blue = 0xff2195f3;
    /**
     * 橘色
     */
    public static int orange = 0xffffc107;

    public static Snackbar show(final Activity activity, String content, int backgroundColor, int time, boolean showFinish, final Snackbar.Callback callback) {

        final Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), content, Snackbar.LENGTH_SHORT);
        //更改背景
        View snackBarView = snackbar.getView();
        if (snackBarView != null) {
            snackBarView.setBackgroundColor(backgroundColor);
        }
        //是否显示关闭
        if (showFinish) {
            snackbar.setActionTextColor(0xffffffff)
                    .setAction("关闭", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });
        }

        //进行回调
        if (callback != null) {
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    callback.onDismissed(transientBottomBar, event);
                }

                @Override
                public void onShown(Snackbar sb) {
                    super.onShown(sb);
                    callback.onShown(sb);
                }
            });

        }
        snackbar.setDuration(time).show();
        return snackbar;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 各种状态的提示
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 蓝色的提示
     *
     * @param activity 显示依赖的对象
     * @param content  显示的内容
     * @return
     */
    public static Snackbar showHint(Activity activity, String content) {
        return show(activity, content, blue, Snackbar.LENGTH_SHORT, false, null);
    }

    /**
     * 黄色的提示
     *
     * @param activity 显示依赖的对象
     * @param content  显示的内容
     * @return
     */
    public static Snackbar showWarning(Activity activity, String content) {
        return show(activity, content, orange, Snackbar.LENGTH_SHORT, false, null);
    }

    /**
     * 红色的提示
     *
     * @param activity 显示依赖的对象
     * @param content  显示的内容
     * @return
     */
    public static Snackbar showError(Activity activity, String content) {
        return show(activity, content, red, Snackbar.LENGTH_SHORT, false, null);
    }

    /**
     * 绿色的提示
     *
     * @param activity 显示依赖的对象
     * @param content  显示的内容
     * @return
     */
    public static Snackbar showSucceed(Activity activity, String content) {
        return show(activity, content, green, Snackbar.LENGTH_SHORT, false, null);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 基本色的提示
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Snackbar showGreen(Activity activity, String content) {
        return show(activity, content, green, Snackbar.LENGTH_SHORT, false, null);
    }

    public static Snackbar showRed(Activity activity, String content) {
        return show(activity, content, red, Snackbar.LENGTH_SHORT, false, null);
    }

    public static Snackbar showBlue(Activity activity, String content) {
        return show(activity, content, blue, Snackbar.LENGTH_SHORT, false, null);
    }

    public static Snackbar showOrange(Activity activity, String content) {
        return show(activity, content, orange, Snackbar.LENGTH_SHORT, false, null);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 基本背景色的自定义时显示间方法
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Snackbar showGreenTime(Activity activity, String content, int time) {
        return show(activity, content, green, time, false, null);
    }

    public static Snackbar showRedTime(Activity activity, String content, int time) {
        return show(activity, content, red, time, false, null);
    }

    public static Snackbar showBlueTime(Activity activity, String content, int time) {
        return show(activity, content, blue, time, false, null);
    }

    public static Snackbar showOrangeTime(Activity activity, String content, int time) {
        return show(activity, content, orange, time, false, null);
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                 带回调的显示法
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Snackbar showRedCallback(Activity activity, String content, Snackbar.Callback callback) {
        return show(activity, content, red, Snackbar.LENGTH_SHORT, false, callback);
    }


    public static Snackbar showGreenCallback(Activity activity, String content, Snackbar.Callback callback) {
        return show(activity, content, green, Snackbar.LENGTH_SHORT, false, callback);
    }


    public static Snackbar showBlueCallback(Activity activity, String content, Snackbar.Callback callback) {
        return show(activity, content, blue, Snackbar.LENGTH_SHORT, false, callback);
    }


    public static Snackbar showOrangeCallback(Activity activity, String content, Snackbar.Callback callback) {
        return show(activity, content, orange, Snackbar.LENGTH_SHORT, false, callback);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                显示关闭提示
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Snackbar showFinishRed(Activity activity, String content) {
        return show(activity, content, red, Snackbar.LENGTH_SHORT, true, null);
    }

    public static Snackbar showFinishGreen(Activity activity, String content) {
        return show(activity, content, green, Snackbar.LENGTH_SHORT, true, null);
    }

    public static Snackbar showFinishOrange(Activity activity, String content) {
        return show(activity, content, orange, Snackbar.LENGTH_SHORT, true, null);
    }

    public static Snackbar showFinishBlue(Activity activity, String content) {
        return show(activity, content, blue, Snackbar.LENGTH_SHORT, true, null);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                各种自定义的显示
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 自定义颜色、时间
     *
     * @param activity
     * @param content
     * @param color
     * @param time
     * @return
     */
    public static Snackbar showCustom(Activity activity, String content, int color, int time) {
        return show(activity, content, color, time, false, null);
    }


    /**
     * 自定义 颜色和回调
     *
     * @param activity
     * @param content
     * @param color
     * @param callback
     * @return
     */
    public static Snackbar showCustom(Activity activity, String content, int color, Snackbar.Callback callback) {
        return show(activity, content, color, Snackbar.LENGTH_SHORT, false, callback);
    }

    /**
     * 自定义 颜色，时间、显示关闭
     *
     * @param activity
     * @param content
     * @param color
     * @param time
     * @return
     */
    public static Snackbar showCustom(Activity activity, String content, int color, int time, boolean showFinish) {
        return show(activity, content, color, time, true, null);
    }

    /**
     * 自定义 颜色，关闭和回调
     *
     * @param activity
     * @param content
     * @param color
     * @param showFinish
     * @param callback
     * @return
     */
    public static Snackbar showCustom(Activity activity, String content, int color, boolean showFinish, Snackbar.Callback callback) {
        return show(activity, content, color, Snackbar.LENGTH_SHORT, showFinish, callback);
    }

    /**
     * 自定义时 颜色，时间、显示关闭，带回调方法
     *
     * @param activity
     * @param content
     * @param color
     * @param time
     * @param showFinish
     * @param callback
     * @return
     */
    public static Snackbar showCustom(Activity activity, String content, int color, int time, boolean showFinish, Snackbar.Callback callback) {
        return show(activity, content, color, time, showFinish, callback);
    }


}
