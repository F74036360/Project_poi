package com.example.joan.place;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Joan on 2017/9/8.
 */
public class ThreePOI_B_tree_back extends AsyncTask<String,Void,Void>{
    int count;
    Context ctx;
    public ThreePOI_B_tree_back(Context ctx)
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
        Account_fragment.Add_info_for_B_DB(count,id,name,rating,lat,lng,phone,web,opening,photo);
        Account_fragment.Add_info_for_R_DB(count,id,min_lat,min_lng,max_lat,max_lng);
        int temp=Account_fragment.Count_Google_IDs.get(count);
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

        int temp=Account_fragment.Count_Google_IDs.get(count);
        //Log.e("work done"," "+temp);
        int insert=temp+1;
        Account_fragment.Count_Google_IDs.set(count,insert);
        if(Account_fragment.Count_Google_IDs.get(count)==Account_fragment.GoogleIDlist.get(count)&&count==1)
        {
            double lat=Account_fragment.firstplace.getLatLng().latitude;
            double lon=Account_fragment.firstplace.getLatLng().longitude;
            if(Account_fragment.findbestsol(1,lat,lon)==1)
            {
                ThreePOI_Findsec findsec=new ThreePOI_Findsec(ctx,0);
                findsec.execute();
            }
        }
        if(Account_fragment.Count_Google_IDs.get(count)==Account_fragment.GoogleIDlist.get(count)&&count==2)
        {
            ThreePOI_Findsec findsec2=new ThreePOI_Findsec(ctx,2);
            findsec2.execute();
        }
        super.onPostExecute(aVoid);
    }
}

