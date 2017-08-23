package com.example.joan.place;

/**
 * Created by Joan on 2017/7/24.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaceDetail {

    /** Receives a JSONObject and returns a list */
    public HashMap<String,String> parse(JSONObject jObject){

        JSONObject jPlaceDetails = null;
        try {
            /** Retrieves all the elements in the 'places' array */
            jPlaceDetails = jObject.getJSONObject("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getPlaces with the array of json object
         * where each json object represent a place
         */
        return getPlaceDetails(jPlaceDetails);
    }

    /** Parsing the Place Details Object object */
    private HashMap<String, String> getPlaceDetails(JSONObject jPlaceDetails){

        HashMap<String, String> hPlaceDetails = new HashMap<String, String>();

        String name = "-NA-";
        String icon = "-NA-";
        String vicinity="-NA-";
        String latitude="-NA-";
        String longitude="-NA-";
        String formatted_address="-NA-";
        String formatted_phone="-NA-";
        String website="-NA-";
        String rating="-NA-";
        String international_phone_number="-NA-";
        String url="-NA-";

        try {
            // Extracting Place name, if available
            if(!jPlaceDetails.isNull("name")){
                name = jPlaceDetails.getString("name");
            }

            // Extracting Icon, if available
            if(!jPlaceDetails.isNull("icon")){
                icon = jPlaceDetails.getString("icon");
            }

            // Extracting Place Vicinity, if available


            // Extracting Place formatted_address, if available
            if(!jPlaceDetails.isNull("formatted_address")){
                formatted_address = jPlaceDetails.getString("formatted_address");
            }

            // Extracting Place formatted_phone, if available
            if(!jPlaceDetails.isNull("formatted_phone_number")){
                formatted_phone = jPlaceDetails.getString("formatted_phone_number");
            }

            // Extracting website, if available
            if(!jPlaceDetails.isNull("website")){
                website = jPlaceDetails.getString("website");
            }

            // Extracting rating, if available
            if(!jPlaceDetails.isNull("rating")){
                rating = jPlaceDetails.getString("rating");
            }

            // Extracting rating, if available
            if(!jPlaceDetails.isNull("international_phone_number")){
                international_phone_number = jPlaceDetails.getString("international_phone_number");
            }

            // Extracting url, if available
            if(!jPlaceDetails.isNull("url")){
                url = jPlaceDetails.getString("url");
            }

            latitude = jPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = jPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lng");
            //Log.e("lat",""+latitude);
            ///Log.e("lng",""+longitude);

            ArrayList<String> myList = new ArrayList<String>();
            if(!jPlaceDetails.isNull("opening_hours"))
            {
                JSONObject weekday=jPlaceDetails.getJSONObject("opening_hours");
                if(!weekday.isNull("weekday_text"))
                {
                    JSONArray arr=weekday.getJSONArray("weekday_text");
                    for(int i=0;i<arr.length();i++)
                    {
                        String temp=arr.getString(i);
                        switch (i)
                        {
                            case 6:
                                hPlaceDetails.put("Sunday",temp);
                                break;
                            case 0:
                                hPlaceDetails.put("Monday", temp);
                                break;
                            case 1:
                                hPlaceDetails.put("Tuesday", temp);
                                break;
                            case 2:
                                hPlaceDetails.put("Wednesday", temp);
                                break;
                            case 3:
                                hPlaceDetails.put("Thursday", temp);
                                break;
                            case 4:
                                hPlaceDetails.put("Friday", temp);
                                break;
                            case 5:
                                hPlaceDetails.put("Saturday", temp);
                                break;

                        }
                    }
                }
                else
                {
                    String temp="null";
                    hPlaceDetails.put("Sunday","null");
                    hPlaceDetails.put("Monday", "null");
                    hPlaceDetails.put("Tuesday", temp);
                    hPlaceDetails.put("Wednesday", temp);
                    hPlaceDetails.put("Thursday", temp);
                    hPlaceDetails.put("Friday", temp);
                    hPlaceDetails.put("Saturday", temp);
                }
            }

            if(!jPlaceDetails.isNull("photos"))
            {
                JSONArray ref=jPlaceDetails.getJSONArray("photos");
                JSONObject temp=ref.getJSONObject(0);
                String ref_photo=temp.getString("photo_reference");
                hPlaceDetails.put("ref_photo",ref_photo);
            }

            hPlaceDetails.put("name", name);
            hPlaceDetails.put("icon", icon);
            hPlaceDetails.put("lat", latitude);
            hPlaceDetails.put("lng", longitude);
            hPlaceDetails.put("formatted_address", formatted_address);
            hPlaceDetails.put("formatted_phone", formatted_phone);
            hPlaceDetails.put("website", website);
            hPlaceDetails.put("rating", rating);
            hPlaceDetails.put("international_phone_number", international_phone_number);
            hPlaceDetails.put("url", url);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hPlaceDetails;
    }
}