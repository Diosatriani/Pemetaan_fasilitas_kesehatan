package com.bersama.go_fkes.Helper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bersama.go_fkes.Activity.MapsActivity;
import com.bersama.go_fkes.MainActivity;
import com.bersama.go_fkes.R;

public class SplashPermissionActivity extends AppCompatActivity {

    private PermissionHelper permissionHelper;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_permission);

        permissionHelper = new PermissionHelper(this);

        checkAndRequestPermissions();
    }

    private boolean checkAndRequestPermissions() {

        permissionHelper.permissionListener(new PermissionHelper.PermissionListener() {
            @Override
            public void onPermissionCheckDone() {
                intent = new Intent(SplashPermissionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        permissionHelper.checkAndRequestPermissions();

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestCallBack(requestCode, permissions, grantResults);
    }
}

