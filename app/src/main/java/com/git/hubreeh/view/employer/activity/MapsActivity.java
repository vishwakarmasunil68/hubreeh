package com.git.hubreeh.view.employer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.git.hubreeh.ApiServices.ApiClient;
import com.git.hubreeh.ApiServices.ApiInterface;
import com.git.hubreeh.R;
import com.git.hubreeh.model.AddressDecoder;
import com.git.hubreeh.utility.GPSTracker;
import com.git.hubreeh.utility.Pref;
import com.git.hubreeh.utility.StringUtils;
import com.git.hubreeh.utility.TagUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class MapsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, OnMapReadyCallback {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivSelect)
    ImageView ivSelect;
    @BindView(R.id.tvAddresss)
    TextView tvAddresss;
    private GoogleMap googlemap;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    final int MY_LOCATION = 0x2;
    GoogleApiClient googleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    BottomSheetBehavior sheetBehavior;

    String lat = "", longx = "", addres = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitle("");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    GPSTracker gps;

    public void getLocation() {
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.d(TagUtils.getTag(), "location:-latitude:-" + latitude);
            Log.d(TagUtils.getTag(), "location:-longitude:-" + longitude);

//            if(googlemap!=null){
//                googlemap.addMarker(new MarkerOptions().position(new LatLng( latitude, longitude)).title("Current Location"));
//            }

//            if(googlemap!=null){
            LatLng latLng = new LatLng(latitude, longitude);
//                googlemap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                googlemap.animateCamera(CameraUpdateFactory.zoomTo(18));
//            }
            if (googlemap != null) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,
                        10);
                googlemap.animateCamera(cameraUpdate);
            }

            Pref.SetStringPref(getApplicationContext(), StringUtils.CURRENT_LATITUDE, String.valueOf(latitude));
            Pref.SetStringPref(getApplicationContext(), StringUtils.CURRENT_LONGITUDE, String.valueOf(longitude));
        } else {
//            gps.showSettingsAlert();
        }
    }


    @Override
    public void onMapReady(GoogleMap mgoogleMap) {
        googlemap = mgoogleMap;
        googlemap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        View myLocationButton = findViewById(MY_LOCATION);
        if (myLocationButton != null && myLocationButton.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myLocationButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                    getResources().getDisplayMetrics());
            params.setMargins(margin, margin, margin, margin);
            myLocationButton.setLayoutParams(params);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googlemap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            googlemap.setMyLocationEnabled(true);
        }


        googlemap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                double lat = googlemap.getCameraPosition().target.latitude;
                double longx = googlemap.getCameraPosition().target.longitude;
                if (lat != 0.0 && longx != 0.0) {
                    getAddress("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + longx + "&sensor=true&key=AIzaSyAtRfXXSsAiT7kYDKsgU_p0wL_sZ6dNNsg");

                }
            }
        });
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Log.d(TagUtils.getTag(), "location:-latitude:-" + latitude);
            Log.d(TagUtils.getTag(), "location:-longitude:-" + longitude);
            LatLng latLng = new LatLng(latitude, longitude);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(15f).build();

            googlemap.setMyLocationEnabled(true);
            googlemap.getUiSettings().setMyLocationButtonEnabled(true);
            googlemap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

        }

//        getLocation();

    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googlemap.setMyLocationEnabled(true);
                    }
                } else {
                    checkLocationPermission();
                }
                return;
            }
        }
    }


    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d(TagUtils.getTag(),"location:-"+location.getLatitude());
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        String provider = locationManager.getBestProvider(new Criteria(), true);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        googlemap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        googlemap.animateCamera(CameraUpdateFactory.zoomTo(18));
//        if (googleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
        }
    }


    @OnClick(R.id.ivSelect)
    public void onViewClicked() {
        Intent returnIntent = new Intent();

        Log.d(TagUtils.getTag(), "address:-" + addres);
        Log.d(TagUtils.getTag(), "long:-" + longx);
        Log.d(TagUtils.getTag(), "lat:-" + lat);
        if (addres.equalsIgnoreCase("") || lat.equalsIgnoreCase("") || longx.equalsIgnoreCase("")) {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        } else {
            returnIntent.putExtra("address", addres);
            returnIntent.putExtra("lat", lat);
            returnIntent.putExtra("long", longx);
            setResult(Activity.RESULT_OK, returnIntent);
        }
        finish();
    }


    private void getAddress(String url) {
        ApiInterface apiInterface = ApiClient.getJobClient().create(ApiInterface.class);
        Call<AddressDecoder> call = apiInterface.address_decoder(url);
        call.enqueue(new Callback<AddressDecoder>() {
            @Override
            public void onResponse(Call<AddressDecoder> call, retrofit2.Response<AddressDecoder> response) {
                AddressDecoder decoder = response.body();
                if (decoder.getStatus().equalsIgnoreCase("OK")) {
                   /* List<AddressDecoder.ResultsBean.AddressComponentsBean> beans = new ArrayList<>();
                    beans.addAll(decoder.getResults().get(0).getAddress_components());
                    for (int i = 0; i < beans.size(); i++) {
                        if (beans.get(i).getTypes().contains("administrative_area_level_2")) {

                        }
                    }*/

                    tvAddresss.setText(response.body().getResults().get(0).getFormatted_address());
                    addres = response.body().getResults().get(0).getFormatted_address();
                    lat = "" + response.body().getResults().get(0).getGeometry().getLocation().getLat();
                    longx = "" + response.body().getResults().get(0).getGeometry().getLocation().getLng();

                } else {
                    Toast.makeText(MapsActivity.this, "Address Not Found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<AddressDecoder> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
