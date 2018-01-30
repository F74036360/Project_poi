package com.example.joan.place;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Taiwan extends Fragment {
    TextView South;
    TextView North;
    TextView East;
    TextView Mid;
    ImageButton btn_north;
    ImageButton btn_south;
    ImageButton btn_east;
    ImageButton btn_mid;

    public View mainview;
    public Taiwan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mainview=inflater.inflate(R.layout.fragment_taiwan, container, false);
        South=(TextView) mainview.findViewById(R.id.text_south);
        North=(TextView) mainview.findViewById(R.id.text_teipei);
        East=(TextView) mainview.findViewById(R.id.text_east);
        Mid=(TextView) mainview.findViewById(R.id.text_mid);
       /* btn_north=(ImageButton)mainview.findViewById(R.id.image_taipei);
        btn_east=(ImageButton) mainview.findViewById(R.id.image_east);
        btn_south=(ImageButton) mainview.findViewById(R.id.image_south);
        btn_mid=(ImageButton) mainview.findViewById(R.id.image_mid);*/

        South.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("south","south");
                South fragment = new South();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.for_change_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        North.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("north","north");
                North fragment = new North();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.for_change_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        Mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("mid","mid");
                Mid fragment = new Mid();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.for_change_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        East.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("east","east");
                East fragment = new East();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.for_change_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        /*btn_east.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("east","mid");
            }
        });
        btn_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("mid","mid");
            }
        });
        btn_south.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("north","north");
            }
        });
        btn_north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("north","north");
            }
        });*/

        return mainview;
    }

}
