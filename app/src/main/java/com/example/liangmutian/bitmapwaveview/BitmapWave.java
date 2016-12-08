package com.example.liangmutian.bitmapwaveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by lmt on 16/12/8.
 */

public class BitmapWave extends View {
    private PorterDuffXfermode mMode3 = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);//透明出来
    //private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.XOR);//红色覆盖
    private PorterDuffXfermode mMode2 = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);//浸泡感觉
    private PorterDuffXfermode mMode1 = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);//重叠部分消失 可以表示反向加载
    private PorterDuffXfermode mMode0 = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);//基本波浪


    // private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);//
    // private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);//
    // private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);//
    private Paint mWavePaint;
    private Paint bitmapPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;

    private Path mPath;
    private int mOffset;
    ValueAnimator animator;

    private int mCenterY;
    private int mWaveCount;
    //    背景图
    Bitmap bitmap;
    //浪宽
    private int mWaveLength = 700;
    //波浪比例(高度)
    private int progerss = 50;
    //波浪振幅高
    private int mWaveHeight = 80;
    //波浪比例
    private float waveBit = 1 / 4f;//1:1
    //波浪颜色
    private int mWavePaintColor;


    public BitmapWave(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.bitmapWave);
        mWavePaintColor = ta.getColor(R.styleable.bitmapWave_overColor, Color.RED);
        Drawable d = ta.getDrawable(R.styleable.bitmapWave_backbitmap);

        BitmapDrawable bd = (BitmapDrawable) d;
        bitmap = bd.getBitmap();
        ta.recycle();
        init();
    }

    public BitmapWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BitmapWave(Context context) {
        super(context);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = widthSize;
        mHeight = heightSize;

        setMeasuredDimension(mWidth, mHeight);
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mWaveCount = (int) Math.round(mHeight / mWaveLength + 1.5);
        mCenterY = (mHeight / 100) * (100 - progerss);
        mWaveLength = 700;
        bitmap = changeSize(bitmap);
    }


    public void setColor(int c) {
        mWavePaint.setColor(c);
    }

    public void setProgerss(int c) {
        this.progerss = c;
    }


    private void init() {
        mWavePaint = new Paint();
        bitmapPaint = new Paint();
        mWavePaint.setColor(mWavePaintColor);
        mPath = new Path();

        bitmapPaint.setAntiAlias(true);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setXfermode(mMode0);


        ValueAnimator animator = ValueAnimator.ofInt(0, mWaveLength);
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBitmap.eraseColor(Color.parseColor("#00000000"));


        mPath.reset();
        mPath.moveTo(-mWaveLength + mOffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {//50是波纹的大小

            mPath.quadTo((-mWaveLength * (1 - waveBit)) + (i * mWaveLength) + mOffset, mCenterY + mWaveHeight,
                    (-mWaveLength / 2) + (i * mWaveLength) + mOffset, mCenterY);

            mPath.quadTo((-mWaveLength * waveBit) + (i * mWaveLength) + mOffset, mCenterY - mWaveHeight, i * mWaveLength + mOffset, mCenterY);
        }

        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();

        if (bitmap != null)
            mCanvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
        mCanvas.drawPath(mPath, mWavePaint);

        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    public void setMode(int c) {

        switch (c) {
            case 0:
                mWavePaint.setXfermode(mMode0);
                break;
            case 1:
                mWavePaint.setXfermode(mMode1);
                break;
            case 2:
                mWavePaint.setXfermode(mMode2);
                break;
            case 3:
                mWavePaint.setXfermode(mMode3);
                break;


        }

    }

    public Bitmap changeSize(Bitmap bm) {
        int width = bm.getWidth();

        int height = bm.getHeight();
        float scaleWidth = ((float) mWidth) / width;

        float scaleHeight = ((float) mHeight) / height;
        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,

                true);
        return newbm;

    }
}
