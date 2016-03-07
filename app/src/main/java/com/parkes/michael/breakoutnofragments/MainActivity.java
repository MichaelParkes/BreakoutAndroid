package com.parkes.michael.breakoutnofragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //BreakoutView breakoutView = new BreakoutView(this);
        //setContentView(breakoutView);


        Display disp = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        int ScreenSizeX = size.x;
        int ScreenSizeY = size.y;

        Paddle Corner = new Paddle(ScreenSizeX,ScreenSizeY);
        
        ControlsView CV = (ControlsView) findViewById(R.id.Controls);
        ImageView LeftContrl = (ImageView) CV.findViewById(R.id.imgLeftBut);
        ImageView RightContrl = (ImageView) CV.findViewById(R.id.imgRightBut);

        PlayScreenView PV = (PlayScreenView) findViewById(R.id.PlayScreen);
        //Corner = (Paddle) findViewById(R.id.)


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
