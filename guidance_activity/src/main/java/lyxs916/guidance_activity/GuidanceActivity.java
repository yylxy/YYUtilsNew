package lyxs916.guidance_activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * 说明：引导页
 * 作者　　: 杨阳
 * 创建时间: 2017/1/14 9:43
 */
public class GuidanceActivity extends Activity {
    ViewPager viewpager;
    LinearLayout index;
    TextView enter;
    /**
     * ImageView 集合，会根据图片的集合的数量，新建本集合view的数量
     */
    List<ImageView> mViews = new ArrayList<>();
    /**
     * 图片的集合
     */
    List<Integer> mimageIds = new ArrayList<>();
    /**
     * 点击的回调
     */
    static OnClickListenerCallback mCallback;
    //主颜色
    int mColor = 0x16be32;

    /**
     * @param activity
     * @param color    主色
     * @param imageIds 图片id集合
     * @param callback 回调
     */
    public static void startUI(Activity activity, int color, ArrayList<Integer> imageIds, OnClickListenerCallback callback) {
        Intent intent = new Intent(activity, GuidanceActivity.class);
        intent.putExtra("color", color);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Bundle bundle = new Bundle();
        bundle.putSerializable("imageIds", imageIds);
        intent.putExtras(bundle);
        mCallback = callback;
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        parseIntent();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCallback = null;
    }


    public void parseIntent() {
        mColor = getIntent().getIntExtra("color", mColor);
        mimageIds = (List<Integer>) getIntent().getSerializableExtra("imageIds");
    }


    public void initView() {

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        index = (LinearLayout) findViewById(R.id.index);
        enter = (TextView) findViewById(R.id.enter);

        enter.setBackgroundColor(mColor);
        addView();
        viewpager.setAdapter(new GuidanceAdapter(mViews));
        changeColor(0);
        addListener();
        pressListener(enter);


    }

    /**
     * 添加控制的监听
     */
    private void addListener() {
        /**
         * viewPage 滑动的监听
         */
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 最后一页点击事件的监听
         */
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.clickCallback(true);
                    finish();
                }

            }
        });

    }


    /**
     * 添加指示器的view
     */
    private void addView() {
        for (int i = 0; i < mimageIds.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ImageView image = new ImageView(this);
            image.setImageResource(mimageIds.get(i));
            image.setLayoutParams(params);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mViews.add(image);

            LinearLayout.LayoutParams paramsIndes = new LinearLayout.LayoutParams(50, 20);
            paramsIndes.setMargins(50, 0, 0, 0);
            View view = new View(this);
            view.setBackgroundColor(0xff399be6);
            view.setLayoutParams(paramsIndes);
            index.addView(view);
        }

    }

    /**
     * 改变指示条颜色和隐藏显示进入的按钮的控制
     *
     * @param position
     */
    private void changeColor(int position) {
        for (int i = 0; i < mimageIds.size(); i++) {
            if (i == position) {
                index.getChildAt(i).setBackgroundColor(mColor);
            } else {
                index.getChildAt(i).setBackgroundColor(0xffeeeeee);
            }
            if (position == mViews.size() - 1) {
                enter.setVisibility(View.VISIBLE);
                scaleAnim(enter, 1f, 0.1f, 1000, null);
            } else {
                enter.setVisibility(View.GONE);
            }
        }
    }

    /**
     * view 按压动画
     *
     * @param view
     */
    public static void pressListener(final View view, final OnClickListenerCallback callback) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    scaleAnim(view, 0.9f, 1f, 500, null);
                }
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    scaleAnim(view, 1f, 0.9f, 500, callback);
                }

                return false;
            }
        });

    }


    public static void pressListener(final View view) {
        pressListener(view, null);

    }


    /**
     * 缩放动画
     *
     * @param view
     * @param scaleMax 结束比例
     * @param scaleMin 开始比例
     * @param duration 时间
     */
    public static void scaleAnim(final View view, float scaleMax, float scaleMin, int duration, final OnClickListenerCallback callback) {

        if (callback != null) {
            view.setEnabled(false);
        }

        PropertyValuesHolder scx = PropertyValuesHolder.ofFloat("scaleX", scaleMin, scaleMax);
        PropertyValuesHolder scY = PropertyValuesHolder.ofFloat("scaleY", scaleMin, scaleMax);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                view, scx, scY).setDuration(duration);


        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (callback != null) {
                    view.setEnabled(true);
                    callback.clickCallback(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });


        objectAnimator.start();


    }

    /**
     * 点击进入的回调
     */
    public interface OnClickListenerCallback {
        void clickCallback(boolean isOnClick);
    }

    /**
     * 说明：viewPage 的Adapter
     * 作者　　: 杨阳
     * 创建时间: 2017/1/14 11:28
     */

    class GuidanceAdapter extends PagerAdapter {
        private List<ImageView> views;


        public GuidanceAdapter(List<ImageView> views) {
            super();
            this.views = views;
        }


        @Override
        public int getCount() {
            return views.size();
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(views.get(position), 0);
            return views.get(position);
        }


    }
}
