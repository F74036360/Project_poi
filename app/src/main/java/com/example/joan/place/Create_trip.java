package com.example.joan.place;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Create_trip extends Fragment {
    Button createfirst;
    Button buttonmap;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int mYear,mMonth,mDate,mHour,mMinute;
    private  Button doSetDate;
    private  Button doSetTime;
    private  Button pickplace;
    private  EditText setTimeDuration;
    private  Button POI;
    private GoogleApiClient client;
    public List<HashMap<String, String>> mainlist = null;
    private  ImageButton poiitem_restaurant;
    private  ImageButton poiitem_travel;
    private  ImageButton poiitem_bank;
    private  ImageButton poiitem_salon;
    private  ImageButton poiitem_hospital;
    private  ImageButton poiitem_hotel;
    private  ImageButton poiitem_book;
    private  ImageButton poiitem_cafe;
    private  ImageButton poiitem_mall;
    //private  ImageView img_trip;
    private  Button poi_self_ok;
    private TextView POIchoice;
    private EditText self_choice_typed;
    private String choice_of_poi;
    private  Place firstplace;
    private String msg;
    private String formatDate;
    private String formatTime;
    private String visitDuration;
    ArrayList<String> myDataset = new ArrayList<>();
    ArrayList<String> ref_dataset=new ArrayList<>();
    ArrayList<LatLng> latlng_card=new ArrayList<>();
    private final static int PLACE_PICKER_REQUEST = 1;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    View view;
    MyAdapter myAdapter;
    public Create_trip() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_create_trip,container,false);
        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
        //Context context=getContext();
        //img_trip=(ImageView)view.findViewById(R.id.img_trip);
        //Picasso.with(getContext()).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc").into(img_trip);
        createfirst=(Button)view.findViewById(R.id.first_trip_button);
        POI=(Button)view.findViewById(R.id.POI);
        createfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1=getActivity().getLayoutInflater();
                final View v=inflater1.inflate(R.layout.alert_choose_firat_location,null);
                new AlertDialog.Builder(getActivity()).setTitle("起點選擇")
                        .setView(v).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        visitDuration=setTimeDuration.getText().toString();
                        Toast.makeText(getContext(),formatDate+"\n"+formatTime+"\n"+msg+"\n"+visitDuration,Toast.LENGTH_LONG).show();
                    }
                }).show();
                pickplace=(Button)v.findViewById(R.id.pickplace);
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

                doSetDate=(Button)v.findViewById(R.id.datepicker_first);
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
                                formatDate =setDateFormat(year,month,day);
                                doSetDate.setText(formatDate);
                            }

                        }, mYear,mMonth, mDate).show();
                    }
                });

                doSetTime=(Button)v.findViewById(R.id.timepicker_first);
                doSetTime.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int hour = c.get(Calendar.HOUR_OF_DAY);
                        int minute = c.get(Calendar.MINUTE);
                        // Create a new instance of TimePickerDialog and return it
                        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener(){

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                formatTime=hourOfDay+":"+minute;
                                doSetTime.setText(formatTime);

                            }
                        }, hour, minute, false).show();
                    }
                });
                setTimeDuration=(EditText)v.findViewById(R.id.time_duration_edittext);
            }
        });

        POI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1=getActivity().getLayoutInflater();
                final View v1=inflater1.inflate(R.layout.poi_test,null);
                final AlertDialog.Builder dialog_list=new AlertDialog.Builder(getActivity());
                dialog_list.setTitle("POI").setView(v1).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        choice_of_poi=POIchoice.getText().toString();
                        Toast.makeText(getContext(),""+choice_of_poi,Toast.LENGTH_LONG).show();
                        getrelative(firstplace,choice_of_poi);

                    }
                }).show();

                poiitem_restaurant=(ImageButton)v1.findViewById(R.id.restaurant);
                setPoiitem_restaurant();
                poiitem_salon=(ImageButton)v1.findViewById(R.id.salon);
                setPoiitem_salon();
                poiitem_cafe=(ImageButton)v1.findViewById(R.id.cafe);
                setpoiitem_cafe();
                poiitem_travel=(ImageButton)v1.findViewById(R.id.travelspot);
                setPoiitem_travel();
                poiitem_hospital=(ImageButton)v1.findViewById(R.id.hospital);
                setPoiitem_hospital();
                poiitem_hotel=(ImageButton)v1.findViewById(R.id.hotel);
                setPoiitem_hotel();
                poiitem_bank=(ImageButton)v1.findViewById(R.id.bank);
                setPoiitem_bank();
                poiitem_book=(ImageButton)v1.findViewById(R.id.bookstore);
                setPoiitem_book();
                poiitem_mall=(ImageButton)v1.findViewById(R.id.department_store);
                setPoiitem_mall();
                self_choice_typed=(EditText)v1.findViewById(R.id.self_typed);
                POIchoice=(TextView)v1.findViewById(R.id.POI_choice);
                poi_self_ok=(Button)v1.findViewById(R.id.self_OK);
                set_selfok();

            }
        });


        return view;
    }

    private void getrelative(Place known, String poi) {
        String url = getUrl(known.getLatLng(),poi);
        PlacesTask task = new PlacesTask();
        task.execute(url);

    }

    private void set_selfok() {
        poi_self_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText(""+self_choice_typed.getText());
            }
        });
    }


    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==PLACE_PICKER_REQUEST)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                firstplace = PlacePicker.getPlace(data, getActivity());
                msg=String.format("Place: %s",firstplace.getName());
                pickplace.setText(msg);
                Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
            }

        }
    }

    private void setPoiitem_restaurant()
    {
        poiitem_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("restaurant");
            }
        });
    }

    private void setPoiitem_travel()
    {
        poiitem_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("景點");
            }
        });
    }

    private void setPoiitem_bank()
    {
        poiitem_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("銀行");
            }
        });
    }

    private void setPoiitem_salon()
    {
        poiitem_salon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("理髮廳");
            }
        });
    }

    private void setPoiitem_hospital()
    {
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

    private void setPoiitem_mall(){
        poiitem_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                POIchoice.setText("賣場");
            }
        });
    }



    private String getUrl(LatLng latlng, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location="+latlng.latitude+","+latlng.longitude);
        googlePlacesUrl.append("&rankby=distance");
        googlePlacesUrl.append("&keyword=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    private String getUrl_detail(String Place_id)
    {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        googlePlacesUrl.append("placeid="+Place_id);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    private String downloadUrl(String strUrl) throws IOException {
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
            Log.d("Exception while downloading url", "" + e.toString());
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


    private class PlacesTask extends AsyncTask<String, Integer, String> {
        String data = null;

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

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>>
    {
        JSONObject jObject;
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData)
        {

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
            Log.d("Map", "list size: " + list.size());
            mainlist = list;
            ArrayList<String> mdataset1 = new ArrayList<>();
            ArrayList<String> mdataset2 = new ArrayList<>();
            // Clears all the existing markers;
            for (int i = 0; i < list.size(); i++) {
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));
                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));
                // Getting name
                String name = hmPlace.get("place_name");
                Log.d("Map", "place: " + name);
                myDataset.add(name);
                LatLng latLng = new LatLng(lat, lng);
                Log.d("latlng", "" + latLng);
                latlng_card.add(latLng);
                Log.d("place_id",""+hmPlace.get("place_id"));
                String url = getUrl_detail(hmPlace.get("place_id").toString());
                PlacesTaskDetail task = new PlacesTaskDetail();
                task.execute(url);

            }

        }
    }

    private class PlacesTaskDetail extends AsyncTask<String, Integer, String>{

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            ParserTaskDetail parserTask = new ParserTaskDetail();
            parserTask.execute(result);

        }
    }

    /** A class to parse the Google Place Details in JSON format */
    private class ParserTaskDetail extends AsyncTask<String, Integer, HashMap<String,String>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected HashMap<String,String> doInBackground(String... jsonData) {

            HashMap<String, String> hPlaceDetails = null;
            PlaceDetail placeDetailsJsonParser = new PlaceDetail();

            try{
                jObject = new JSONObject(jsonData[0]);
                // Start parsing Google place details in JSON format
                hPlaceDetails = placeDetailsJsonParser.parse(jObject);
                String photo="https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference="
                        +hPlaceDetails.get("ref_photo")+"&key=" + "AIzaSyDomABgA1RgXQaE31JakIQi9Cw66nhHGAc";
                ref_dataset.add(photo);
                //Log.e("photo",photo);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return hPlaceDetails;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(HashMap<String,String> hPlaceDetails){

            if(ref_dataset.size()==myDataset.size())
            {
                myAdapter = new MyAdapter(myDataset,ref_dataset,latlng_card);
                RecyclerView mList = (RecyclerView) view.findViewById(R.id.list_view);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mList.setLayoutManager(layoutManager);
                mList.setAdapter(myAdapter);
            }


        }
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mData;
        private List<String> photodata;
        private List<LatLng> latLngs;



        public class ViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
            public TextView mTextView;
            public ImageView IMG;
            public MapView mapView;
            public GoogleMap MgoogleMap;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.info_text);
                IMG=(ImageView)v.findViewById(R.id.img);
                mapView=(MapView)v.findViewById(R.id.map_card);
            }

            @Override
            public void onMapReady(GoogleMap googleMap) {
                MgoogleMap=googleMap;
                LatLng sydney = new LatLng(-34, 151);
                MgoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                MgoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }

        public MyAdapter(List<String> data,List<String> photo_data,List<LatLng> latLngs1) {
            mData = data;
            photodata=photo_data;
            latLngs=latLngs1;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.forcard, parent, false);
            ViewHolder vh = new ViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mTextView.setText(mData.get(position));
            Picasso.with(getContext()).load(photodata.get(position)).resize(1000,600)
                    .into(holder.IMG);
            holder.mapView.onCreate(null);
            holder.mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng sydney = latLngs.get(position);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title(mData.get(position)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                }
            });
            holder.mapView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MapsActivity maps=new MapsActivity(latLngs.get(position));
                    Intent intent = new Intent(getActivity(),maps.getClass());
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

    }







}
