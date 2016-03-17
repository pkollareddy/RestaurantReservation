package com.example.praneethkollareddy.restaurantreservation.databeans;

/**
 * Created by INSPIRON on 19-Feb-16.
 */
public class ResData {
    private String name,cuisine,dollar_range,rating,waittime,street,city,state, zipcode, latitude, longitude,image;

public ResData(){

    }

public String getImage(){
    return image;
}

    public String getName(){
        return name;
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
    public String getStreet(){
        return street;
    }
    public String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }
    public String getZipcode(){
        return zipcode;
    }
    public String getLongitude(){
        return longitude;
    }
    public String getLatitude(){
        return latitude;
    }
    public String getWaittime(){
        return waittime;
    }



}
