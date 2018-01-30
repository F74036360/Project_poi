package com.example.joan.place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joan on 2017/7/24.
 */
public class Place_Matrix {
    public List<HashMap<String, String>> parse(JSONObject jObject) {
        JSONArray jPlaces = null;
        try {
            /** Retrieves all the elements in the 'places' array */
            jPlaces = jObject.getJSONArray("rows");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getPlaces with the array of json object
         * where each json object represent a place
         */
        return getPlaces(jPlaces);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
        int placesCount = jPlaces.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> place = null;
        for (int i = 0; i < placesCount; i++) {
            try {
                /** Call getPlace with place JSON object to parse the place */
                place = getPlace((JSONObject) jPlaces.get(i));
                placesList.add(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject jPlace) {

        HashMap<String, String> place = new HashMap<String, String>();
        String Distance_text = "-NA-";
        String Distance_value="-NA-";
        String Duration_text = "-NA-";
        String Duration_value="-NA-";

        try {
            // Extracting Place name, if available
            if (!jPlace.isNull("elements")) {
                JSONArray obj = jPlace.getJSONArray("elements");
                Distance_text = obj.getJSONObject(0).getJSONObject("distance").getString("text");
                Distance_value = obj.getJSONObject(0).getJSONObject("distance").getString("value");
                Duration_text = obj.getJSONObject(0).getJSONObject("duration").getString("text");
                Duration_value = obj.getJSONObject(0).getJSONObject("duration").getString("value");
            }
            //Log.e("distance",""+Distance_text);

            place.put("distance_text", Distance_text);
            place.put("distance_value", Distance_value);
            place.put("duration_text", Duration_text);
            place.put("duration_value",Duration_value);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return place;
    }
}
