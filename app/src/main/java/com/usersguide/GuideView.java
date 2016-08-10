package com.usersguide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Auth：yujunyao
 * Since: 2016/8/10 11:27
 * Email：yujunyao@yonglibao.com
 */
public class GuideView extends View{
    private Paint mPaint = new Paint();
    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);

    public GuideView(Context context) {
        this(context, null);
    }

    public GuideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint.setFilterBitmap(false);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void resetPaint() {
        mPaint.reset();
        mPaint.setFilterBitmap(false);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(sXfermode);
    }

    private Bitmap drawBackground() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.color_4d4d4d));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        return bitmap;
    }

    private Bitmap drawFigure() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        RectF localRectF = new RectF(100, 100, 500, 500);
        canvas.drawOval(localRectF, paint);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int iLayer = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(drawBackground(), 0, 0, mPaint);

        resetPaint();

        canvas.drawBitmap(drawFigure(), 0, 0, mPaint);

        canvas.restoreToCount(iLayer);
    }
}
