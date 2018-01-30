package com.example.joan.place;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Joan on 2017/9/8.
 */
public class B_tree_background extends AsyncTask<String,Void,Void>{
    int count;
    Context ctx;
    int location;
    public B_tree_background(Context ctx)
    {
        this.ctx=ctx;
    }
    @Override
    protected Void doInBackground(String... params) {
        //b_tree_background.execute(id,b_name,b_rating,b_lat,b_lng,b_phone,b_web,b_opening_time,b_photo);
        int id=Integer.parseInt(params[0]);
        String name=params[1];
        String rating=params[2];
        double lat=Double.parseDouble(params[3]);
        double lng=Double.parseDouble(params[4]);
        double min_lat=Double.parseDouble(params[3]);
        double min_lng=Double.parseDouble(params[4]);
        double max_lat=Double.parseDouble(params[3]);
        double max_lng=Double.parseDouble(params[4]);
        String phone=params[5];
        String web=params[6];
        String opening=params[7];
        String photo=params[8];
        count=Integer.parseInt(params[9]);
        //Log.e("in back ground","id="+id);
        Liked.Add_info_for_B_DB(count,id,name,rating,lat,lng,phone,web,opening,photo);
        Liked.Add_info_for_R_DB(count,id,min_lat,min_lng,max_lat,max_lng);
        int temp=Liked.Count_Google_IDs.get(count);
        //Log.e("temp=",""+temp);

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

        int temp=Liked.Count_Google_IDs.get(count);
        //Log.e("work done"," "+temp);
        int insert=temp+1;
        Liked.Count_Google_IDs.set(count,insert);
        if(Liked.Count_Google_IDs.get(count)==Liked.GoogleIDlist.get(count)&&count==0)
        {
            double lat=Liked.firstplace.getLatLng().latitude;
            double lon=Liked.firstplace.getLatLng().longitude;
            if(Liked.findbestsol(0,lat,lon)==1)
            {
                Log.e("fiirstmemberlist size",""+Liked.firstmemberlists.size());
                //Find_sec find_sec=new Find_sec(ctx);
               // find_sec.execute();

            }
        }
        else if(Liked .firstmemberlists.size()>0&&Liked.Count_Google_IDs.get(count)==Liked.GoogleIDlist.get(count)&&count==1)
        {

            if(Liked.firstfound==1)
            {
                Find_sec find_sec=new Find_sec(ctx);
                find_sec.execute();
                //Log.e("fiirstmemberlist size",""+Liked.firstmemberlists.size());
                //Find_sec find_sec=new Find_sec(ctx);
                // find_sec.execute();

            }
        }
        super.onPostExecute(aVoid);
    }
}

