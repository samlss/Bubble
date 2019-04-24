package me.samlss.bubble_demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import me.samlss.bubble.Bubble;

public class MainActivity extends AppCompatActivity {
    private Bubble mBubble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mBubble = new Bubble.Builder(this)
                .setAlpha(0.5f)
                .setBubbleColor(Color.YELLOW)
                .setDuration(3000)
                .setInterpolator(new LinearInterpolator())
                .setBubbleRadius(30)
                .build();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mBubble.shoot((int) event.getX(), (int) event.getY());
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mBubble.stop();
        return super.onKeyDown(keyCode, event);
    }
}
