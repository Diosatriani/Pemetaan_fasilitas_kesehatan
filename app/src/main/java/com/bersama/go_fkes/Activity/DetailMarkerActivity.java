package com.bersama.go_fkes.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bersama.go_fkes.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class DetailMarkerActivity extends AppCompatActivity implements OnMapReadyCallback, DirectionCallback {

    private TextView tvPosisiTujuan, tvHasilDir, jarak;
    private MapFragment mapFragment;
    private LatLng latLngTujuan;
    private LatLng latLngPosisi;
    LocationManager locationManager;
    String latitude, longitude;
    private LatLng latitu,longitu;
    private LatLng latLngTujuanMobil;
    CameraPosition cameraPosition;
    static final int REQUEST_LOCATION = 1;
    private LatLng latLngTujuanBike;
    private ProgressDialog progressDialog;
    private Toolbar mActionToolbar;
    private FloatingActionButton fabDirWalk;
    private String serverKey = "AIzaSyBpmvzj6M8tfy9-VrD80oq70qmRbC4lb2Q";
    private String[] colors = {"#FF3F51B5", "#FF2E2F33", "#FF7B7B7C"};
    private String telephone, username, alamat, id;
    private SharedPreferences sharedpreferences;
    private GoogleMap gMap;
    private Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_marker);

        tvPosisiTujuan = findViewById(R.id.tv_posisi_tujuan);
        jarak = findViewById(R.id.jarak);
        fabDirWalk      = findViewById(R.id.fab_dir_walk);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_detail_marker);
        mapFragment.getMapAsync(this);


        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation1();




        mActionToolbar = findViewById(R.id.toolbar_detail_marker);
        setSupportActionBar(mActionToolbar);
        getSupportActionBar().setTitle("Direction");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

//        requestLocationUpdates();

        fabDirWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDirectionJalan();
            }
        });

    }

    //button back toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        this.googleMap = googleMap;
        gMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);

//        addMarker();
        setUpMap();

    }

    private void setUpMap() {
        Intent intent = this.getIntent();
        String lat =intent.getExtras().getString("TAG_LATITUDE");
        String lng =intent.getExtras().getString("TAG_LONGITUDE");
        final LatLng pos = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        username = intent.getExtras().getString("TAG_NAMA_DEALER");
        telephone = intent.getExtras().getString("notelp");
        alamat = intent.getExtras().getString("alamat");

        tvPosisiTujuan.setText(username);

        latLngTujuan        = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        latLngTujuanBike    = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        latLngTujuanMobil   = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

        myMarker = gMap.addMarker(new MarkerOptions()
                .position(pos).icon(BitmapDescriptorFactory.fromBitmap(
                        createCustomMarker(DetailMarkerActivity.this))));

        cameraPosition = new CameraPosition.Builder().target(pos).zoom(15).build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



    }

    public Bitmap createCustomMarker(Context context) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        ImageView markerImage = (ImageView) marker.findViewById(R.id.iconku);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }




    public void requestDirectionJalan() {
        Intent intent = this.getIntent();
        String lat =intent.getExtras().getString("TAG_LATITUDE");
        String lng =intent.getExtras().getString("TAG_LONGITUDE");
        final LatLng destination = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

        LatLng origin = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    private void gotoLocation(double lat, double lng, float zoom){
        LatLng latLng = new LatLng(lat,lng);
        CameraUpdate cameraUpdate =CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        gMap.animateCamera(cameraUpdate);
    }

    private void getLocation1() {
//        if (ActivityCompat.checkSelfPermission(DetailMarkerActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (DetailMarkerActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(DetailMarkerActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

//            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);
                String TAG = null;
                Log.d(TAG, "getLocation1: "+latitude+longitude);


//                textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
//                        + "\n" + "Longitude = " + longitude);

            }else{
//
//                Toast.makeText(this,"Untuk mengirim laporan, hidupkan GPS terlebih dahulu!", Toast.LENGTH_SHORT).show();
//
            }
        }
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {

            Route route = direction.getRouteList().get(0);

            Intent intent = this.getIntent();
            String lat =intent.getExtras().getString("TAG_LATITUDE");
            String lng =intent.getExtras().getString("TAG_LONGITUDE");
            final LatLng destination = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
            final LatLng origin = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
//            final LatLng origin = new LatLng(-7.051145, 110.442600);

            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.))
//            gMap.addMarker(new MarkerOptions().position(origin));
            gMap.addMarker(new MarkerOptions().position(destination).icon(BitmapDescriptorFactory.fromBitmap(
                    createCustomMarker(DetailMarkerActivity.this))));

            Leg leg = route.getLegList().get(0);
            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            gMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.BLUE));
            setCameraWithCoordinationBounds(route);

//            kunjungi.setVisibility(View.GONE);
            jarak.setVisibility(View.VISIBLE);
//            jarak.setText((leg.getDistance().getText() , leg.getDuration().getText());
            jarak.setText(String.format("Jarak = %s , Durasi = %s"
                    ,leg.getDistance().getText() , leg.getDuration().getText()));
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }

}
