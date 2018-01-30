package com.example.joan.place;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class First extends Fragment {
    Button profession;
    Button hito_spot;
    Button hito_restaurant;
    public First() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Mainview=inflater.inflate(R.layout.fragment_first,container,false);
        profession=(Button) Mainview.findViewById(R.id.profession);
        hito_spot=(Button)Mainview.findViewById(R.id.hito);
        hito_restaurant=(Button)Mainview.findViewById(R.id.restaurant);
        profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        hito_spot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        hito_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return Mainview;
    }


}
