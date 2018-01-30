package com.example.joan.place;

/**
 * Created by Joan on 2017/9/13.
 */
public class Firstmemberlist {
    public String name;
    public double lat;
    public double lng;
    public int id;
    public Firstmemberlist(String name,double lat,double lng,int id)
    {
        this.name=name;
        this.lat=lat;
        this.lng=lng;
        this.id=id;
    }
    public String getname()
    {
        return name;
    }
    public int getid()
    {
        return id;
    }
    public double getLat()
    {
        return lat;
    }
    public double getLng()
    {
        return lng;
    }
}
