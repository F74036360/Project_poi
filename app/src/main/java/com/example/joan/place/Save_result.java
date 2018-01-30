package com.example.joan.place;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class Save_result extends Fragment {
    public static String table_used="";
    public static Context ctx;
    public Button data_selector;
    public static DBHelper DH;
    public static final String POI_name="name";
    public static final String POI_tel="tel";
    public static final String POI_web="web";
    public static final String POI_opening_time="opening_time";
    public static final String POI_rating="rating";
    public static final String POI_lat="lat";
    public static final String POI_lng="lng";
    public static final String POI_address="address";//photo
    public static final String POI_ID="_ID";
    public static String create_table_cloumn=" ( _ID INTEGER PRIMARY KEY," +POI_name
            +" TEXT,"+POI_tel+" TEXT,"+POI_lat+" DOUBLE,"+POI_lng+" DOUBLE,"
            +POI_opening_time+" TEXT," +POI_rating+" TEXT,"+POI_address+" TEXT,"+POI_web+" TEXT)";
    public static ArrayList<String> tablename_list=new ArrayList<>();
    public static View Mainview;


    public Save_result() {
        DH=new DBHelper(MainActivity.ctx);
        String temp="table_";
        //tablename_list.add(temp);
        SQLiteDatabase S_DB=this.DH.getWritableDatabase();
        S_DB.execSQL("CREATE TABLE IF NOT EXISTS "+temp+create_table_cloumn);
        //Log.e("save Result","sad");
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mainview=inflater.inflate(R.layout.fragment_save_result, container, false);
        data_selector=(Button)Mainview.findViewById(R.id.database_selector);
        ctx=getContext();
        tablename_list.clear();

        SQLiteDatabase S_DB=this.DH.getWritableDatabase();
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
                tablename_list.add(c1.getString(1));
                c1.moveToNext();
            }
        }
        c1.close();


        data_selector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tablename_list.clear();

                SQLiteDatabase S_DB=DH.getWritableDatabase();
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
                        tablename_list.add(c1.getString(1));
                        c1.moveToNext();
                    }
                }
                c1.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final ArrayAdapter<String> table=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,tablename_list);
                builder.setAdapter(table, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String temp="table_"+tablename_list.get(i);
                        android.database.sqlite.SQLiteDatabase S_DB=DH.getWritableDatabase();
                       // table_used=temp;
                        Cursor c=S_DB.rawQuery("SELECT * FROM " +temp,null);
                        ArrayList<String> mData=new ArrayList<String>() ;
                        ArrayList<String> photodata=new ArrayList<String>();
                        List<LatLng> latLngs=new ArrayList<LatLng>();
                        List<String> all_poi_rating=new ArrayList<String>();
                        List<String> all_poi_openingtime=new ArrayList<String>();
                        List<String> all_poi_phone=new ArrayList<String>();
                        List<String> all_poi_website=new ArrayList<String>();
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

                            MyAdapter myAdapter=new MyAdapter(mData,photodata,latLngs
                                    ,all_poi_rating,all_poi_openingtime,all_poi_phone,all_poi_website);
                            RecyclerView mList = (RecyclerView) Mainview.findViewById(R.id.listview);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            mList.setLayoutManager(layoutManager);
                            mList.setAdapter(myAdapter);
                        }



                    }
                }).show();
            }
        });
        return Mainview;
    }
    public void add_singledatabase(String table_name, String all_poi_name
            ,String all_poi_photo,LatLng all_poi_latlng,
                             String all_poi_rating,String all_poi_phone,
                             String all_poi_website,String all_poi_openingtime)
    {
        tablename_list.clear();
        SQLiteDatabase S_DB=this.DH.getWritableDatabase();
        String temp="table_"+table_name;
        Log.e("insert table name",table_name);
        //String search_query = "SELECT * FROM "+ DBHelper.tablenamelist +" WHERE name = "+"'"+temp+"' ";
        //Cursor cursor = db.rawQuery(search_query,null);
        Cursor c=S_DB.rawQuery("SELECT * FROM " +DBHelper.tablenamelist,null);
        //Cursor c=S_DB.rawQuery(search_query,null);
        int reset=0;
        if(c!=null)
        {
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                Log.e("origin table name in save",""+c.getString(1));
                if(c.getString(1)==table_name)
                {
                    reset=1;
                    break;
                }
                c.moveToNext();
            }
        }
        if(reset==0)
        {
            ContentValues cnt=new ContentValues();
            cnt.put(DBHelper.tablename,table_name);
            S_DB.insertOrThrow(DBHelper.tablenamelist,null,cnt);

        }
        Cursor c1=S_DB.rawQuery("SELECT * FROM " +DBHelper.tablenamelist,null);
        if(c1!=null)
        {
            c1.moveToFirst();
            while (c1.isAfterLast() == false) {
                Log.e("origin table name",""+c1.getString(1));
                tablename_list.add(c1.getString(1));
                c1.moveToNext();
            }
        }
        c1.close();
        c.close();

        /*if(c!=null)
        {
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                Log.e("origin table name",""+c.getString(1));
                for(int i=0;i<tablename_list.size();i++)
                {
                    Log.e("tablename_list",""+tablename_list.get(i));
                    if(tablename_list.get(i).toString().matches(c.getString(1)))
                    {
                        reset=1;
                        break;
                    }
                }
                if(reset==0)
                {
                    ContentValues cnt=new ContentValues();
                    cnt.put(DBHelper.tablename,table_name);
                    S_DB.insertOrThrow(DBHelper.tablenamelist,null,cnt);
                    tablename_list.add(c.getString(1));
                    Log.e("tablesize",""+tablename_list.size());
                }

                c.moveToNext();
            }
        }
        c.close();*/
        SQLiteDatabase db=this.DH.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS "+temp+create_table_cloumn);

        Log.e("table name",""+temp);
        Cursor cursor = db.rawQuery("SELECT  * FROM " + temp, null);
        int id=0;
        if(cursor.moveToLast()!=false){
            id = cursor.getInt(0);
            Log.e("id",""+id);
            //--get other cols values
        }

        ContentValues contentValues=new ContentValues();
        contentValues.put(POI_ID,id+1);
        contentValues.put(POI_name,all_poi_name);
        contentValues.put(POI_rating,all_poi_rating);
        contentValues.put(POI_lat,all_poi_latlng.latitude);
        contentValues.put(POI_lng,all_poi_latlng.longitude);
        contentValues.put(POI_tel,all_poi_phone);
        contentValues.put(POI_opening_time,all_poi_openingtime);
        contentValues.put(POI_web,all_poi_website);
        contentValues.put(POI_address,all_poi_photo);
        db.insert(temp,null,contentValues);

        Log.e("done","saved all");


    }

    public void add_database(String table_name, ArrayList<String> all_poi_name
            ,ArrayList<String> all_poi_photo,ArrayList<LatLng> all_poi_latlng,
                                    ArrayList<String> all_poi_rating, ArrayList<String> all_poi_phone,
                                    ArrayList<String> all_poi_website,ArrayList<String> all_poi_openingtime)
    {
        SQLiteDatabase S_DB=this.DH.getWritableDatabase();
        String temp="table_"+table_name;
        ContentValues cnt=new ContentValues();
        cnt.put(DBHelper.tablename,table_name);
        S_DB.insertOrThrow(DBHelper.tablenamelist,null,cnt);
        Cursor c=S_DB.rawQuery("SELECT * FROM " +DBHelper.tablenamelist,null);

        if(c!=null)
        {
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                tablename_list.add(c.getString(1));
                c.moveToNext();
            }
        }
        c.close();
        SQLiteDatabase db=this.DH.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS "+temp+create_table_cloumn);
        for(int i=0;i<all_poi_name.size();i++)
        {
            Log.e("table name",""+temp);
            ContentValues contentValues=new ContentValues();
            contentValues.put(POI_ID,i);
            contentValues.put(POI_name,all_poi_name.get(i));
            contentValues.put(POI_rating,all_poi_rating.get(i));
            contentValues.put(POI_lat,all_poi_latlng.get(i).latitude);
            contentValues.put(POI_lng,all_poi_latlng.get(i).longitude);
            contentValues.put(POI_tel,all_poi_phone.get(i));
            contentValues.put(POI_opening_time,all_poi_openingtime.get(i));
            contentValues.put(POI_web,all_poi_website.get(i));
            contentValues.put(POI_address,all_poi_photo.get(i));
            db.insertOrThrow(temp,null,contentValues);
        }
        Log.e("done","saved all");

    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private List<String> mData;
        private List<String> photodata;
        private List<LatLng> latLngs;
        private List<String> all_poi_rating;
        private List<String> all_poi_openingtime;
        private List<String> all_poi_phone;
        private List<String> all_poi_website;

        public class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
            public TextView mTextView;
            public ImageView IMG;
            public MapView mapView;
            public GoogleMap MgoogleMap;
            public TextView webview;
            public TextView phoneview;
            public TextView rankview;
            public TextView openingview;
            public TextView starttime;
            public TextView endtime;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.info_text);
                IMG = (ImageView) v.findViewById(R.id.img);
                mapView = (MapView) v.findViewById(R.id.map_card);
                webview=(TextView)v.findViewById(R.id.website);
                phoneview=(TextView)v.findViewById(R.id.phone);
                rankview=(TextView)v.findViewById(R.id.rank);
                openingview=(TextView)v.findViewById(R.id.opening_time);
                starttime=(TextView)v.findViewById(R.id.starttime);
                endtime=(TextView)v.findViewById(R.id.endtime);
            }

            @Override
            public void onMapReady(GoogleMap googleMap) {
                MgoogleMap = googleMap;
                LatLng sydney = new LatLng(-34, 151);
                MgoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                MgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }


        public MyAdapter(List<String> data, List<String> photo_data, List<LatLng> latLngs1,List<String> all_poi_rating,
                         List<String>  all_poi_openingtime,List<String>all_poi_phone, List<String> all_poi_website )
        {
            mData = data;
            photodata = photo_data;
            latLngs = latLngs1;
            this.all_poi_rating=all_poi_rating;
            this.all_poi_openingtime=all_poi_openingtime;
            this.all_poi_phone=all_poi_phone;
            this.all_poi_website=all_poi_website;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.forcard, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mTextView.setText(mData.get(position));
            Picasso.with(ctx).load(photodata.get(position)).fit()
                    .into(holder.IMG);
            holder.rankview.setText("評分: "+all_poi_rating.get(position));
            holder.openingview.setText(all_poi_openingtime.get(position));
            holder.phoneview.setText(all_poi_phone.get(position));
            holder.webview.setAutoLinkMask(Linkify.ALL);
            holder.webview.setText(all_poi_website.get(position));


            // holder.starttime.setText(new SimpleDateFormat("HH:mm").format(timeLine.get(position)));
            // holder.endtime.setText(new SimpleDateFormat("HH:mm").format(timeLine.get(position+1)));
           /*holder.mapView.onCreate(null);
            holder.mapView.setClickable(false);
            holder.mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng sydney = latLngs.get(position);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title(mData.get(position)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                }

            });*/
          /*  holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new android.app.AlertDialog.Builder(ctx).setTitle("刪除此地標?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            android.database.sqlite.SQLiteDatabase S_DB=DH.getWritableDatabase();
                           // int position_delete=position-1;
                            //Log.e("position",""+position_delete);
                            //SQLiteDatabase db = this.getWritableDatabase();
                            S_DB.execSQL("DELETE FROM " + table_used+ " WHERE "+POI_ID+"='"+position+"'");
                            //db.close();

                            S_DB.delete(table_used,POI_ID+"="+position,null);
                            Cursor c=S_DB.rawQuery("SELECT * FROM " +table_used,null);
                            ArrayList<String> mData=new ArrayList<String>() ;
                            ArrayList<String> photodata=new ArrayList<String>();
                            List<LatLng> latLngs=new ArrayList<LatLng>();
                            List<String> all_poi_rating=new ArrayList<String>();
                            List<String> all_poi_openingtime=new ArrayList<String>();
                            List<String> all_poi_phone=new ArrayList<String>();
                            List<String> all_poi_website=new ArrayList<String>();
                            if(c!=null)
                            {
                                c.moveToFirst();
                                while (c.isAfterLast()==false)
                                {
                                    Log.e("name",""+c.getString(1));
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
                                MyAdapter myAdapter=new MyAdapter(mData,photodata,latLngs
                                        ,all_poi_rating,all_poi_openingtime,all_poi_phone,all_poi_website);
                                RecyclerView mList = (RecyclerView) Mainview.findViewById(R.id.listview);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                mList.setLayoutManager(layoutManager);
                                mList.setAdapter(myAdapter);
                            }
                            /* POI_change poi_change=new POI_change(position);
                            RecyclerView mList = (RecyclerView) Liked.Mainview.findViewById(R.id.list_view);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            mList.setLayoutManager(layoutManager);
                            mList.setAdapter(poi_change);
                        }
                    })
                    return true;
                }
            });*/



        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }



}
