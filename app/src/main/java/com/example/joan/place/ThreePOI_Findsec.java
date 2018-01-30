package com.example.joan.place;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Joan on 2017/9/8.
 */
public class ThreePOI_Findsec extends AsyncTask<String,Void,Void>{
    int count;
    Context ctx;
    public ThreePOI_Findsec(Context c,int count)
    {
        ctx=c;
        this.count=count;
    }
    @Override
    protected Void doInBackground(String... params) {
        for(int i=0;i<Account_fragment.firstmemberlists.size();i++)
        {
            Firstmemberlist temp=Account_fragment.firstmemberlists.get(i);
            int id=temp.getid();
            double lat=temp.getLat();
            double lng=temp.getLng();
            if(Account_fragment.findsecsol(count,lat,lng,id)==1)
            {
                break;
            }
        }
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

    }
}

