package com.example.medcenter;

import com.example.medcenter.service.StatService;
import com.example.medcenter.service.StatServiceImpl;
import sun.rmi.server.LoaderHandler;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Test {
    public static void main(String[] args) {

//        LocalDate now = LocalDate.now(); // 2015-11-24
//        LocalDate earlier = now.minusMonths(1);
////        System.out.println(earlier.getMonth()); // java.time.Month = OCTOBER
////        System.out.println(earlier.getMonth().getValue()); // 10
////        System.out.println(earlier.getYear()); // 2015
//
//        List months = new ArrayList();
//
////        LocalDate now = LocalDate.now(); // 2015-11-24
////        LocalDate earlier = now.minusMonths(1); // 2015-10-24
//        for(int i=0 ; i<6 ; i++){
//            months.add(now.minusMonths(i).getMonthValue());
//        }
//        for(int j = 0 ; j <months.size() ; j++){
//            System.out.println(months.get(j));
//        }

        StatService stat = new StatServiceImpl();

        List<List<Object>> s = null;
//        try {
//            s = stat.getNumberOfVisitsByMonths();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
            s = stat.getNumberOfVisitsByMonths();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        for(List t : s){
//            System.out.println(t.get(0) +" - "+ t.get(1));
//        }

    }
}
