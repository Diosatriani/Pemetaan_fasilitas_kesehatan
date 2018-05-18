package com.bersama.go_fkes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.bersama.go_fkes.Activity.AboutActivity;
import com.bersama.go_fkes.Activity.ListActivity;
import com.bersama.go_fkes.Activity.MapsActivity;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private SliderLayout sliderLayout;
    private CardView cv_maps, cv_list, cv_about, cv_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderLayout = findViewById(R.id.slider);
        cv_maps = findViewById(R.id.cv_maps);
        cv_list = findViewById(R.id.cv_list);
        cv_about = findViewById(R.id.cv_about);
        cv_exit = findViewById(R.id.cv_exit);


        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Fasilitas Kesehatan",R.drawable.fasilitas);
        file_maps.put("Rumah Sakit Ungaran",R.drawable.rs_ungaran);
        file_maps.put("Puskesmas Ungaran",R.drawable.puskesmas_ungarang);
        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Left_Top);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(4000);

        cv_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                Toast.makeText(MainActivity.this, "Anda Memilih Menu Maps", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        cv_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                Toast.makeText(MainActivity.this, "Anda Memilih Menu List", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });
        cv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                Toast.makeText(MainActivity.this, "Anda Memilih Menu About", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        cv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("GO_FKES");
                builder.setMessage("Jika anda Ingin Keluar Tekan (YA)!!!")
                        .setIcon(R.drawable.ic_exit_to_app_black_24dp)
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int index) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    finishAffinity();
                                }
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int index) {
                                dialog.cancel();
                            }
                        });AlertDialog alert =builder.create();
                alert.show();
            }
        });
    }
}
