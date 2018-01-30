package com.example.joan.place;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class map extends Fragment  implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener{
    public Button data_selector;
    GoogleApiClient mGoogleApiClient;
    public static Location mLastLocation;
    public static Marker mCurrLocationMarker;
    private GoogleApiClient client;
    public static GoogleMap mMap;
    public static View MainView;


    public map() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainView=inflater.inflate(R.layout.fragment_map, container, false);
        data_selector=(Button) MainView.findViewById(R.id.data_selector);
        SupportMapFragment mapFragment= (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mappp);
        mapFragment.getMapAsync(this);
        if(Save_result.tablename_list.size()==0)
        {
            SQLiteDatabase S_DB=Save_result.DH.getWritableDatabase();
            //String search_query = "SELECT * FROM "+ DBHelper.tablenamelist +" WHERE name = "+"'"+temp+"' ";
            //Cursor cursor = db.rawQuery(search_query,null);

            //Cursor c=S_DB.rawQuery(search_query,null);
            int reset=0;
            Cursor c1=S_DB.rawQuery("SELECT * FROM " +DBHelper.tablenamelist,null);
            if(c1!=null)
            {
                c1.moveToFirst();
                while (c1.isAfterLast() == false) {
                    Log.e("origin table name",""+c1.getString(1));
                    Save_result.tablename_list.add(c1.getString(1));
                    c1.moveToNext();
                }
            }
            c1.close();
        }

        data_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                final ArrayAdapter<String> table=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,Save_result.tablename_list);
                builder.setAdapter(table, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String temp="table_"+Save_result.tablename_list.get(i);
                        android.database.sqlite.SQLiteDatabase S_DB=Save_result .DH.getWritableDatabase();
                        Cursor c=S_DB.rawQuery("SELECT * FROM " +temp,null);
                        ArrayList<String> mData=new ArrayList<String>() ;
                        ArrayList<String> photodata=new ArrayList<String>();
                        ArrayList<LatLng> latLngs=new ArrayList<LatLng>();
                        ArrayList<String> all_poi_rating=new ArrayList<String>();
                        ArrayList<String> all_poi_openingtime=new ArrayList<String>();
                        ArrayList<String> all_poi_phone=new ArrayList<String>();
                        ArrayList<String> all_poi_website=new ArrayList<String>();
                        if(c!=null)
                        {
                            c.moveToFirst();
                            while (c.isAfterLast()==false)
                            {
                                mData.add(c.getString(1));
                                photodata.add(c.getString(7));
                                double lat=c.getDouble(3);
                                double lng=c.getDouble(4);
                                latLngs.add(new LatLng(lat,lng));
                                all_poi_rating.add(c.getString(6));
                                all_poi_openingtime.add(c.getString(5));
                                all_poi_phone.add(c.getString(2));
                                all_poi_website.add(c.getString(8));
                                c.moveToNext();
                            }
                            moveMap(latLngs,mData);
                        }

                    }
                }).show();
            }
        });
        return MainView;

    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this.getContext()).addApi(AppIndex.API).build();
        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }


    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Initialize Google Play Services
        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }


        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(22.9997, 120.218))
                .title("Marker"));
        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(new LatLng(22.9997, 120.218))
                        .zoom(15)
                        .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void moveMap(ArrayList<LatLng> all_poi_latlng,ArrayList<String> all_poi_name)
    {
        // 建立地圖攝影機的位置物件
        mMap.clear();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Initialize Google Play Services
        for(int i=0;i<all_poi_latlng.size();i++)
        {
            mMap.addMarker(new MarkerOptions()
                    .position(all_poi_latlng.get(i))
                    .title(all_poi_name.get(i)));
            CameraPosition cameraPosition =
                    new CameraPosition.Builder()
                            .target(all_poi_latlng.get(i))
                            .zoom(15)
                            .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }




    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.e("new location: ",""+latLng.latitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
