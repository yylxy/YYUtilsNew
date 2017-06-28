package lyxs_916.view_utils.photo;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


/**
 * 说明：缩放的ImageView
 * 作者　　: 杨阳
 * 创建时间: 2017/1/24 14:28
 * <p>
 * 需要用到的知识点
 * 1.Matrix
 * 2.ScaleGestureDetector
 * 3.事件分发机制
 * -------------------------
 * 复写ImageView
 * <p>
 * 实现方法：
 * ViewTreeObserver.OnGlobalLayoutListener//布局生成后的监听
 * ScaleGestureDetector.OnScaleGestureListener//手势的监听
 */
public class CustomZoomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener,
        View.OnTouchListener {
    /**
     * 第一次加载
     */
    private boolean mOnce;
    /**
     * 缩放-初始化值
     */
    private float mInitScale;
    /**
     * 缩放-双击时到达时的值
     */
    private float mMinScale;
    /**
     * 缩放-最大值
     */
    private float mMaxScale;

    /**
     * 的矩阵类持有3 x3矩阵转换坐标。
     *
     * @param context
     */
    private Matrix mScaleMatrix;
    /**
     * 用户多点触控比例
     */
    private ScaleGestureDetector mScaleGestureDetector;

    public CustomZoomImageView(Context context) {
        this(context, null);
    }

    public CustomZoomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomZoomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(this);
    }

    //注册
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    //移除
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 获取ImageView加载完成后的图片
     */
    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            //得到控件的宽和高
            int width = getWidth();
            int height = getHeight();
            //得到图片，以及宽和高
            Drawable d = getDrawable();
            if (d == null)
                return;
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            //计算图片的缩放比例
            float scale = 1;
            //如果图片的宽度大于控件的宽度，但是图片的高度小于控件的高度，将图片缩小。
            if (dw > width && dh < height) {
                scale = width * 1.0F / dw;
            }
            //如果图片的宽度小于控件的宽度，但是图片的高度大于控件的高度，将图片缩小。
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }
            //如果图片宽高都大于或都小于，将图片缩放到控件内部
            if ((dw > width && dh > height) || (dw < width && dh < height)) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }
            //得到初始化时缩放的比例
            mInitScale = scale;
            mMinScale = mInitScale * 2;
            mMaxScale = mInitScale * 4;

            //将图片移动到控件的中心位置
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;

        }
    }

    /**
     * 获取当前图片的缩放比例
     *
     * @return
     */
    private float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];

    }

    /**
     * 手势的监听
     *
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //得到缩放的比例
        float scaleFactor = detector.getScaleFactor();
        float scale = getScale();

        if (getDrawable() == null) return true;

        //缩放的范围
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scale = mMaxScale / scale;
            }

            mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
            setImageMatrix(mScaleMatrix);
        }

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * 触控的监听
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //把事件传给手势处理
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
