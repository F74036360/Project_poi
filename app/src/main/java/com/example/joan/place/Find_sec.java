package com.example.joan.place;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joan on 2017/9/8.
 */
public class Find_sec extends AsyncTask<String,Void,Void>{
    int count;
    Context ctx;
    public Find_sec(Context c)
    {
        ctx=c;
    }
    @Override
    protected Void doInBackground(String... params) {
       /* int gotbestans=0;
        for(int i=0;i<Liked.firstmemberlists.size();i++)
        {
            Firstmemberlist temp=Liked.firstmemberlists.get(i);
            int id=temp.getid();
            double lat=temp.getLat();
            double lng=temp.getLng();
            if(Liked.findsecsol(1,lat,lng,id)==1)
            {
                gotbestans=1;
                //break;
            }
        }
        if(gotbestans==0)
        {
            Liked.firstmemberlists.clear();
            Liked.firstfound=0;
            double lat=Liked.firstplace.getLatLng().latitude;
            double lon=Liked.firstplace.getLatLng().longitude;
            if(Liked.findanothersol(0,lat,lon)==1)
            {
                Log.e("fiirstmemberlist size",""+Liked.firstmemberlists.size());
                if(Liked.firstfound==1)
                {

                    for(int i=0;i<Liked.firstmemberlists.size();i++)
                    {
                        Firstmemberlist temp=Liked.firstmemberlists.get(i);
                        int id=temp.getid();
                        double lat2=temp.getLat();
                        double lng=temp.getLng();
                        if(Liked.findsecsol(1,lat2,lng,id)==1)
                        {
                            gotbestans=1;
                            break;
                        }
                    }

                }
            }

        }*/
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        int gotbestans=0;
        for(int i=0;i<Liked.firstmemberlists.size();i++)
        {
            Firstmemberlist temp=Liked.firstmemberlists.get(i);
            int id=temp.getid();
            double lat=temp.getLat();
            double lng=temp.getLng();
            if(Liked.findsecsol(1,lat,lng,id)==1)
            {
                gotbestans=1;
                //break;
            }
        }
        if(gotbestans==0)
        {
            Liked.firstmemberlists.clear();
            Liked.firstfound=0;
            double lat=Liked.firstplace.getLatLng().latitude;
            double lon=Liked.firstplace.getLatLng().longitude;
            if(Liked.findanothersol(0,lat,lon)==1)
            {
                Log.e("fiirstmemberlist size",""+Liked.firstmemberlists.size());
                if(Liked.firstfound==1)
                {

                    for(int i=0;i<Liked.firstmemberlists.size();i++)
                    {
                        Firstmemberlist temp=Liked.firstmemberlists.get(i);
                        int id=temp.getid();
                        double lat2=temp.getLat();
                        double lng=temp.getLng();
                        if(Liked.findsecsol(1,lat2,lng,id)==1)
                        {
                            gotbestans=1;
                            break;
                        }
                    }
                }
            }
        }
        if(gotbestans==0)
        {
            Log.e("into last ans","gggg");
            Liked.firstmemberlists.clear();
            Liked.firstfound=0;
            double lat=Liked.firstplace.getLatLng().latitude;
            double lon=Liked.firstplace.getLatLng().longitude;
            if(Liked.findlastsol(0,lat,lon)==1){
                if(Liked.findlastsol(1,lat,lon)==1)
                {
                    Log.e("all_poi_name",""+Liked.all_poi_name);
                    Liked.all_ans_list.add(new Anslist(Liked.all_poi_name,Liked.all_poi_photo,Liked.all_poi_latlng
                            ,Liked.all_poi_rating,Liked.all_poi_address,Liked.all_poi_phone,Liked.all_poi_website,Liked.all_poi_openingtime,Liked.timeLine));
                    for(int i=0;i<Liked.all_latlng.size()-1;i++)
                    {
                        //Log.e("i=",""+i);
                        LatLng L1=Liked.all_latlng.get(i);
                        LatLng L2=Liked.all_latlng.get(i+1);
                        //Log.e("ans name",""+all_poi_name);
                        //Log.e("start:"+L1," to: "+L2);
                        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+L1.latitude+","+L1.longitude +
                                "&destinations="+L2.latitude+","+L2.longitude +
                                "&key=AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc";
                        //Log.e("distance matrix","i="+i);
                        Liked.Distance_Matrix task = new Liked.Distance_Matrix(i,Liked.all_ans_list.size()-1);
                        task.execute(url);

                    }

                }
            }

        }
    }
}

