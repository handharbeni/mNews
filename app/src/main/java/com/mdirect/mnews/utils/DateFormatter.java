package com.mdirect.mnews.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatter {
    public DateFormatter(){

    }
    public String format(String inputString){
        String reformattedStr = "";
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MMM yyyy");

        try {

            reformattedStr = myFormat.format(fromUser.parse(inputString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reformattedStr;
    }
}
