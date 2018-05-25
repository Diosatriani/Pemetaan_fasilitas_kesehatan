package com.bersama.go_fkes.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bersama.go_fkes.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView namatxt, alamattxt, lattxt, lngtxt, jenistxt;
    private Toolbar mActionToolbar;
    private Button btn_notelp;
    private ImageView imaget;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mActionToolbar = (Toolbar) findViewById(R.id.tabs_detail);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Informasi Detail");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        imaget = (ImageView) findViewById(R.id.imageView);
        namatxt = (TextView) findViewById(R.id.namat);
        btn_notelp = (Button) findViewById(R.id.btn_call);
        jenistxt = (TextView) findViewById(R.id.jenist);


        final Intent intent = this.getIntent();


        String image = intent.getExtras().getString("IMAGE");
//        Toast.makeText(this, "imageee"+image, Toast.LENGTH_SHORT).show();
        Picasso.with(getApplication())
//                .load("http://warganet.ml/map/images/foto/"+image)
                .load("https://diosatriani44.000webhostapp.com/map/images/foto/" + image)
//                .load("http://192.168.1.8/map/images/foto/"+image)
                .into(imaget);
        String nama = intent.getExtras().getString("TAG_NAMA_DEALER");
        final String notelp = intent.getExtras().getString("notelp");
        String jenis = intent.getExtras().getString("jenis");


        namatxt.setText(nama);
//        alamattxt.setText(alamat);
        jenistxt.setText(jenis);
//        lattxt.setText(lat);
//        lngtxt.setText(lng);
        btn_notelp.setText(notelp);

        btn_notelp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+notelp));

                if (ActivityCompat.checkSelfPermission(DetailActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }

        });

//        imaget.setImageURI(Uri.parse("http://172.20.10.5/map/images/foto/"+image));

    }
    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}