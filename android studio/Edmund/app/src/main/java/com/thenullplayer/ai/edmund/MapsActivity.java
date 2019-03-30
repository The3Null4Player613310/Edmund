package com.thenullplayer.ai.edmund;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    //setup variables
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private SensorManager sensorManager;
    private Boolean isFollowing;
    //marker variables
    private Marker userMarker;
    private ArrayList<Marker> locationMarkers = new ArrayList<>();
    //marker options variables
    private MarkerOptions locationMarkerOptions = new MarkerOptions();
    private MarkerOptions userMarkerOptions = new MarkerOptions();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //setup my location button
        findViewById(R.id.myLocation).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //ternary hack on binary object
                if(isFollowing == null)
                    isFollowing = false;
                else if(isFollowing == false)
                    isFollowing=true;
                else if(isFollowing == true)
                    isFollowing=false;

                if((userMarker != null) && (isFollowing != null))
                {
                    CameraPosition camPos;
                    if(isFollowing == false)
                        camPos = new CameraPosition(userMarker.getPosition(), 18F, 0F, googleMap.getCameraPosition().bearing);
                    else
                        camPos = new CameraPosition(userMarker.getPosition(), 19F, 45F, userMarker.getRotation());
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
                }

            }
        });

        //create user bitmap
        Bitmap bitmap;
        Drawable drawable = getDrawable(R.drawable.ic_navigation_black_32dp);
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.setColorFilter(new LightingColorFilter( Color.BLACK, Color.GREEN));
        drawable.draw(canvas);

        //setup marker options
        //location marker
        locationMarkerOptions.position(new LatLng(0,0));
        locationMarkerOptions.rotation(0F);
        locationMarkerOptions.title("");
        locationMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(240F));
        //locationMarker.flat(true);
        //locationMarker.anchor(0.5F,0.5F);

        //user marker
        userMarkerOptions.position(new LatLng(0,0));
        userMarkerOptions.rotation(0F);
        userMarkerOptions.title("");
        //userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(90F));
        userMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        userMarkerOptions.flat(true);
        userMarkerOptions.anchor(0.5F,0.5F);

        //setup sensor manager
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

        //setup location manager
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) || (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
        {
            //setup listeners
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,new gpsLocationListener());
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,new networkLocationListener());
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,0,0, new passiveLocationListener());
        }
        else
        {
            Toast.makeText(this,R.string.location_permission_prompt,Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMapIn) throws SecurityException
    {
        googleMap = googleMapIn;

        //setup map
        googleMap.setMapType(MAP_TYPE_HYBRID);
        googleMap.setTrafficEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setOnCameraMoveStartedListener(new CameraMoveStartedListener());

        //setup settings

        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setIndoorLevelPickerEnabled(true);

        //set up user marker
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if(lastLocation != null)
        {
            userMarkerOptions.position(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
            userMarkerOptions.rotation(lastLocation.getBearing());
        }
        userMarkerOptions.title("");
        //userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(90F));
        //userMarkerOptions.flat(true);
        //userMarkerOptions.anchor(0.5F,0.5F);
        userMarker = googleMap.addMarker(userMarkerOptions);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMarker.getPosition(), 18F));
    }

    //define camera listeners

    private class CameraMoveStartedListener implements OnCameraMoveStartedListener
    {

        @Override
        public void onCameraMoveStarted(int i)
        {
            if(i==REASON_GESTURE)
                isFollowing=null;
        }
    }

    //define sensor listeners
    //wip

    //define location listeners

    private class gpsLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location) {
            Marker playerMarker = userMarker;
            playerMarker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            if (location.getSpeed()>0F)
                playerMarker.setRotation(location.getBearing());
            if (isFollowing!=null)
            {
                CameraPosition camPos;
                if(isFollowing == false)
                    camPos = new CameraPosition(userMarker.getPosition(), 18F, 0F, googleMap.getCameraPosition().bearing);
                else
                    camPos = new CameraPosition(userMarker.getPosition(), 19F, 45F, userMarker.getRotation());
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
                //googleMap.animateCamera(CameraUpdateFactory.newLatLng(userMarker.getPosition()));//old
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }

    private class networkLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location) {
            //mark locations of interest
            ArrayList<Marker> removedMarkers = new ArrayList<>();
            Location tempLocation = new Location("");
            tempLocation.reset();
            Marker newMarker = googleMap.addMarker(locationMarkerOptions.position(new LatLng(location.getLatitude(),location.getLongitude())));
            //iterate through markers and merge markers
            for (Marker marker:locationMarkers)
            {
                tempLocation.setLatitude(marker.getPosition().latitude);
                tempLocation.setLongitude(marker.getPosition().longitude);
                tempLocation.setAltitude(location.getAltitude());
                if (location.getAccuracy() >= location.distanceTo(tempLocation))//log mod for more markers try base e //Math.log(location.getAccuracy())
                {
                    marker.remove();
                    removedMarkers.add(marker);
                }
            }
            //remove markers from list
            for (Marker marker:removedMarkers)
            {
                locationMarkers.remove(marker);
            }
            locationMarkers.add(newMarker);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }

    private class passiveLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }

    //misc methods

}
