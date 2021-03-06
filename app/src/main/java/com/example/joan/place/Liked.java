package com.example.joan.place;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
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
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Liked extends Fragment {
    static long time1;

    long time2;
    public static Activity main_activity;
    public Button createfirst;
    public Button saveresult;
    public Button Matrix;
    private static int mYear;
    private static int mMonth;
    private static int mDate;
    private static String FirstWeekday;
    private static Button doSetDate;
    private static Button doSetTime;
    private static Button pickplace;
    public static int firstfound=0;
    public static int secfound=0;
    //private EditText setTimeDuration;
    private Button all_OK;
    private Button add_POI;
    private GoogleApiClient client;
    public List<HashMap<String, String>> mainlist = null;
    private ImageButton poiitem_restaurant;
    private ImageButton poiitem_travel;
    private ImageButton poiitem_bank;
    private ImageButton poiitem_salon;
    private ImageButton poiitem_hospital;
    private ImageButton poiitem_hotel;
    private ImageButton poiitem_book;
    private ImageButton poiitem_cafe;
    private ImageButton poiitem_mall;
    public  int middle=0;
    static int cnt_timeline=0;
    public static FloatingActionButton map_direction;
    public static ViewPager mViewPager;
    public static List<PageView> pageList;
    public static ArrayList<Date> timeLine=new ArrayList<>();
    static ArrayList<LatLng> all_latlng=new ArrayList<>();//from start to end
    //for poi reference
    public static ArrayList<String> all_poi_name=new ArrayList<>();
    public static ArrayList<String> all_poi_rating=new ArrayList<>();
    public static ArrayList<LatLng> all_poi_latlng=new ArrayList<>();
    public static ArrayList<String> all_poi_photo=new ArrayList<>();
    public static ArrayList<String> all_poi_address=new ArrayList<>();
    public static ArrayList<String> all_poi_phone=new ArrayList<>();
    public static ArrayList<String> all_poi_website=new ArrayList<>();
    public static ArrayList<String> all_poi_openingtime=new ArrayList<>();
    public ArrayList<Integer> cnt_poi_in_database=new ArrayList<>();
    //public static ArrayList<LatLng> anslist=new ArrayList<>();
    public static ArrayList<Firstmemberlist> firstmemberlists=new ArrayList<>();
    public static ArrayList<Firstmemberlist> secmemberlists=new ArrayList<>();
    public static ArrayList<Anslist> all_ans_list=new ArrayList<>();

    public static Context ctx;


    //end of poi ref

    //private  ImageView img_trip;
    static long when_to_get_middle=0;
    private Button poi_self_ok;
    private TextView POIchoice;
    private EditText self_choice_typed;
    private String choice_of_poi;
    public static Place firstplace;
    private Place lastplace;
    private String first_place_msg;
    private String last_place_msg;
    private static String formatDate;
    private String formatTime;
    private static String first_time_set;

    //ArrayList<String> myDataset = new ArrayList<>();
    //ArrayList<String> ref_dataset = new ArrayList<>();
    private final static int PLACE_PICKER_REQUEST = 1;
    private final static int PLACE_PICKER_REQUEST_LAST=2;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    public static View Mainview;
    MyAdapter myAdapter;
    public static POIAdapter poiAdapter;
    public static ArrayList<String> POI_choice_list=new ArrayList<>();
    public static ArrayList<String> POI_length_list=new ArrayList<>();
    public static ArrayList<Integer> Count_Google_IDs=new ArrayList();
    public static ArrayList<Integer> GoogleIDlist=new ArrayList<>();

    //database
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


    public Liked() {
        // Required empty public constructor
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mainview = inflater.inflate(R.layout.fragment_liked, container, false);
        main_activity=getActivity();
        ctx=getContext();
        fragmentManager= getFragmentManager();
        all_ans_list.clear();
       /*SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
        parseFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        displayFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));


        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
            timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date time1 = null;
            time1 = displayFormat.parse("3:00 PM");
            Date time2 = parseFormat.parse("7:00 AM");
            time1=parseFormat.parse(parseFormat.format(time1));
            time2=parseFormat.parse(parseFormat.format(time2));
            Date path=timeFormat.parse("12:00:00");
            long sum=path.getTime()+time2.getTime()+path.getTime();
            Log.e("sum=",""+sum);
            time2= parseFormat.parse(parseFormat.format(new Date(sum)));
            Log.e("sum time2",""+time2);
            String time2e=parseFormat.format(time2);
            //Log.e("time1",""+timelength[0]);
           // Log.e("time2",""+timelength[1]);
           // Log.e("Starttime",""+time1);
           // Log.e("time2",""+time2);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/



        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
        createfirst = (Button) Mainview.findViewById(R.id.first_trip_button);
        all_OK = (Button) Mainview.findViewById(R.id.all_ok);
       // Matrix=(Button) Mainview.findViewById(R.id.Matrix);
        saveresult=(Button)Mainview.findViewById(R.id.save_result);
        //add_POI=(Button)Mainview.findViewById(R.id.add_poi);
        poiAdapter = new POIAdapter();
        if(all_ans_list.size()==0)
        {
            timeLine.clear();
            POI_length_list.clear();
            POI_choice_list.clear();

            pageList = new ArrayList<>();
            pageList.add(new PageTwoView());
            mViewPager = (ViewPager) Mainview.findViewById(R.id.pager);
            mViewPager.setAdapter(new SamplePagerAdapter());
            /*final RecyclerView mList = (RecyclerView) Mainview.findViewById(R.id.list_view);
            final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            mList.setLayoutManager(layoutManager1);
            POI_choice_list.add("poi");
            POI_choice_list.add("poi");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
            POI_length_list.add("00:30:00");
            POI_length_list.add("00:30:00");
            Log.e("",""+POI_length_list.size());
            mList.setAdapter(poiAdapter);*/
        }

        createfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POI_length_list.clear();
                POI_choice_list.clear();
                firstmemberlists.clear();
                secmemberlists.clear();
                firstfound=0;
                secfound=0;
                //fragmentManager= getFragmentManager();
                timeLine.clear();
                poiAdapter = new POIAdapter();
                pageList = new ArrayList<>();
                pageList.add(new PageTwoView());
                mViewPager = (ViewPager) Mainview.findViewById(R.id.pager);
                mViewPager.setAdapter(new SamplePagerAdapter());
              /*  final RecyclerView mList = (RecyclerView) Mainview.findViewById(R.id.list_view);
                final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
                layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                mList.setLayoutManager(layoutManager1);
                POI_choice_list.add("poi");
                POI_choice_list.add("poi");
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                POI_length_list.add("00:30:00");
                POI_length_list.add("00:30:00");
                Log.e("",""+POI_length_list.size());
                mList.setAdapter(poiAdapter);*/
                Count_Google_IDs.clear();
                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                final View v = inflater1.inflate(R.layout.alert_choose_firat_location, null);
                new AlertDialog.Builder(getActivity()).setTitle("起點選擇")
                        .setView(v).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //visitDuration = setTimeDuration.getText().toString();
                        //Toast.makeText(getContext(), formatDate + "\n" + formatTime + "\n" + msg + "\n" + visitDuration, Toast.LENGTH_LONG).show();
                    }
                }).show();
                pickplace = (Button) v.findViewById(R.id.pickplace);
                pickplace.setOnClickListener(new View.OnClickListener() {
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

                doSetDate = (Button) v.findViewById(R.id.datepicker_first);
                doSetDate.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDate = c.get(Calendar.DAY_OF_MONTH);
                        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                formatDate = setDateFormat(year, month, day);
                                SimpleDateFormat dateFormat=new SimpleDateFormat("EEEE", Locale.ENGLISH);
                                Date date=new Date(year,month,day);
                                Log.e(""+formatDate,""+dateFormat.format(date));
                                FirstWeekday=dateFormat.format(date);
                                doSetDate.setText(""+(month+1)+"/"+day);
                            }



                        }, mYear, mMonth, mDate).show();
                    }
                });

                doSetTime = (Button) v.findViewById(R.id.timepicker_first);
                doSetTime.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int hour = c.get(Calendar.HOUR_OF_DAY);
                        int minute = c.get(Calendar.MINUTE);
                        // Create a new instance of TimePickerDialog and return it
                        new TimePickerDialog(ctx, new TimePickerDialog.OnTimeSetListener(){

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Log.e("hour"+hourOfDay,"minute: "+minute);
                                SimpleDateFormat time=new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                                time.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                                SimpleDateFormat time2=new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
                                time2.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                                Time t = Time.valueOf(hourOfDay+":"+minute+":00");
                                first_time_set=time.format(t);
                                doSetTime.setText(""+time2.format(t));
                                Log.e("hour",""+first_time_set);


                            }
                        }, hour, minute, false).show();
                    }
                });

            }
        });

        Count_Google_IDs.clear();
        all_OK.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.e("START TIME",""+System.currentTimeMillis());
                pageList.clear();
                all_poi_name.clear();
                all_poi_rating.clear();
                all_poi_latlng.clear();
                all_poi_photo.clear();
                all_poi_address.clear();
                all_poi_phone.clear();
                all_poi_website.clear();
                all_poi_openingtime.clear();
                all_ans_list.clear();
                cnt_poi_in_database.clear();
                time1=System.currentTimeMillis();
                R_db=SQLiteDatabase.openOrCreateDatabase(":memory:",null);
                b_db=SQLiteDatabase.openOrCreateDatabase(":memory:",null);
                if(firstplace.getName()==lastplace.getName())
                {
                    int all_place=POI_choice_list.size()+1;
                    //middle=all_place/2;
                    SimpleDateFormat s1=new SimpleDateFormat("ss");
                    SimpleDateFormat s2=new SimpleDateFormat("HH:mm");
                    for(int i=0;i<POI_choice_list.size();i++)
                    {
                        anslist.clear();
                        cnt_poi_in_database.add(0);
                        Count_Google_IDs.add(0);
                        String temp_Rtree="R_table"+i;
                        R_db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS "+""+temp_Rtree+" USING rtree(id, minX, maxX, minY, maxY);");
                        String temp_Btree="B_table"+i;
                        b_db.execSQL("CREATE TABLE IF NOT EXISTS "+temp_Btree+" ( _ID INTEGER PRIMARY KEY," +POI_name
                                +" TEXT,"+POI_tel+" TEXT,"+POI_lat+" DOUBLE,"+POI_lng+" DOUBLE,"
                                +POI_opening_time+" TEXT," +POI_rating+" TEXT,"+POI_address+" TEXT,"+POI_web+" TEXT)");
                    }

                    try {
                        SimpleDateFormat time=new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
                        time.setTimeZone(TimeZone.getTimeZone("GMT+8"));


                        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                        parseFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                        Date timeS=parseFormat.parse(first_time_set);
                        Log.e("timeS",""+timeS);
                        when_to_get_middle=timeS.getTime();
                        Log.e("times",""+when_to_get_middle);
                        Log.e("middle First",""+parseFormat.format(when_to_get_middle));
                        timeLine.add(parseFormat.parse(parseFormat.format(when_to_get_middle)));

                        /*String time1=parseFormat.format(when_to_get_middle).toString();
                        //Log.e("time1",""+time1);
                        String time2="01:00:00";
                        //Log.e("time2",time2);*/
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                        //Date date1 = timeFormat.parse(time1);
                        //Date date2 = timeFormat.parse(time2);
                        /*long sum =date2.getTime()+when_to_get_middle;
                        Log.e("sum",""+sum);
                        Log.e("date2",""+date2);
                        String date3 = timeFormat.format(new Date(sum));
                        Log.e("date3",""+date3);*/
                        //Date timeS = new SimpleDateFormat("HH:mm").parse(parseFormat.format(timeLine.get(count)));
                        //Date timeE = new SimpleDateFormat("HH:mm").parse(parseFormat.format(timeLine.get(count+1)));
                        for(int i=0;i<POI_length_list.size();i++)
                        {

                            Log.e("path",""+POI_length_list.get(i));
                            Date path=timeFormat.parse(POI_length_list.get(i));
                            when_to_get_middle+=path.getTime();
                            Log.e("path",""+when_to_get_middle);
                            String sum = timeFormat.format(new Date(when_to_get_middle));
                            Log.e("sum",""+sum);
                            //long when_to_get_middle1=path.getTime();
                            //long temp=when_to_get_middle+when_to_get_middle1;
                            //Log.e("middle",""+parseFormat.format(temp));

                            timeLine.add(parseFormat.parse(parseFormat.format(when_to_get_middle)));
                        }

                        /*for(int i=;i<POI_length_list.size();i++)
                        {
                            when_to_get_middle+=POI_length_list.get(i).getTime();
                            timeLine.add(parseFormat.parse(s2.format(when_to_get_middle)));
                        }*/
                        for(int i=0;i<timeLine.size();i++)
                        {
                            //SimpleDateFormat format=new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                            //format.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                            //Log.e("format", format.format(timeLine.get(i)));
                            Log.e("timeLine: "+i,""+timeLine.get(i));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    for(int i=0;i<POI_choice_list.size();i++)
                    {
                        String result=getUrl(firstplace.getLatLng(),POI_choice_list.get(i),1000);
                        PlacesTask task = new PlacesTask(i);
                        task.execute(result);
                    }


                }


            }
        });

        return Mainview;

    }

    public static  class PageView extends RelativeLayout {
        public PageView(Context context) {
            super(context);
        }
    }

    public static void initView() {
        mViewPager = (ViewPager) Mainview.findViewById(R.id.pager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        PageListener pageListener=new PageListener();
        mViewPager.setOnPageChangeListener(pageListener);
    }

    public static class PageListener extends ViewPager.SimpleOnPageChangeListener {
        public void onPageSelected(int position) {
            Log.e("page",""+position);
        }
    }


    public static void initData() {
        pageList = new ArrayList<>();
        for(int i=0;i<all_ans_list.size();i++)
        {
            pageList.add(new PageOneView(all_ans_list.get(i)));
        }
    }

    public static class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    public static class PageOneView extends PageView{
        public PageOneView(Anslist anslist) {
            super(ctx);
            View view = LayoutInflater.from(ctx).inflate(R.layout.page_content, null);
           // TextView textView = (TextView) view.findViewById(R.id.text);
           // textView.setText("Page one");
            MyAdapter myAdapter=new MyAdapter(anslist.all_poi_name,anslist.all_poi_photo
                    ,anslist.all_poi_latlng,anslist.all_poi_rating,anslist.all_poi_opening,anslist.all_poi_website,anslist.all_poi_phone,anslist.alltimeLine);
            //Liked.MyAdapter myAdapter=new Liked.MyAdapter(Liked.all_poi_name,Liked.all_poi_photo,Liked.all_poi_latlng);
            RecyclerView mList = (RecyclerView) view.findViewById(R.id.list_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mList.setLayoutManager(layoutManager);
            mList.setAdapter(myAdapter);
            addView(view);
        }
    }

    public static class PageTwoView extends PageView{
        @TargetApi(Build.VERSION_CODES.N)
        public PageTwoView() {
            super(ctx);
            View view = LayoutInflater.from(ctx).inflate(R.layout.list_view, null);
            final RecyclerView mList = (RecyclerView) view.findViewById(R.id.list_view);
            final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            mList.setLayoutManager(layoutManager1);
            POI_choice_list.add("poi");
            POI_choice_list.add("poi");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
            POI_length_list.add("00:30:00");
            POI_length_list.add("00:30:00");
            Log.e("",""+POI_length_list.size());
            mList.setAdapter(poiAdapter);
            addView(view);
        }
    }



    private String getUrl(LatLng latlng, String nearbyPlace,int radius) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latlng.latitude + "," + latlng.longitude);
        googlePlacesUrl.append("&radius="+radius);
        googlePlacesUrl.append("&rankby=prominence");
        googlePlacesUrl.append("&keyword=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc");
        Log.d("getUrl_poi", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());

    }


    private void set_selfok() {
        poi_self_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("" + self_choice_typed.getText());
            }
        });
    }


    public static String setDateFormat(int year, int monthOfYear, int dayOfMonth) {
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            Log.e("first place","OK");
            if (resultCode == Activity.RESULT_OK) {
                firstplace = PlacePicker.getPlace(data, getActivity());
                Log.e("first place",""+firstplace);
                lastplace=firstplace;
                all_latlng.add(firstplace.getLatLng());
                first_place_msg = String.format("%s", firstplace.getName());
                pickplace.setText(first_place_msg);
                createfirst.setText(first_place_msg);
                //Toast.makeText(getContext(), first_place_msg, Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==PLACE_PICKER_REQUEST_LAST)
        {
            if (resultCode == Activity.RESULT_OK) {
                /*lastplace = PlacePicker.getPlace(data, getActivity());
                last_place_msg = String.format("Place: %s", lastplace.getName());
                pickplace.setText(last_place_msg);
                Toast.makeText(getContext(), last_place_msg, Toast.LENGTH_LONG).show();*/

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
        b_db.insertOrThrow(temp_Btree,null,contentValues);
        Log.e("inserted in B","id="+id);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.joan.place/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.joan.place/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public static class Distance_Matrix extends AsyncTask<String, Integer, String> {
        String data = null;
        int count;
        int from_which_anslist;
        // Invoked by execute() method of this object
        public Distance_Matrix(int count,int from_which_anslist)
        {
            this.count=count;
            this.from_which_anslist=from_which_anslist;
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
        @Override
        protected void onPostExecute(String result) {
            Log.e("distance count",""+count);
            //Log.e("anslist in distance",""+all_poi_name);
           // Log.e("size of all_anslist",""+all_ans_list.size());

            ParseMatrix parserTask=new ParseMatrix(count,from_which_anslist);
            parserTask.execute(result);
        }
    }

    public static class ParseMatrix extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        JSONObject jObject;
        int count;
        int from_which_anslist;
        public ParseMatrix(int count,int from_which_anslist)
        {
            this.count=count;
            this.from_which_anslist=from_which_anslist;
        }
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            Place_Matrix place_matrix=new Place_Matrix();
            try {
                jObject = new JSONObject(jsonData[0]);
                places = place_matrix.parse(jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {
            //Log.e("timeline origin",""+timeLine);
            Log.e("all_ans_size",""+all_ans_list.size());
            for(int i=0;i<1;i++)
            {
                HashMap<String, String> Matrix_info = list.get(i);
                //Log.e("distance text",""+Matrix_info.get("distance_text"));
                //Log.e("duration text",""+Matrix_info.get("duration_text"));
                Date time_duration;
                SimpleDateFormat s1=new SimpleDateFormat("ss");
                s1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                SimpleDateFormat s2=new SimpleDateFormat("HH:mm");
                s2.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                try {

                    SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                    parseFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    /*Date timeS=parseFormat.parse(first_time_set);
                    Log.e("timeS",""+timeS);
                    when_to_get_middle=timeS.getTime();
                    Log.e("times",""+when_to_get_middle);
                    Log.e("middle First",""+parseFormat.format(when_to_get_middle));
                    timeLine.add(parseFormat.parse(parseFormat.format(when_to_get_middle)));*/


                    time_duration=s1.parse(Matrix_info.get("duration_value"));
                    Date time_Duration = new SimpleDateFormat("HH:mm").parse(parseFormat.format(time_duration));
                    //Date timelineori=timeLine.get(cnt_timeline);
                    Log.e("from_which_anslist",""+from_which_anslist);
                    for(int j=count;j<all_ans_list.get(from_which_anslist).alltimeLine.size();j++)
                    {

                        Date setTime= s2.parse(s2.format(all_ans_list.get(from_which_anslist).alltimeLine.get(j)));
                        //Log.e("settime",""+setTime);
                        //Log.e("time_duration",""+time_Duration);
                        long timeline_s=setTime.getTime()+time_Duration.getTime();
                        Date change_timeline =s2.parse(s2.format(timeline_s));
                        all_ans_list.get(from_which_anslist).alltimeLine.set(j,change_timeline);
                    }



                    // timeLine.add(cnt_timeline,timelineori);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
           // Log.e("all poi latlng in distance",""+all_ans_list.get(from_which_anslist).all_poi_latlng.size());
           // Log.e("count",""+count);
            if(count==all_ans_list.get(from_which_anslist).all_poi_latlng.size()-1)
            {
                /*map_direction.setVisibility(View.VISIBLE);
                map_direction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        map fragment2 = new map(firstplace,all_poi_latlng,all_poi_name);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().replace(R.id.for_change_view, fragment2);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });*/
                R_db.close();
                b_db.close();
                Log.e("anslist "+from_which_anslist,""+all_ans_list.get(from_which_anslist).all_poi_name);
                Log.e("timeline in matrix",""+all_ans_list.get(from_which_anslist).alltimeLine);
                /*Liked.MyAdapter myAdapter=new Liked.MyAdapter(Liked.all_poi_name,Liked.all_poi_photo,Liked.all_poi_latlng);
                RecyclerView mList = (RecyclerView) Liked.Mainview.findViewById(R.id.list_view);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mList.setLayoutManager(layoutManager);
                mList.setAdapter(myAdapter);*/


               // Log.e("END TIME",""+System.currentTimeMillis());
              //  long time2=System.currentTimeMillis();
               // Log.e("total time:",""+(time2-time1));

            }

            if(from_which_anslist==all_ans_list.size()-1)
            {
                initData();
                initView();
            }



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
            mainlist = list;
            int i=0;
            ArrayList<String> Google_IDs=new ArrayList<>();
            Log.e("listsize",""+list.size());
            for (i = 0; i < list.size(); i++) {
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
                if(hmPlace.get("place_id")!=null)
                {
                    String place_id=hmPlace.get("place_id");
                    //Log.e("count="+count,"id="+place_id);
                    Google_IDs.add(place_id);

                }
            }
            GoogleIDlist.add(Google_IDs.size());
            for(i=0;i<Google_IDs.size();i++)
            {
                String url = getUrl_detail(Google_IDs.get(i));
                PlacesTaskDetail task = new PlacesTaskDetail(count);
                task.execute(url);
            }


        }
    }



    public static int findbestsol(int count,double latitude,double longitude)
    {
        Log.e("into findbestsol"," count="+count);
        String rtable="R_table"+count;
        String btable="B_table"+count;

        double length=5;
        double lat_diff=length/110.574;  //利用距離的比例來算出緯度上的比例
        double lon_distance=111.320*Math.cos(latitude*Math.PI/180); //算出該緯度上的經度長度
        double lon_diff=length/lon_distance; //利用距離的比例來算出經度上的比例
        //從中間點找半徑500m
        double N_500 = latitude + Math.abs(lat_diff);
        double S_500 = latitude - Math.abs(lat_diff);
        double E_500 = longitude+ Math.abs(lon_diff);
        double W_500 = longitude- Math.abs(lon_diff);

        //四面八方
        length=2;
        lat_diff=length/110.574;  //利用距離的比例來算出緯度上的比例
        lon_distance=111.320*Math.cos(latitude*Math.PI/180); //算出該緯度上的經度長度
        lon_diff=length/lon_distance; //利用距離的比例來算出經度上的比例
        double N_1000=latitude+Math.abs(lat_diff);
        double S_1000=latitude-Math.abs(lat_diff);
        double W_1000=longitude-Math.abs(lon_diff);
        double E_1000=latitude+Math.abs(lon_diff);

       /* Log.e("firstplace lat"," "+latitude);
        Log.e("firstplace lng"," "+longitude);
        Log.e("N ",""+N_500);
        Log.e("S ",""+S_500);
        Log.e("E ",""+E_500);
        Log.e("W ",""+W_500);*/

        String queryfrommid ="SELECT id FROM "+rtable
                +" WHERE minX>="+W_500
                +" AND maxX<="+E_500
                +" AND minY>="+S_500+" AND maxY<="+N_500;
        Cursor cursor = R_db.rawQuery(queryfrommid, null);
        Cursor c;
        int ans=-1;
        String ansName=null;
        LatLng anslatlng = null;
        double rating=0.0;
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                c=b_db.rawQuery("SELECT * FROM " +btable+ " WHERE " + POI_ID + " = '" + id + "'", null);
                if(c!=null)
                {
                    c.moveToFirst();
                    Log.e("name"," "+c.getString(1));
                    firstmemberlists.add(new Firstmemberlist(c.getString(1),c.getDouble(3),c.getDouble(4),c.getInt(0)));
                    /*if(Double.parseDouble(c.getString(6))>rating)
                    {
                        rating=Double.parseDouble(c.getString(6));
                        ans=c.getInt(0);
                        ansName=c.getString(1);
                        anslatlng=new LatLng(c.getDouble(3),c.getDouble(4));
                    }*/
                }
            } while (cursor.moveToNext());
            for(int i=0;i<firstmemberlists.size();i++)
            {
                Firstmemberlist temp=Liked.firstmemberlists.get(i);
                int id=temp.getid();
               // double lat=temp.getLat();
               // double lng=temp.getLng();

            }
            firstfound=1;
            return 1;
        }
        else
        {
            Log.e("first no sol","sad...");
            return 0;
        }
    }

    public static int findlastsol(int count,double latitude,double longitude)
    {
        Log.e("into findanothersol"," count="+count);
        String rtable="R_table"+count;
        String btable="B_table"+count;

        double length=2;
        double lat_diff=length/110.574;  //利用距離的比例來算出緯度上的比例
        double lon_distance=111.320*Math.cos(latitude*Math.PI/180); //算出該緯度上的經度長度
        double lon_diff=length/lon_distance; //利用距離的比例來算出經度上的比例
        //從中間點找半徑500m
        double N_500 = latitude + Math.abs(lat_diff);
        double S_500 = latitude - Math.abs(lat_diff);
        double E_500 = longitude+ Math.abs(lon_diff);
        double W_500 = longitude- Math.abs(lon_diff);


        String queryfrommid ="SELECT id FROM "+rtable
                +" WHERE minX>="+W_500
                +" AND maxX<="+E_500
                +" AND minY>="+S_500+" AND maxY<="+N_500;
        Cursor cursor = R_db.rawQuery(queryfrommid, null);
        Cursor c;
        int ans=-1;
        String ansName=null;
        LatLng anslatlng = null;
        double rating=0.0;
        if (cursor.moveToFirst()&&count==0) {
            do {
                int id = cursor.getInt(0);
                c=b_db.rawQuery("SELECT * FROM " +btable+ " WHERE " + POI_ID + " = '" + id + "'", null);
                if(c!=null)
                {
                    c.moveToFirst();
                    all_poi_name.add(c.getString(1));
                    all_poi_photo.add(c.getString(7));
                    double lat = c.getDouble(3);
                    double lng = c.getDouble(4);
                    all_poi_latlng.add(new LatLng(lat,lng));
                    all_latlng.add(new LatLng(lat,lng));
                    all_poi_rating.add(c.getString(6));
                    all_poi_address.add(c.getString(7));
                    all_poi_phone.add(c.getString(2));
                    all_poi_website.add(c.getString(8));
                    all_poi_openingtime.add(c.getString(5));
                    break;
                }
            } while (cursor.moveToNext());

            firstfound=1;
            return 1;
        }
        else if(cursor.moveToFirst()&&count==1)
        {
            do {
                int id = cursor.getInt(0);
                c=b_db.rawQuery("SELECT * FROM " +btable+ " WHERE " + POI_ID + " = '" + id + "'", null);
                if(c!=null)
                {
                    c.moveToFirst();
                    all_poi_name.add(c.getString(1));
                    all_poi_photo.add(c.getString(7));
                    double lat = c.getDouble(3);
                    double lng = c.getDouble(4);
                    all_poi_latlng.add(new LatLng(lat,lng));
                    all_latlng.add(new LatLng(lat,lng));
                    all_poi_rating.add(c.getString(6));
                    all_poi_address.add(c.getString(7));
                    all_poi_phone.add(c.getString(2));
                    all_poi_website.add(c.getString(8));
                    all_poi_openingtime.add(c.getString(5));
                    break;
                }
            } while (cursor.moveToNext());
            secfound=1;
            return 1;
        }

        return 0;
    }


    public static int findanothersol(int count,double latitude,double longitude)
    {
        Log.e("into findanothersol"," count="+count);
        String rtable="R_table"+count;
        String btable="B_table"+count;

        double length=3;
        double lat_diff=length/110.574;  //利用距離的比例來算出緯度上的比例
        double lon_distance=111.320*Math.cos(latitude*Math.PI/180); //算出該緯度上的經度長度
        double lon_diff=length/lon_distance; //利用距離的比例來算出經度上的比例
        //從中間點找半徑500m
        double N_500 = latitude + Math.abs(lat_diff);
        double S_500 = latitude - Math.abs(lat_diff);
        double E_500 = longitude+ Math.abs(lon_diff);
        double W_500 = longitude- Math.abs(lon_diff);


       /* Log.e("firstplace lat"," "+latitude);
        Log.e("firstplace lng"," "+longitude);
        Log.e("N ",""+N_500);
        Log.e("S ",""+S_500);
        Log.e("E ",""+E_500);
        Log.e("W ",""+W_500);*/

        String queryfrommid ="SELECT id FROM "+rtable
                +" WHERE minX>="+W_500
                +" AND maxX<="+E_500
                +" AND minY>="+S_500+" AND maxY<="+N_500;
        Cursor cursor = R_db.rawQuery(queryfrommid, null);
        Cursor c;
        int ans=-1;
        String ansName=null;
        LatLng anslatlng = null;
        double rating=0.0;
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                c=b_db.rawQuery("SELECT * FROM " +btable+ " WHERE " + POI_ID + " = '" + id + "'", null);
                if(c!=null)
                {
                    c.moveToFirst();
                    //Log.e("name"," "+c.getString(1));
                    firstmemberlists.add(new Firstmemberlist(c.getString(1),c.getDouble(3),c.getDouble(4),c.getInt(0)));
                }
            } while (cursor.moveToNext());
            for(int i=0;i<firstmemberlists.size();i++)
            {
                Firstmemberlist temp=Liked.firstmemberlists.get(i);
                int id=temp.getid();

            }
            firstfound=1;
            return 1;
        }
        else
        {
            Log.e("first no sol","sad...");
            return 0;
        }

    }


    public static int findsecsol(int count,double mid_Lat,double mid_lng,int midid)
    {
        anslist.clear();
        //Log.e("int sec","------mid id------: "+midid);
        String rtable="R_table"+count;
        String btable="B_table"+count;
        double firstplace_Lat=firstplace.getLatLng().latitude;
        double firstplace_Lng=firstplace.getLatLng().longitude;
        //double mid_Lat=anslatlnglist.get(0).latitude;
        //double mid_lng=anslatlnglist.get(0).longitude;
        //Log.e("mid_lat",""+mid_Lat);
        //Log.e("mid_lng",""+mid_lng);
        double E,W,S,N;
        if(firstplace_Lat<=mid_Lat)
        {
            S=firstplace_Lat;
            N=mid_Lat;
        }
        else
        {
            S=mid_Lat;
            N=firstplace_Lat;
        }

        if(firstplace_Lng<=mid_lng)
        {
            W=firstplace_Lng;
            E=mid_lng;
        }
        else
        {
            W=mid_lng;
            E=firstplace_Lng;
        }
       /* Log.e("N ",""+N);
        Log.e("S ",""+S);
        Log.e("E ",""+E);
        Log.e("W ",""+W);*/

        String queryfrommid ="SELECT id FROM "+rtable
                +" WHERE minX>="
                +W+" AND maxX<="
            +E+" AND minY>="+S+" AND maxY<="+N;
        Cursor cursor = R_db.rawQuery(queryfrommid, null);
        Cursor c;
        int ans=-1;
        int already=0;
        double rating=0.0;
        if (cursor.moveToFirst()) {
            all_latlng.clear();
            all_latlng.add(firstplace.getLatLng());
            ArrayList<String> all_poi_name=new ArrayList<>();
            ArrayList<String> all_poi_rating=new ArrayList<>();
            ArrayList<LatLng> all_poi_latlng=new ArrayList<>();
            ArrayList<String> all_poi_photo=new ArrayList<>();
            ArrayList<String> all_poi_address=new ArrayList<>();
            ArrayList<String> all_poi_phone=new ArrayList<>();
            ArrayList<String> all_poi_website=new ArrayList<>();
            ArrayList<String> all_poi_openingtime=new ArrayList<>();
            ArrayList<Date> all_timeline=new ArrayList<>();
            for(int i=0;i<timeLine.size();i++)
            {
                all_timeline.add(timeLine.get(i));
            }
            do {
                int id = cursor.getInt(0);
                c=b_db.rawQuery("SELECT * FROM " +btable+ " WHERE " + POI_ID + " = '" + id + "'", null);

                if(c!=null)
                {
                    c.moveToFirst();
                   // Log.e("------sec------","id="+c.getInt(0));
                   //
                    if(Double.parseDouble(c.getString(6))>rating)
                    {
                      //  Log.e("rating",""+c.getString(6));
                        rating=Double.parseDouble(c.getString(6));
                        ans=c.getInt(0);
                       // Log.e("name"," "+c.getString(1));
                    }
                }
            } while (cursor.moveToNext());
            anslist.add(midid);
            anslist.add(ans);
            for(int i=0;i<anslist.size();i++)
            {
                int countfortable=0;
                if(i%2==0) countfortable=0;
                else countfortable=1;
                String b_table="B_table"+countfortable;
                c=b_db.rawQuery("SELECT * FROM " +b_table+ " WHERE " + POI_ID + " = '" + anslist.get(i)+ "'", null);
                if(c!=null)
                {
                    c.moveToFirst();
                    Log.e("ans name"," "+c.getString(1));
                    all_poi_name.add(c.getString(1));
                    all_poi_photo.add(c.getString(7));
                    double lat = c.getDouble(3);
                    double lng = c.getDouble(4);
                    all_poi_latlng.add(new LatLng(lat,lng));
                    all_latlng.add(new LatLng(lat,lng));
                    all_poi_rating.add(c.getString(6));
                    all_poi_address.add(c.getString(7));
                    all_poi_phone.add(c.getString(2));
                    all_poi_website.add(c.getString(8));
                    all_poi_openingtime.add(c.getString(5));
                }
            }
            Log.e("poi_latlngsize",""+all_poi_latlng.size());
            //Log.e("timeline",""+timeLine);
            all_ans_list.add(new Anslist(all_poi_name,all_poi_photo,all_poi_latlng,all_poi_rating,all_poi_address,all_poi_phone,all_poi_website,all_poi_openingtime,all_timeline));
            Log.e("all_poi_name",""+all_poi_name);
            //Log.e("all_latlng",""+all_latlng);
            for(int i=0;i<all_latlng.size()-1;i++)
            {
                //Log.e("i=",""+i);
                LatLng L1=all_latlng.get(i);
                LatLng L2=all_latlng.get(i+1);
                //Log.e("ans name",""+all_poi_name);
                //Log.e("start:"+L1," to: "+L2);
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+L1.latitude+","+L1.longitude +
                        "&destinations="+L2.latitude+","+L2.longitude +
                        "&key=AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc";
                //Log.e("distance matrix","i="+i);
                Distance_Matrix task = new Distance_Matrix(i,all_ans_list.size()-1);
                task.execute(url);

            }

            return 1;
        }
        else
        {
            Log.e("no sol","sad...");
            return 0;
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

    /**
     * A class to parse the Google Place Details in JSON format
     */
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
            ArrayList<String> temp=new ArrayList<>();
            HashMap<String, String> hPlaceDetails = null;
            PlaceDetail placeDetailsJsonParser = new PlaceDetail();
            try {
                jObject = new JSONObject(jsonData[0]);
                hPlaceDetails = placeDetailsJsonParser.parse(jObject);
                Double rating=0.0;
                Log.e("count="+count,""+hPlaceDetails.get("name"));
                if(hPlaceDetails.get("rating").compareTo("-NA-")!=0)
                {
                    rating=Double.parseDouble(hPlaceDetails.get("rating"));
                  //  Log.e("count="+count,""+rating);
                }
                if(hPlaceDetails.get(FirstWeekday).compareTo("null")!=0&&rating>=3.0)
                {
                    Log.e("count="+count,""+hPlaceDetails.get(FirstWeekday));
                    String opening_hour=hPlaceDetails.get(FirstWeekday);
                    String[] separated = opening_hour.split(": ");
                    if(separated[1].compareTo("Closed")!=0)
                    {
                        String[] time=separated[1].split(",");
                        for(int i=0;i<time.length;i++)
                        {
                            if(time[i].compareTo("Open 24 hours")!=0)
                            {
                                String[] timelength=time[i].split(" – ");
                                if(timelength[0].contains("AM")==false&&timelength[0].contains("PM")==false)
                                {
                                    if(timelength[1].contains("AM")==true)timelength[0]+=" AM";
                                    else timelength[0]+=" PM";
                                }

                                SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
                                SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                                parseFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                                displayFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));



                                Date time1 = displayFormat.parse(timelength[0]);
                                Date time2 = displayFormat.parse(timelength[1]);
                                time1=parseFormat.parse(parseFormat.format(time1));
                                time2=parseFormat.parse(parseFormat.format(time2));

                                if(timelength[0].contains("PM")==true&&timelength[1].contains("AM")==true)
                                {
                                    Log.e("time2",""+time2);
                                    Date path=parseFormat.parse("12:00:00");
                                    long sum=path.getTime()+time2.getTime();
                                    Log.e("sum=",""+sum);
                                    time2= parseFormat.parse(parseFormat.format(new Date(sum)));

                                }

                              //  String time2e=parseFormat.format(time2);
                              /*  Log.e("time1",""+timelength[0]);
                                Log.e("time2",""+timelength[1]);
                                Log.e("Starttime",""+time1);
                                Log.e("time2",""+time2);*/
                                //Log.e("time2e",""+time2e);

                               // Date timeS=parseFormat.parse(timeLine.get(count).toString());
                                //Date timeE=parseFormat.parse(timeLine.get(count+1).toString());
                               // Log.e("timeS",""+timeS);
                                //Log.e("timeE",""+timeLine.get(count+1).toString());
                                //Date time1 = new SimpleDateFormat("HH:mm").parse(displayFormat.format(open_start));
                                //Date time2 = new SimpleDateFormat("HH:mm").parse(displayFormat.format(end_time));

                                Date timeS = timeLine.get(count);
                                Date timeE = timeLine.get(count+1);
                               /* Log.e("Starttime",""+time1);
                                Log.e("Endtime",""+time2);
                                Log.e("timeS",""+timeS);
                                Log.e("timeE",""+timeE);
                                Log.e("------","------");*/
                                if (timeS.after(time1) && timeS.before(time2) && timeE.before(time2)) {
                                    temp.add(hPlaceDetails.get("name"));
                                    String photo = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference="
                                            + hPlaceDetails.get("ref_photo") + "&key="
                                            + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc";
                                    b_name=hPlaceDetails.get("name");
                                    b_rating=hPlaceDetails.get("rating");
                                    b_lat=hPlaceDetails.get("lat");
                                    b_lng=hPlaceDetails.get("lng");
                                    b_phone=hPlaceDetails.get("formatted_phone");
                                    b_web=hPlaceDetails.get("website");
                                    b_opening_time=hPlaceDetails.get(FirstWeekday);
                                    b_photo=photo;
                                    id=cnt_poi_in_database.get(count).toString();
                                }

                            }

                        }


                    }
                    else if(hPlaceDetails.get(FirstWeekday).toString().contains("Open 24 hours")) {
                        temp.add(hPlaceDetails.get("name"));

                        String photo = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference="
                                + hPlaceDetails.get("ref_photo") + "&key="
                                + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc";
                        b_name=hPlaceDetails.get("name");
                        b_rating=hPlaceDetails.get("rating");
                        b_lat=hPlaceDetails.get("lat");
                        b_lng=hPlaceDetails.get("lng");
                        b_phone=hPlaceDetails.get("formatted_phone");
                        b_web=hPlaceDetails.get("website");
                        b_opening_time=hPlaceDetails.get(FirstWeekday);
                        b_photo=photo;
                        id=cnt_poi_in_database.get(count).toString();
                    }
                }

            } catch (Exception e) {
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
                int temp_cnt_database=cnt_poi_in_database.get(count)+1;
                Log.e("temp_cnt_database size",""+temp_cnt_database);
                cnt_poi_in_database.set(count,temp_cnt_database);
                B_tree_background b_tree_background=new B_tree_background(ctx);
                String count_S= Integer.toString(count);
                b_tree_background.execute(id,b_name,b_rating,b_lat,b_lng,b_phone,b_web,b_opening_time,b_photo,count_S);

            }
            else
            {
                int temp=Count_Google_IDs.get(count);
                int insert=temp+1;
                 Log.e("null temp=",""+temp);
                Count_Google_IDs.set(count,insert);
            }
            /*if(Count_Google_IDs.get(count)==GoogleIDlist.get(count))
            {
                if(findbestsol(0,firstplace.getLatLng().latitude,firstplace.getLatLng().longitude)==1)
                {
                    for(int i=0;i<firstmemberlists.size();i++)
                    {
                        Find_sec find_sec=new Find_sec(getContext());
                        find_sec.execute();
                    }

                }
            }*/
        }
    }



    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private ArrayList<String> mData;
        private ArrayList<String> photodata;
        private ArrayList<LatLng> latLngs;
        private ArrayList<String> all_poi_rating;
        private ArrayList<String> all_poi_website;
        private ArrayList<String> all_poi_phone;
        private ArrayList<String> all_poi_openingtime;
        private ArrayList<Date> timeLine;
        public class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
            public TextView mTextView;
            public ImageView IMG;
            public MapView mapView;
            public GoogleMap MgoogleMap;
            public TextView webview;
            public TextView phoneview;
            public TextView rankview;
            public TextView openingview;
            public Button mapbtn;
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
                mapbtn=(Button)v.findViewById(R.id.mapbtn);
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


        public MyAdapter(ArrayList<String> data, ArrayList<String> photo_data, ArrayList<LatLng> latLngs1,
                         ArrayList<String> all_poi_rating,
                         ArrayList<String> all_poi_openingtime,ArrayList<String> all_poi_website,
                         ArrayList<String> all_poi_phone,ArrayList<Date> alltimeLine)
        {
            mData = data;
            photodata = photo_data;
            latLngs = latLngs1;
            this.all_poi_openingtime=all_poi_openingtime;
            this.all_poi_phone=all_poi_phone;
            this.all_poi_website=all_poi_website;
            this.all_poi_rating=all_poi_rating;
            this.timeLine=alltimeLine;
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
            holder.mapbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Double lat=latLngs.get(position).latitude;
                    Double lng=latLngs.get(position).longitude;
                    String name=mData.get(position);
                    String uri="geo:"+lat+","+lng+"?q="+lat+","+lng+"(Google+"+name+")";
                    //Log.e("uri",uri);
                    Uri gmmIntentUri = Uri.parse(uri);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    ctx.startActivity(mapIntent);
                }
            });
            SimpleDateFormat s2=new SimpleDateFormat("HH:mm");
            s2.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            // SimpleDateFormat s1=new SimpleDateFormat("ss");
            holder.starttime.setText("抵達時間"+s2.format(timeLine.get(position)));

            Date path= null;
            try {
                SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                parseFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                Date timeS=parseFormat.parse(parseFormat.format(timeLine.get(position)));
                //Log.e("timeS",""+timeS);
                when_to_get_middle=timeS.getTime();
                //Log.e("times",""+when_to_get_middle);
                //Log.e("middle First",""+parseFormat.format(when_to_get_middle));
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
                timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                //Log.e("path",""+POI_length_list.get(position));
                Date length=timeFormat.parse(POI_length_list.get(position));
                when_to_get_middle+=length.getTime();
                //Log.e("path",""+when_to_get_middle);
                String sum = timeFormat.format(new Date(when_to_get_middle));
                Log.e("sum",""+parseFormat.parse(parseFormat.format(when_to_get_middle)));
                Date endtime=parseFormat.parse(parseFormat.format(when_to_get_middle));
                holder.endtime.setText("離開時間"+s2.format(endtime));
                //timeLine.add(parseFormat.parse(parseFormat.format(when_to_get_middle)));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder save=new AlertDialog.Builder(ctx);
                    save.setTitle("確定儲存這筆資料 ? ");
                    save.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
                            final EditText edittext = new EditText(ctx);
                            alert.setTitle("為此資料取名");
                            alert.setView(edittext);
                            alert.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //What ever you want to do with the value
                                    String temptable = edittext.getText().toString();
                                    MainActivity.save_result.add_database(temptable,mData
                                            ,photodata,latLngs,all_poi_rating,
                                            all_poi_phone,all_poi_website,all_poi_openingtime);
                                }
                            });
                            alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // what ever you want to do with No option.
                                }
                            });
                            alert.show();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();




                    return true;
                }
            });

            //long when_to_get_middle1=path.getTime();
            //long temp=when_to_get_middle+when_to_get_middle1;
            //Log.e("middle",""+parseFormat.format(temp));




           // holder.endtime.setText("離開時間"+s2.format(timeLine.get(position+1)));
            /*try {
                Date timeS=s2.parse(s2.format(timeLine.get(position)));
                when_to_get_middle=timeS.getTime();
                Date path=s2.parse(s2.format(POI_length_list.get(position)));
                when_to_get_middle+=path.getTime();
                //Log.e("middle",""+s2.format(when_to_get_middle));
                holder.endtime.setText("離開時間"+s2.format(when_to_get_middle));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

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

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }

    public class POIAdapter extends RecyclerView.Adapter<POIAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            public Button poi_choice;
            public Spinner length;
            final String[] limit = {"0.5 hr","1 hr", "1.5 hr", "2 hr","3 hr","6 hr","8 hr","12 hr"};
            public ViewHolder(View v) {
                super(v);
                poi_choice=(Button)v.findViewById(R.id.cardview_btn_POI);
                length=(Spinner)v.findViewById(R.id.spinner_poi);
                ArrayAdapter<String> lunchList = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        limit);
                length.setAdapter(lunchList);
            }
        }

        public POIAdapter() {

        }

        @Override
        public POIAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.poi_cardview, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.poi_choice.setText(POI_choice_list.get(position));
            holder.poi_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater1 = getActivity().getLayoutInflater();
                    final View v1 = inflater1.inflate(R.layout.poi_test, null);
                    final AlertDialog.Builder dialog_list = new AlertDialog.Builder(getActivity());
                    dialog_list.setView(v1).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            choice_of_poi = POIchoice.getText().toString();
                            //Toast.makeText(getContext(), "" + choice_of_poi, Toast.LENGTH_LONG).show();
                            holder.poi_choice.setText(choice_of_poi);
                            //Log.e("position",""+position);
                            POI_choice_list.set(position,choice_of_poi);
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

            holder.length.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   // SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm",Locale.ENGLISH);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    Date time;
                    switch (i)
                    {
                        case 0:
                            POI_length_list.set(position,"00:30:00");
                            break;
                        case 1:
                            POI_length_list.set(position,"01:00:00");
                            break;
                        case 2:
                            POI_length_list.set(position,"01:30:00");
                            break;
                        case 3:
                            POI_length_list.set(position,"02:00:00");
                            break;
                        case 4:
                            POI_length_list.set(position,"03:00:00");
                            break;
                        case 5:
                            POI_length_list.set(position,"06:00:00");
                            break;
                        case 6:
                            POI_length_list.set(position,"08:00:00");
                            break;
                        case 7:
                            POI_length_list.set(position,"12:00:00");
                            break;
                    }


                }

                @TargetApi(Build.VERSION_CODES.N)
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                    POI_length_list.set(position,"00:30:00");
                }
            });


        }

        @Override
        public int getItemCount() {
            return POI_choice_list.size();
        }

    }

}
