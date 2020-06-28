package com.borisem.borisemair;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
public class Draw2D extends View {

    private Paint mPaint = new Paint();
    private Rect mTextBoundRect = new Rect();

    public Draw2D(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.argb(255, 100, 100, 100));
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);


        RectF ring = new RectF(0f,0f,100f,100f);
        canvas.drawArc(ring, 0, 180, false, mPaint);


    }
}