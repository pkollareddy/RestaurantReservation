package com.example.praneethkollareddy.restaurantreservation.databeans;

/**
 * Created by praneethkollareddy on 2/20/16.
 */
public class Reservation {

    private String name;
    private String email;
    private String phone;
    private int party;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    String rName;
    String genTime;

    public Reservation() {}

    public Reservation(String name, String email, String phone, int party, int year, int month, int day, int hour, int minute, String rName, String genTime) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.party = party;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.rName = rName;
        this.genTime = genTime;
    }

    public String getGenTime(){

        return genTime;
    }

    public String getrName(){
        return rName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getParty() {
        return party;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

}
