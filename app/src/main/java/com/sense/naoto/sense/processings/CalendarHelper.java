package com.sense.naoto.sense.processings;

import java.util.Calendar;

public class CalendarHelper {

    public static String getNowDate(){
        //Now
        Calendar calendar = Calendar.getInstance();
        String date_string = getStringDate(calendar);
        return date_string;
    }

    private static String getStringDate(Calendar calendar){
        //ここは日にちを受け取りそれを文字列にして前しているこのクラスの中枢
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        //monthはJanuaryが0
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        StringBuffer buffer = new StringBuffer();
        buffer.append(year);
        buffer.append("/");
        buffer.append(month);
        buffer.append("/");
        buffer.append(day);
        String date_string = buffer.toString();
        return date_string;
    }
}
