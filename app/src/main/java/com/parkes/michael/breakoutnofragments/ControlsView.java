package com.parkes.michael.breakoutnofragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MPARKES on 2/23/2016.
 */
public class ControlsView extends View
{
    public ControlsView(Context context){
        super(context);
    }

    public ControlsView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public ControlsView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        int blue = Color.rgb(0, 0, 255);
        int radius;
        radius = 60;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);
        paint.setColor(blue);
        canvas.drawCircle(75, 60, radius, paint);
        canvas.drawCircle(1040, 60, radius, paint);
    }
}