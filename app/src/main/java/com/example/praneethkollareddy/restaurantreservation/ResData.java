package com.example.praneethkollareddy.restaurantreservation;

/**
 * Created by INSPIRON on 19-Feb-16.
 */
public class ResData {
    private String address;
    private String cuisine;
    private String dollar_range;
    private String rating;
    private String waittime;
    private String name;

public ResData(){

    }

    public String getName(){
        return name;
            }
    public String getAddress(){
        return address;
    }
    public String getCuisine(){
        return cuisine;
    }
    public String getDollar_range(){
        return dollar_range;
    }
    public String getRating(){
        return rating;
    }
    public String getWaittime(){
        return waittime;
    }

}
