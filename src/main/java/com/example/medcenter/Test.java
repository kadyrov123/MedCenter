package com.example.medcenter;

import sun.rmi.server.LoaderHandler;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        LocalDate date  = LocalDate.now();
        java.util.Date today =  Calendar.getInstance().getTime();
//        System.out.println(today);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        Time startTime = new Time(8,0,0);
//        System.out.println(startTime);
//        System.out.println(date);

        date.atStartOfDay();

        date.atTime(8,0,0);

        System.out.println(date);

        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date("01.01.2010"));

        Time start = new Time(8,0,0);
        Time end = new Time(17,0,0);
        List<LocalTime> times = new ArrayList<>();
        LocalTime startTime = LocalTime.parse("08:00:00");
        LocalTime endTime  = LocalTime.parse("12:12:00");
//        System.out.println(startTime);
//        System.out.println(startTime.plusMinutes(10));



        LocalTime localTime = startTime;
        while(0 < (endTime.compareTo(localTime))){
            times.add(localTime);
            localTime = localTime.plusMinutes(20);

        }


//        for(LocalTime l: times){
//            System.out.println(l);
//        }
        for (int i=0 ; i<times.size() ; i++){
            if(i!=times.size()-1){
                System.out.println(times.get(i)+" - "+times.get(i+1));
            }
        }

    }
}
