package com.bersama.go_fkes.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bersama.go_fkes.R;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        iv1 = (ImageView) findViewById(R.id.bg);
        Animation myanim = new AnimationUtils().loadAnimation(this, R.anim.mytransitions);
        iv1.startAnimation(myanim);
        final Intent i = new Intent(this, Splahs2Activity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }

            }
        };
        timer.start();
    }
}
