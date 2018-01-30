package com.example.joan.place;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.sqlite.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mid extends Fragment {

    public static int countindb=0;
    public String FirstWeekday;
    public Button first;

    public static int already=0;
    public static SQLiteDatabase R_db=null;
    public static SQLiteDatabase b_db=null;
    public static final String POI_name="name";
    public static final String POI_tel="tel";
    public static final String POI_web="web";
    public static final String POI_opening_time="opening_time";
    public static final String POI_rating="rating";
    public static final String POI_lat="lat";
    public static final String POI_lng="lng";
    public static final String POI_address="address";//photo
    public static final String POI_ID="_ID";
    public static FragmentManager fragmentManager;
    public static ArrayList<Integer> anslist=new ArrayList<>();
    public static int cntdata=0;
    public static ArrayList<String> Google_IDs=new ArrayList<>();
    public static ArrayList<String> all_poi_name=new ArrayList<>();
    public static ArrayList<String> all_poi_rating=new ArrayList<>();
    public static ArrayList<LatLng> all_poi_latlng=new ArrayList<>();
    public static ArrayList<String> all_poi_photo=new ArrayList<>();
    public static ArrayList<String> all_poi_address=new ArrayList<>();
    public static ArrayList<String> all_poi_phone=new ArrayList<>();
    public static ArrayList<String> all_poi_website=new ArrayList<>();
    public static ArrayList<String> all_poi_openingtime=new ArrayList<>();

    Button search;
    Button POI;
    int PLACE_PICKER_REQUEST=0;
    public static View mainview;
    public static Context ctx;
    Place firstplace;
    private ImageButton poiitem_restaurant;
    private ImageButton poiitem_travel;
    private ImageButton poiitem_bank;
    private ImageButton poiitem_salon;
    private ImageButton poiitem_hospital;
    private ImageButton poiitem_hotel;
    private ImageButton poiitem_book;
    private ImageButton poiitem_cafe;
    private ImageButton poiitem_mall;
    private EditText self_choice_typed;
    private String choice_of_poi;
    private Button poi_self_ok;
    private TextView POIchoice;

    Button  Taichung;
    Button Yunlin;
    Button Changhua;
    Button Miaoli;

    public Mid() {
        // Required empty public constructor
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainview=inflater.inflate(R.layout.fragment_mid, container, false);
        ctx=getContext();
        search=(Button)mainview.findViewById(R.id.search);
        POI=(Button)mainview.findViewById(R.id.POI);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat s2=new SimpleDateFormat("EEEE", Locale.ENGLISH);
        FirstWeekday=s2.format(currentTime);
        Taichung=(Button)mainview.findViewById(R.id.Taichung);
        Yunlin=(Button)mainview.findViewById(R.id.Yunlin);
        Changhua=(Button)mainview.findViewById(R.id.Changhua);
        Miaoli=(Button)mainview.findViewById(R.id.Miaoli);

        Taichung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POITask(new LatLng(24.137195,120.686751));
            }
        });

        Yunlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POITask(new LatLng(23.711793,120.541171));
            }
        });

        Changhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POITask(new LatLng(24.081631,120.538444));
            }
        });

        Miaoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POITask(new LatLng(24.570020,120.822343));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        POI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                final View v1 = inflater1.inflate(R.layout.poi_test, null);
                final AlertDialog.Builder dialog_list = new AlertDialog.Builder(getActivity());
                dialog_list.setTitle("POI").setView(v1).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice_of_poi = POIchoice.getText().toString();
                        //Toast.makeText(getContext(), "" + choice_of_poi, Toast.LENGTH_LONG).show();
                        POI.setText(choice_of_poi);
                        cntdata=0;
                        cntdata=0;
                        countindb=0;
                        already=0;

                        R_db= SQLiteDatabase.openOrCreateDatabase(":memory:",null);
                        b_db=SQLiteDatabase.openOrCreateDatabase(":memory:",null);
                        String temp_Rtree="R_table0";
                        R_db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS "+""+temp_Rtree+" USING rtree(id, minX, maxX, minY, maxY);");
                        String temp_Btree="B_table0";
                        b_db.execSQL("CREATE TABLE IF NOT EXISTS "+temp_Btree+" ( _ID INTEGER PRIMARY KEY," +POI_name
                                +" TEXT,"+POI_tel+" TEXT,"+POI_lat+" DOUBLE,"+POI_lng+" DOUBLE,"
                                +POI_opening_time+" TEXT," +POI_rating+" TEXT,"+POI_address+" TEXT,"+POI_web+" TEXT)");
                        String result=getUrl(new LatLng(24.137195,120.686751),choice_of_poi,2000);
                        if(firstplace!=null)
                        {
                            result=getUrl(firstplace.getLatLng(),choice_of_poi,2000);
                        }
                        all_poi_name.clear();
                        all_poi_photo.clear();
                        all_poi_latlng.clear();
                        all_poi_rating.clear();
                        //all_poi_address.add(c.getString(7));
                        all_poi_phone.clear();
                        all_poi_website.clear();
                        all_poi_openingtime.clear();
                        PlacesTask task = new PlacesTask(0);
                        task.execute(result);


                        //Log.e("position",""+position);
                    }
                }).show();

                poiitem_restaurant = (ImageButton) v1.findViewById(R.id.restaurant);
                setPoiitem_restaurant();
                poiitem_salon = (ImageButton) v1.findViewById(R.id.salon);
                setPoiitem_salon();
                poiitem_cafe = (ImageButton) v1.findViewById(R.id.cafe);
                setpoiitem_cafe();
                poiitem_travel = (ImageButton) v1.findViewById(R.id.travelspot);
                setPoiitem_travel();
                poiitem_hospital = (ImageButton) v1.findViewById(R.id.hospital);
                setPoiitem_hospital();
                poiitem_hotel = (ImageButton) v1.findViewById(R.id.hotel);
                setPoiitem_hotel();
                poiitem_bank = (ImageButton) v1.findViewById(R.id.bank);
                setPoiitem_bank();
                poiitem_book = (ImageButton) v1.findViewById(R.id.bookstore);
                setPoiitem_book();
                poiitem_mall = (ImageButton) v1.findViewById(R.id.department_store);
                setPoiitem_mall();
                self_choice_typed = (EditText) v1.findViewById(R.id.self_typed);
                POIchoice = (TextView) v1.findViewById(R.id.POI_choice);
                poi_self_ok = (Button) v1.findViewById(R.id.self_OK);
                set_selfok();
            }
        });


        cntdata=0;
        cntdata=0;
        countindb=0;
        already=0;
        R_db= SQLiteDatabase.openOrCreateDatabase(":memory:",null);
        b_db=SQLiteDatabase.openOrCreateDatabase(":memory:",null);
        String temp_Rtree="R_table0";
        R_db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS "+""+temp_Rtree+" USING rtree(id, minX, maxX, minY, maxY);");
        String temp_Btree="B_table0";
        b_db.execSQL("CREATE TABLE IF NOT EXISTS "+temp_Btree+" ( _ID INTEGER PRIMARY KEY," +POI_name
                +" TEXT,"+POI_tel+" TEXT,"+POI_lat+" DOUBLE,"+POI_lng+" DOUBLE,"
                +POI_opening_time+" TEXT," +POI_rating+" TEXT,"+POI_address+" TEXT,"+POI_web+" TEXT)");
        String result=getUrl(new LatLng(24.137195,120.686751),"餐廳",2000);
        PlacesTask task = new PlacesTask(0);
        task.execute(result);


        return mainview;
    }

    public void POITask(LatLng latLng)
    {
        cntdata=0;
        cntdata=0;
        countindb=0;
        already=0;

        R_db= SQLiteDatabase.openOrCreateDatabase(":memory:",null);
        b_db=SQLiteDatabase.openOrCreateDatabase(":memory:",null);
        String temp_Rtree="R_table0";
        R_db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS "+""+temp_Rtree+" USING rtree(id, minX, maxX, minY, maxY);");
        String temp_Btree="B_table0";
        b_db.execSQL("CREATE TABLE IF NOT EXISTS "+temp_Btree+" ( _ID INTEGER PRIMARY KEY," +POI_name
                +" TEXT,"+POI_tel+" TEXT,"+POI_lat+" DOUBLE,"+POI_lng+" DOUBLE,"
                +POI_opening_time+" TEXT," +POI_rating+" TEXT,"+POI_address+" TEXT,"+POI_web+" TEXT)");
        String result=getUrl(latLng,"餐廳",2000);
        all_poi_name.clear();
        all_poi_photo.clear();
        all_poi_latlng.clear();
        all_poi_rating.clear();
        //all_poi_address.add(c.getString(7));
        all_poi_phone.clear();
        all_poi_website.clear();
        all_poi_openingtime.clear();
        PlacesTask task = new PlacesTask(0);
        task.execute(result);
    }

    private String getUrl(LatLng latlng, String nearbyPlace, int radius) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latlng.latitude + "," + latlng.longitude);
        googlePlacesUrl.append("&radius=" + radius);
        googlePlacesUrl.append("&rankby=prominence");
        googlePlacesUrl.append("&language=zh-TW");
        googlePlacesUrl.append("&keyword=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc");
        Log.d("getUrl_poi", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    public static void addtoarray()
    {
        already=1;
        String btable="B_table0";
        Cursor c=b_db.rawQuery("SELECT * FROM " +btable , null);
        if (c != null) {
            c.moveToFirst();
            while (c.isAfterLast() == false)
            {
                all_poi_name.add(c.getString(1));
                all_poi_photo.add(c.getString(7));
                double lat = c.getDouble(3);
                double lng = c.getDouble(4);
                all_poi_latlng.add(new LatLng(lat,lng));
                all_poi_rating.add(c.getString(6));
                //all_poi_address.add(c.getString(7));
                all_poi_phone.add(c.getString(2));
                all_poi_website.add(c.getString(8));
                all_poi_openingtime.add(c.getString(5));
                c.moveToNext();
            }
            Log.e("name size",""+all_poi_name.size());
            Log.e("",""+all_poi_name);
            MyAdapter myAdapter=new MyAdapter(all_poi_name,all_poi_photo,all_poi_latlng);
            RecyclerView mList = (RecyclerView) mainview.findViewById(R.id.list_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mList.setLayoutManager(layoutManager);
            mList.setAdapter(myAdapter);
        }

    }

    private class PlacesTask extends AsyncTask<String, Integer, String> {
        String data = null;
        int count;
        public PlacesTask(int count)
        {
            this.count=count;
        }
        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask(count);
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        JSONObject jObject;
        int count;
        public ParserTask(int count)
        {
            this.count=count;
        }
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            Place_JSON placeJson = new Place_JSON();
            try {
                jObject = new JSONObject(jsonData[0]);
                places = placeJson.parse(jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {
            int i;
            Google_IDs=new ArrayList<>();

            for (i = 0; i < list.size(); i++) {
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
                if(hmPlace.get("place_id")!=null)
                {
                    String place_id=hmPlace.get("place_id");
                    Log.e("count="+count,"id="+place_id);
                    Google_IDs.add(place_id);

                }
            }
            for(i=0;i<Google_IDs.size();i++)
            {
                String url = getUrl_detail(Google_IDs.get(i));
                PlacesTaskDetail task = new PlacesTaskDetail(count);
                task.execute(url);
            }
        }
    }

    private class PlacesTaskDetail extends AsyncTask<String, Integer, String> {
        String data = null;
        // Invoked by execute() method of this object
        int count;
        public PlacesTaskDetail(int count)
        {
            this.count=count;
        }

        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            ParserTaskDetail parserTask = new ParserTaskDetail(count);
            parserTask.execute(result);

        }
    }
    private class ParserTaskDetail extends AsyncTask<String, Integer, HashMap<String, String>> {
        ArrayList<String> temp=new ArrayList<>();
        JSONObject jObject;
        int count;
        String b_name="-NA-";
        String b_rating="-NA-";
        String b_lat="-NA-";
        String b_lng="-NA-";
        String b_phone="-NA-";
        String b_web="-NA-";
        String b_opening_time="-NA-";
        String b_photo="-NA-";
        String id="-NA-";

        public ParserTaskDetail(int count)
        {
            this.count=count;

        }
        // Invoked by execute() method of this object
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        protected HashMap<String, String> doInBackground(String... jsonData) {
            ArrayList<String> temp = new ArrayList<>();
            HashMap<String, String> hPlaceDetails = null;
            PlaceDetail placeDetailsJsonParser = new PlaceDetail();
            try {
                jObject = new JSONObject(jsonData[0]);
                hPlaceDetails = placeDetailsJsonParser.parse(jObject);
                Double rating = 0.0;
                Log.e("count=" + count, "" + hPlaceDetails.get("name"));
                if (hPlaceDetails.get("rating").compareTo("-NA-") != 0) {
                    rating = Double.parseDouble(hPlaceDetails.get("rating"));
                    Log.e("count=" + count, "" + hPlaceDetails.get(rating));
                }
                String photo = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference="
                        + hPlaceDetails.get("ref_photo") + "&key="
                        + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc";
                b_name=hPlaceDetails.get("name");
                Log.e("name=",""+b_name);
                b_rating = hPlaceDetails.get("rating");
                b_lat = hPlaceDetails.get("lat");
                b_lng = hPlaceDetails.get("lng");
                b_phone = hPlaceDetails.get("formatted_phone");
                b_web = hPlaceDetails.get("website");
                b_opening_time = hPlaceDetails.get(FirstWeekday);
                b_photo = photo;
                id = Integer.toString(cntdata);
                Log.e("id=", "" + id);
            }
            catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return hPlaceDetails;
        }

        // Executed after the complete execution of doInBackground() method
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(HashMap<String, String> hPlaceDetails) {
            //Log.e("google list["+count+"]",""+GoogleIDlist.get(count));
            if(id!="-NA-"&&b_name!="-NA-")
            {
                cntdata+=1;
                Log.e("temp in main",""+cntdata);
                POI_schedule_background b_tree_background=new POI_schedule_background(ctx,"Mid");
                String count_S= Integer.toString(count);
                b_tree_background.execute(id,b_name,b_rating,b_lat,b_lng,b_phone,b_web,b_opening_time,b_photo,count_S);

            }

            else
            {
                Log.e("temp in mainelse",""+cntdata);
                cntdata+=1;
                countindb+=1;
            }

        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private List<String> mData;
        private List<String> photodata;
        private List<LatLng> latLngs;


        public class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
            public TextView mTextView;
            public ImageView IMG;
            public MapView mapView;
            public GoogleMap MgoogleMap;
            public TextView webview;
            public Button mapbtn;
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
                mapbtn=(Button)v.findViewById(R.id.mapbtn);
            }

            @Override
            public void onMapReady(GoogleMap googleMap) {
                MgoogleMap = googleMap;
                LatLng sydney = new LatLng(-34, 151);
                MgoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                MgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }


        public MyAdapter(List<String> data, List<String> photo_data, List<LatLng> latLngs1) {
            mData = data;
            photodata = photo_data;
            latLngs = latLngs1;
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
            holder.rankview.setText("評分: "+all_poi_rating.get(position));
            holder.openingview.setText(all_poi_openingtime.get(position));
            holder.phoneview.setText(all_poi_phone.get(position));
            holder.webview.setAutoLinkMask(Linkify.ALL);
            holder.webview.setText(all_poi_website.get(position));
            Picasso.with(ctx)
                    .load(photodata.get(position))
                    .fit()
                    .into(holder.IMG);
            holder.mapbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Double lat=all_poi_latlng.get(position).latitude;
                    Double lng=all_poi_latlng.get(position).longitude;
                    String name=all_poi_name.get(position);
                    String uri="geo:"+lat+","+lng+"?q="+lat+","+lng+"(Google+"+name+")";
                    //Log.e("uri",uri);
                    Uri gmmIntentUri = Uri.parse(uri);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    ctx.startActivity(mapIntent);
                }
            });


            // holder.starttime.setText(new SimpleDateFormat("HH:mm").format(timeLine.get(position)));
            // holder.endtime.setText(new SimpleDateFormat("HH:mm").format(timeLine.get(position+1)));


            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(ctx).setTitle("儲存此地標?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String[] tablename={"profession","hito_spot","hito_restaurant"};

                            AlertDialog.Builder dialog_list = new AlertDialog.Builder(ctx);
                            dialog_list.setTitle("選取資料庫");
                            dialog_list.setItems(tablename, new DialogInterface.OnClickListener(){
                                @Override

                                //只要你在onClick處理事件內，使用which參數，就可以知道按下陣列裡的哪一個了
                                public void onClick(DialogInterface dialog, int which) {
                                    String temptable=tablename[which];
                                    Toast.makeText(ctx,""+tablename[which],Toast.LENGTH_SHORT).show();
                                    MainActivity.save_result.add_singledatabase(temptable,mData.get(position),
                                            photodata.get(position),latLngs.get(position),all_poi_rating.get(position),
                                            all_poi_phone.get(position),all_poi_website.get(position),all_poi_openingtime.get(position));

                                }
                            });
                            dialog_list.show();

                            /* POI_change poi_change=new POI_change(position);
                            RecyclerView mList = (RecyclerView) Liked.Mainview.findViewById(R.id.list_view);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            mList.setLayoutManager(layoutManager);
                            mList.setAdapter(poi_change);*/
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                    return true;
                }
            });



        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }



    public static void Add_info_for_B_DB(int count,int id,String name,String rating,double lat,double lng,String phone,String web,String opening,String photo)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(POI_ID,id);
        contentValues.put(POI_name,name);
        contentValues.put(POI_rating,rating);
        contentValues.put(POI_lat,lat);
        contentValues.put(POI_lng,lng);
        contentValues.put(POI_tel,phone);
        contentValues.put(POI_opening_time,opening);
        contentValues.put(POI_web,web);
        contentValues.put(POI_address,photo);
        String temp_Btree="B_table"+count;
        Log.e("inserted in B",""+name);
        b_db.insertOrThrow(temp_Btree,null,contentValues);
        countindb+=1;

    }

    public static void Add_info_for_R_DB(int count,int id,double min_lat,double min_lng,double max_lat,double max_lng)
    {

        String tablename="R_table"+count;
        String insert="INSERT INTO "+tablename+" VALUES("+id+","+min_lng+", "+max_lng+", "+min_lat+", "+max_lat+");";
        R_db.execSQL(insert);
    }


    private String getUrl_detail(String Place_id) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        googlePlacesUrl.append("placeid=" + Place_id);
        googlePlacesUrl.append("&language=zh-TW");
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc");
        //  Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }
    private static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d("Exception url", "" + e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                firstplace=PlacePicker.getPlace(ctx,data);
                search.setText(firstplace.getName());

                cntdata=0;
                cntdata=0;
                countindb=0;
                already=0;

                R_db= SQLiteDatabase.openOrCreateDatabase(":memory:",null);
                b_db=SQLiteDatabase.openOrCreateDatabase(":memory:",null);
                String temp_Rtree="R_table0";
                R_db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS "+""+temp_Rtree+" USING rtree(id, minX, maxX, minY, maxY);");
                String temp_Btree="B_table0";
                b_db.execSQL("CREATE TABLE IF NOT EXISTS "+temp_Btree+" ( _ID INTEGER PRIMARY KEY," +POI_name
                        +" TEXT,"+POI_tel+" TEXT,"+POI_lat+" DOUBLE,"+POI_lng+" DOUBLE,"
                        +POI_opening_time+" TEXT," +POI_rating+" TEXT,"+POI_address+" TEXT,"+POI_web+" TEXT)");
                String result=getUrl(firstplace.getLatLng(),"餐廳",2000);
                all_poi_name.clear();
                all_poi_photo.clear();
                all_poi_latlng.clear();
                all_poi_rating.clear();
                //all_poi_address.add(c.getString(7));
                all_poi_phone.clear();
                all_poi_website.clear();
                all_poi_openingtime.clear();
                PlacesTask task = new PlacesTask(0);
                task.execute(result);

            }
        }
    }


    private void setPoiitem_restaurant() {
        poiitem_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("餐廳");
            }
        });
    }

    private void setPoiitem_travel() {
        poiitem_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("景點");
            }
        });
    }

    private void setPoiitem_bank() {
        poiitem_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("銀行");
            }
        });
    }

    private void setPoiitem_salon() {
        poiitem_salon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("理髮廳");
            }
        });
    }

    private void setPoiitem_hospital() {
        poiitem_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("醫院");
            }
        });
    }

    private void setpoiitem_cafe() {
        poiitem_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("咖啡廳");
            }
        });
    }

    private void setPoiitem_book() {
        poiitem_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("書局");
            }
        });
    }

    private void setPoiitem_hotel() {

        poiitem_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("旅館");
            }
        });
    }

    private void setPoiitem_mall() {
        poiitem_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("賣場");
            }
        });
    }

    private void set_selfok() {
        poi_self_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("" + self_choice_typed.getText());
            }
        });
    }

}
