package com.parkes.michael.breakoutnofragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by MParkes on 2/24/2016.
 */
public class PlayScreenView extends View {


    public PlayScreenView(Context context){
        super(context);

    }

    public PlayScreenView(Context context, AttributeSet attrs){
        super(context, attrs);

    }

    public PlayScreenView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        int blue = Color.rgb(0, 0, 255);
        int green = Color.rgb(0, 255, 0);
        int red = Color.rgb(255, 0, 0);
        int radius;
        radius = 60;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawPaint(paint);

        paint.setColor(blue);

        //canvas.drawRect(400, 200, 300, 177, paint);

        //left: distance of the left side of rectangular from left side of canvas.

        //top:Distance of bottom side of rectangular from the top side of canvas

        //right:distance of the right side of rectangular from left side of canvas.
        //bottom: Distance of the top side of rectangle from top side of canvas.

        Paddle P = new Paddle(400,200);
        canvas.drawRect(P.getRect(),paint);
    }
}




