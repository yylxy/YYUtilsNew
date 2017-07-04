package lyxs_916.view_utils.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import lyxs_916.view_utils.R;


/**
 * 说明:图片的缩放
 * 作者： 杨阳; 创建于：  2017-06-27  16:45
 */
public class PhotoActivity extends Activity {
    private ViewPager viewPager;
    private int[] pictures = new int[]{R.mipmap.aa, R.mipmap.bb, R.mipmap.ic_launcher};
    private ImageView[] imageViews = new ImageView[pictures.length];

    private int mLastCount = 0;

    public static void starUi(Activity context) {
        Intent intent = new Intent(context, PhotoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photot);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                CustomZoomImageView imageView = new CustomZoomImageView(getApplicationContext());
                imageView.setImageResource(pictures[position]);

                container.addView(imageView);
                imageViews[position] = imageView;


                Log.e("AAAA", "instantiateItem--" + position);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView(imageViews[position]);
                Log.e("AAAA", "destroyItem--" + position);
            }

            @Override
            public int getCount() {
//                Log.e("AAAA","getCount");
                return imageViews.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                Log.e("AAAA", "isViewFromObject");
                int currentItem = viewPager.getCurrentItem();
                if (mLastCount != currentItem) {
                    ((CustomZoomImageView) imageViews[mLastCount]).aaa() ;
                    mLastCount = currentItem;
                }


                return view == object;
            }
        });

    }
}
