package com.example.joan.place;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Joan on 2017/9/26.
 */


public class POI_schedule_background extends AsyncTask<String,Void,Void> {
    int count;
    Context ctx;
    String from_which;
    int location;
    int id=0;
    public POI_schedule_background(Context ctx,String from_which)
    {
        this.from_which=from_which;
        this.ctx=ctx;
    }
    @Override
    protected Void doInBackground(String... params) {

        //b_tree_background.execute(id,b_name,b_rating,b_lat,b_lng,b_phone,b_web,b_opening_time,b_photo);
        id=Integer.parseInt(params[0]);
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
        Log.e("in back ground","id="+id);
        if(from_which.compareTo("OnePOI_Schedule")==0)
        {
            OnePOI_Schedule.Add_info_for_B_DB(count,id,name,rating,lat,lng,phone,web,opening,photo);
            OnePOI_Schedule.Add_info_for_R_DB(count,id,min_lat,min_lng,max_lat,max_lng);
        }
        else if(from_which.compareTo("South")==0)
        {
            South.Add_info_for_B_DB(count,id,name,rating,lat,lng,phone,web,opening,photo);
            South.Add_info_for_R_DB(count,id,min_lat,min_lng,max_lat,max_lng);

        }
        else if(from_which.compareTo("East")==0)
        {
            East.Add_info_for_B_DB(count,id,name,rating,lat,lng,phone,web,opening,photo);
            East.Add_info_for_R_DB(count,id,min_lat,min_lng,max_lat,max_lng);
        }
        else if(from_which.compareTo("North")==0)
        {
            North.Add_info_for_B_DB(count,id,name,rating,lat,lng,phone,web,opening,photo);
            North.Add_info_for_R_DB(count,id,min_lat,min_lng,max_lat,max_lng);
        }
        else if(from_which.compareTo("Mid")==0)
        {
            Mid.Add_info_for_B_DB(count,id,name,rating,lat,lng,phone,web,opening,photo);
            Mid.Add_info_for_R_DB(count,id,min_lat,min_lng,max_lat,max_lng);
        }
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


        if(from_which.compareTo("OnePOI_Schedule")==0)
        {
            int temp=OnePOI_Schedule.cntdata;
            Log.e("temp in background",""+temp);
            Log.e("countdb=",""+OnePOI_Schedule.countindb);
            if(OnePOI_Schedule.already==0&&OnePOI_Schedule.countindb==temp)
            {
                OnePOI_Schedule.addtoarray();
            }
        }

        else if(from_which.compareTo("South")==0)
        {
            int temp=South.cntdata;
            Log.e("temp in background",""+temp);
            Log.e("countdb=",""+South.countindb);
            if(South.already==0&&South.countindb==temp)
            {
                South.addtoarray();
            }
        }

        else if(from_which.compareTo("North")==0)
        {
            int temp=North.cntdata;
            Log.e("temp in background",""+temp);
            Log.e("countdb=",""+North.countindb);
            if(North.already==0&&North.countindb==temp)
            {
                North.addtoarray();
            }
        }

        else if(from_which.compareTo("East")==0)
        {
            int temp=East.cntdata;
            Log.e("temp in background",""+temp);
            Log.e("countdb=",""+East.countindb);
            if(East.already==0&&East.countindb==temp)
            {
                East.addtoarray();
            }
        }

        else if(from_which.compareTo("Mid")==0)
        {
            int temp=Mid.cntdata;
            Log.e("temp in background",""+temp);
            Log.e("countdb=",""+Mid.countindb);
            if(Mid.already==0&&Mid.countindb==temp)
            {
                Mid.addtoarray();
            }
        }

    }
}

