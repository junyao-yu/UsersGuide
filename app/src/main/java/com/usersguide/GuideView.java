package com.usersguide;

import android.content.Context;
import android.content.res.TypedArray;
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
    private Bitmap bitmapBkg = null;
    private Bitmap bitmapFigure = null;
    private static final Xfermode sXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
    private int x, y, radius;

    /**默认间隔距离*/
    private float defaultSpacing = 0;
    /**间隔距离(文字与选中圈之间的padding)*/
    private float textCircleSpacing;

    public GuideView(Context context) {
        this(context, null);
    }

    public GuideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GuideView, defStyleAttr, 0);
        textCircleSpacing = attributes.getDimension(R.styleable.GuideView_spacing, defaultSpacing);
        attributes.recycle();
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

    public void setData(int x, int y, int radius) {
        this.x = (int) (x - textCircleSpacing);
        this.y = (int) (y - textCircleSpacing);
        this.radius = (int) (radius + 2 * textCircleSpacing);
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
        RectF localRectF = new RectF(x, y, x + radius, y + radius);
        canvas.drawOval(localRectF, paint);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int iLayer = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        bitmapBkg = drawBackground();
        canvas.drawBitmap(bitmapBkg, 0, 0, mPaint);

        resetPaint();

        bitmapFigure = drawFigure();
        canvas.drawBitmap(bitmapFigure, 0, 0, mPaint);

        canvas.restoreToCount(iLayer);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRecycle(bitmapBkg);
        isRecycle(bitmapFigure);
    }

    private void isRecycle(Bitmap bitmap) {
        if(!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
