package lyxs_916.view_utils.photo;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;


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
public class CustomZoomImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener,
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

    //-----------自由移动
    /**
     * 记录上次多点触控的数量
     */
    private int mLastPositerCount;
    private float mLastX;
    private float mLastY;
    private final int mTouchSlop;
    private boolean mIsCanDrag;
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;
    private boolean isAutoScale;
    //-----------双击放大缩小
    private GestureDetector mGestureDetecotor;

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
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetecotor = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (isAutoScale) {
                    return true;
                }
                float x = e.getX();
                float y = e.getY();
                if (getScale() < mMinScale) {
//                    mScaleMatrix.postScale(mMinScale / getScale(), mMinScale / getScale(), x, y);
//                    setImageMatrix(mScaleMatrix);
                    postDelayed(new AutoScaleRunnable(mMinScale,x,y),16);
                    isAutoScale=true;
                } else {
//                    mScaleMatrix.postScale(mInitScale / getScale(), mInitScale / getScale(), x, y);
//                    setImageMatrix(mScaleMatrix);
                    postDelayed(new AutoScaleRunnable(mInitScale,x,y),16);
                    isAutoScale=true;
                }
                return true;
            }
        });
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
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBoderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }

        return true;
    }

    /**
     * 获取图片放大以后的，宽和高，和四边的位置
     *
     * @return
     */
    private RectF getRectFMatrix() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    /**
     * 在缩放的时候进行边界控制，及位置控制
     */
    private void checkBoderAndCenterWhenScale() {
        RectF rectF = getRectFMatrix();
        float deltaX = 0;
        float deltaY = 0;
        float width = getWidth();
        float height = getHeight();
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.top < 0) {
                deltaY = height - rectF.bottom;
            }
        }

        //如果宽度或者高度小于控件的宽高，则让图片居中。
        if (rectF.width() < width) {
            deltaX = width / 2 - rectF.right + rectF.width() / 2;
        }

        if (rectF.height() < height) {
            deltaY = height / 2 - rectF.bottom + rectF.height() / 2;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
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
        if (mGestureDetecotor.onTouchEvent(event)) {
            return true;
        }
        //把事件传给手势处理
        mScaleGestureDetector.onTouchEvent(event);

        float x = 0;
        float y = 0;
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;

        if (mLastPositerCount != pointerCount) {
            mIsCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPositerCount = pointerCount;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!mIsCanDrag) {
                    mIsCanDrag = isMoveAction(dx, dy);
                }
                if (mIsCanDrag) {
                    RectF rectf = getRectFMatrix();
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        //如果宽度小于控件宽度，不允许横向移动
                        if (rectf.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        //如果高度小于控件高度，不允许纵向移到
                        if (rectf.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBoderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPositerCount = 0;
                break;
        }
        return true;
    }

    /**
     * 当移动时进行边界检查
     */
    private void checkBoderWhenTranslate() {
        RectF rectf = getRectFMatrix();
        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();

        if (rectf.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectf.top;
        }
        if (rectf.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectf.bottom;
        }
        if (rectf.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectf.left;
        }
        if (rectf.right < width && isCheckLeftAndRight) {
            deltaX = width - rectf.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 判断是不是move
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dy + dx * dy) > mTouchSlop;
    }

    /**
     * 说明:自动缩放
     * 作者： 杨阳; 创建于：  2017-07-03  15:23
     */
    private class AutoScaleRunnable implements Runnable {
        /**
         * 缩放的目标值
         */
        private float mTargetScalde;
        //缩放的中心点
        private float x;
        private float y;

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

        private float tmpScale;

        public AutoScaleRunnable(float mTargetScalde, float x, float y) {
            this.mTargetScalde = mTargetScalde;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScalde) {
                tmpScale = BIGGER;
            }
            if (getScale() > mTargetScalde) {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {
            //进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBoderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < mTargetScalde)
                    || (tmpScale < 1.0f && currentScale> mTargetScalde)) {
                postDelayed(this, 16);

                //设置我们的目标值
            } else {
                float scale = mTargetScalde / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBoderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale=false;
            }
        }
    }
}
