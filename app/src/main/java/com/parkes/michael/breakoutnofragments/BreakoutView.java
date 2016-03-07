package com.parkes.michael.breakoutnofragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by MPARKES on 3/3/2016.
 */
public class BreakoutView extends SurfaceView implements Runnable {

    Thread CurrentThread = null;
    public SurfaceHolder GameHolder;
    boolean paused = true;
    int ScreenSizeX;
    int ScreenSizeY;
    volatile boolean playing;
    Paddle P;
    Ball B;
    Canvas canvas;
    Paint paint;
    Brick[] bricks = new Brick[200];
    int numBricks = 0;

    long fps;
    long timeThisFrame;

    int score = 0;
    int lives = 3;

    public BreakoutView(Context context) {
        super(context);
        GameHolder = getHolder();

        paint = new Paint();

        /*Display disp = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        ScreenSizeX = size.x;
        ScreenSizeY = size.y;*/
        P = new Paddle(ScreenSizeX, ScreenSizeY);
        B = new Ball(ScreenSizeX, ScreenSizeY);

        createBricksAndRestart();
    }


    public void createBricksAndRestart() {
        B.reset(ScreenSizeX, ScreenSizeY);
        int brickWidth = ScreenSizeX / 8;
        int brickHeight = ScreenSizeY / 10;

        int numBricks = 0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 3; row++) {
                bricks[numBricks] = new Brick(row, col, brickWidth, brickHeight);
                numBricks++;
            }
        }
        if (lives == 0) {
            score = 0;
            lives = 3;
        }
    }

    @Override
    public void run()
    {
        while (playing)
        {
            long startFrameTime = System.currentTimeMillis();
            if (!paused)
            {
                update();
            }
            draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1)
            {
                fps = 1000 / timeThisFrame;
            }

        }

    }

    public void update()
    {
        P.update(fps);
        B.update(fps);

        for (int i = 0; i < numBricks; i++)
        {
            if (bricks[i].getVisibility())
            {
                if (RectF.intersects(bricks[i].getRect(), B.getRect()))
                {
                    bricks[i].setInvisible();
                    B.reverseYVelocity();
                    score = score + 10;
                }
            }
        }

        if (RectF.intersects(P.getRect(), B.getRect()))
        {
            B.setRandomXVelocity();
            B.reverseYVelocity();
            B.clearObstacleY(P.getRect().top - 2);

        }
        if (B.getRect().bottom > ScreenSizeY)
        {
            B.reverseYVelocity();
            B.clearObstacleY(ScreenSizeY - 2);
            lives--;
            if (lives == 0)
            {
                paused = true;
                createBricksAndRestart();
            }
        }
        if (RectF.intersects(P.getRect(), B.getRect()))
        {
            B.setRandomXVelocity();
            B.reverseYVelocity();
            B.clearObstacleY(P.getRect().top - 2);
        }

        if (B.getRect().bottom > ScreenSizeY)
        {
            B.reverseYVelocity();
            B.clearObstacleY(ScreenSizeY - 2);
            lives--;
            if (lives == 0)
            {
                paused = true;
                createBricksAndRestart();
            }
        }

        if (B.getRect().top < 0)
        {
            B.reverseYVelocity();
            B.clearObstacleY(12);
        }

        if (B.getRect().left < 0)
        {
            B.reverseXVelocity();
            B.clearObstacleX(2);
        }

        if (B.getRect().right > ScreenSizeX - 10)
        {
            B.reverseXVelocity();
            B.clearObstacleX(ScreenSizeX - 22);
        }

        if (score == numBricks * 10)
        {
            paused = true;
            createBricksAndRestart();
        }
    }//end of update

    public void draw() {
        // Make sure our drawing surface is valid or we crash
        if (GameHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = GameHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the paddle
            canvas.drawRect(P.getRect(), paint);

            // Draw the ball
            canvas.drawRect(B.getRect(), paint);

            // Change the brush color for drawing
            paint.setColor(Color.argb(255, 249, 129, 0));

            // Draw the bricks if visible
            for (int i = 0; i < numBricks; i++) {
                if (bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paint);
                }
            }

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Draw the score
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + "   Lives: " + lives, 10, 50, paint);

            // Has the player cleared the screen?
            if (score == numBricks * 10) {
                paint.setTextSize(90);
                canvas.drawText("YOU HAVE WON!", 10, ScreenSizeY / 2, paint);
            }

            // Has the player lost?
            if (lives <= 0) {
                paint.setTextSize(90);
                canvas.drawText("YOU HAVE LOST!", 10, ScreenSizeY / 2, paint);
            }
            // Draw everything to the screen
            GameHolder.unlockCanvasAndPost(canvas);
        }
    }


    public void pause() {
        playing = false;
        try {
            CurrentThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }
    }

    public void resume() {
        playing = true;
        CurrentThread = new Thread(this);
        CurrentThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                paused = false;
                if (motionEvent.getX() > ScreenSizeX / 2) {
                    P.setMovementState(P.RIGHT);
                } else {
                    P.setMovementState(P.LEFT);
                }
                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                P.setMovementState(P.STOPPED);
                break;
        }

        return true;
    }//end Ontouch

}//end of BreakoutView
