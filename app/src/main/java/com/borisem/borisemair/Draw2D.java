package com.borisem.borisemair;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

public class Draw2D extends View{

    private Paint mPaint = new Paint();
    private Rect mTextBoundRect = new Rect();
    private Context ctx;
    Paint paint;
    Bitmap bitmapSource;
    Bitmap bitmap;
    Matrix matrix;

    public Draw2D(Context context, AttributeSet attrs) {
                super(context, attrs);
                ctx=context;

                paint = new Paint();

                bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.ic_roundbutton);

                matrix = new Matrix();
                // matrix.postScale(10, 15);
                // matrix.postRotate(45);

                bitmap = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight(), matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /// canvas.drawARGB(80, 102, 204, 255);
        canvas.drawBitmap(bitmap, 0, 0, paint);



    }
}