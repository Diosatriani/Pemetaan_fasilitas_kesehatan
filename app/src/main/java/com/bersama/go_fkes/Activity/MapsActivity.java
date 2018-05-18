package com.bersama.go_fkes.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bersama.go_fkes.AppController.AppController;
import com.bersama.go_fkes.MainActivity;
import com.bersama.go_fkes.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private MapFragment mapFragment;
    private GoogleMap gMap;
    private MarkerOptions markerOptions = new MarkerOptions();
    private CameraPosition cameraPosition;
    private LatLng center, latLng;
    private String title;
    private String description;
    private String image;
    private String jenis;
    private EditText etSearch;
    private Button btnSearch;
    private GoogleMap mMap;
    private String notelp;

    public static final String ID = "id";
    public static final String TITLE = "nama";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String DESCRIPTION = "alamat";
    public static final String JENIS = "jenis";
    public static final String IMAGE = "image";

    String tag_json_obj = "json_obj_req";
    private Toolbar mActionToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etSearch = (EditText) findViewById(R.id.tvSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = etSearch.getText().toString();
                if (location != null) {
                    setNewLocation(location);
                } else {
                    Toast.makeText(getBaseContext(), "MASUKKAN LOKASI", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMarkers();
    }

    private void setNewLocation(String location) {
        Geocoder geocoder = new Geocoder(getBaseContext());
        try {
            gMap.clear();
            List<Address> addresses = geocoder.getFromLocationName(location, 10);
            Address address = addresses.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            Marker marker = gMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(address.getAddressLine(0)));
            gMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    public Bitmap createCustomMarker(Context context) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
//        markerImage.setImageResource(resource);
//        TextView txt_name = (TextView)marker.findViewById(R.id.name);
//        txt_name.setText(_name);

        try {
//            URL url = new URL("http://warganet.ml/map/images/foto/"+image);
            URL url = new URL("https://diosatriani44.000webhostapp.com/map/images/foto/"+image);
//            URL url = new URL("http://192.168.1.8/map/images/foto/"+image);
            Picasso.with(getApplication())
//                    .load("http://warganet.ml/map/images/foto/"+image)
//                    .load("https://diosatriani44.000webhostapp.com/map/images/foto/"+image)
                    .load("https://diosatriani44.000webhostapp.com/map/images/foto/"+image)
                    .resize(50,50)
                    .into(markerImage);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

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



    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        gotoLocation(-7.1262331, 110.3963713, 12);
//
//
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
            return;
        }
        gMap.setMyLocationEnabled(true);
//

////         Mengarahkan ke Kabupaten Ungaran
//        center = new LatLng(-7.1262331, 110.3963713);
//        cameraPosition = new CameraPosition.Builder().target(center).zoom(10).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        getMarkers();
    }

    private void gotoLocation(double lat, double lng, float zoom){
        LatLng latLng = new LatLng(lat,lng);
        CameraUpdate cameraUpdate =CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        gMap.animateCamera(cameraUpdate);
    }

    private void addMarker(final LatLng latlng, final String title, final String description, final String image1) {
        markerOptions.position(latlng);
        markerOptions.title(title);
        markerOptions.snippet(description);
        markerOptions.snippet(image1);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(MapsActivity.this)));
        gMap.addMarker(markerOptions);

//        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        })
        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), marker.getSnippet(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MapsActivity.this, ""+description, Toast.LENGTH_SHORT).show();
//                Double lat = marker.getPosition().latitude;
//                Double lng = marker.getPosition().longitude;
//                String notelp= marker.getClass().getName();



                Intent intent = new Intent(MapsActivity.this, DetailActivity.class);
//                intent.putExtra(image1, marker.getSnippet());
                intent.putExtra("TAG_NAMA_DEALER", marker.getTitle());
//                intent.putExtra("TAG_ALAMAT", marker.getSnippet());
                intent.putExtra("IMAGE", marker.getSnippet());
//                intent.putExtra("TAG_LATITUDE", Double.toString(lat));
//                intent.putExtra("TAG_LONGITUDE", Double.toString(lng));
                intent.putExtra("jenis", jenis);
                intent.putExtra("notelp", notelp);
                startActivity(intent);
            }
        });

    }

    // Fungsi get JSON marker
    private void getMarkers() {
        String url = "https://diosatriani44.000webhostapp.com/map/lokasi.php";
//        String url = "http://192.168.1.8/map/lokasi.php";
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    String getObject = jObj.getString("lokasi");
                    JSONArray jsonArray = new JSONArray(getObject);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title = jsonObject.getString(TITLE);
                        description = jsonObject.getString(DESCRIPTION);
                        image = jsonObject.getString(IMAGE);
                        notelp = jsonObject.getString("notelp");
                        jenis = jsonObject.getString("jenis");
                        latLng = new LatLng(Double.parseDouble(jsonObject.getString(LAT)), Double.parseDouble(jsonObject.getString(LNG)));

                        // Menambah data marker untuk di tampilkan ke google map
                        addMarker(latLng, title, description, image);
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.getMessage());
                Toast.makeText(MapsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }


}