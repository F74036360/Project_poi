package com.example.joan.place;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joan on 2017/12/26.
 */
public class Anslist {
    public ArrayList<String> all_poi_name;
    public ArrayList<String> all_poi_photo;
    public ArrayList<LatLng> all_poi_latlng;
    public ArrayList<String> all_poi_rating;
    public ArrayList<String> all_poi_address;
    public ArrayList<String> all_poi_phone;
    public ArrayList<String> all_poi_website;
    public ArrayList<String> all_poi_opening;
    public ArrayList<Date> alltimeLine;
    public Anslist(ArrayList<String> all_poi_name, ArrayList<String> all_poi_photo,
                   ArrayList<LatLng> all_poi_latlng, ArrayList<String> all_poi_rating,
                   ArrayList<String> all_poi_address, ArrayList<String> all_poi_phone,
                   ArrayList<String> all_poi_website, ArrayList<String> all_poi_opening,
                   ArrayList<Date> alltimeLine
                   )
    {
        this.all_poi_name=all_poi_name;
        this.all_poi_address=all_poi_address;
        this.all_poi_latlng=all_poi_latlng;
        this.all_poi_opening=all_poi_opening;
        this.all_poi_phone=all_poi_phone;
        this.all_poi_website=all_poi_website;
        this.all_poi_photo=all_poi_photo;
        this.all_poi_rating=all_poi_rating;
        this.alltimeLine=alltimeLine;
        Log.e("into anslist",""+alltimeLine);
        Log.e("into anslist",""+all_poi_name);
    }



}
