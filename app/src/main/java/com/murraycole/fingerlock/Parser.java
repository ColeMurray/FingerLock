package com.murraycole.fingerlock;

/**
 * Created by Cole on 10/17/2014.
 */
public class Parser {
    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String second;

    public Parser(String time) {


        year = time.substring(0, 4);
        month = time.substring(4, 6);
        day = time.substring(6, 8);
        hour = time.substring(9, 11);
        minute = time.substring(11, 13);
        second = time.substring(13, 15);
    }
    public String parseTime()
    {
        String hour12Format = String.valueOf(Integer.valueOf(hour) % 12);

        String finalTime = hour12Format + ":" + minute + ":" + second + " " + month + " " + day + " "  + year;

        return finalTime;


    }
}
