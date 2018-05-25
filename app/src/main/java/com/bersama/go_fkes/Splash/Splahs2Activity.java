package com.bersama.go_fkes.Splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bersama.go_fkes.Helper.SplashPermissionActivity;
import com.bersama.go_fkes.MainActivity;
import com.bersama.go_fkes.R;

public class Splahs2Activity extends AppCompatActivity {

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splahs2);

        iv = (ImageView) findViewById(R.id.iv);
        Animation myanim = new AnimationUtils().loadAnimation(this,R.anim.mytransitions);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this, SplashPermissionActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5000) ;
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }

            }
        };
        timer.start();
    }
}
