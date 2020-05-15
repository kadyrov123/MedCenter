package com.example.medcenter.service;

import java.text.ParseException;
import java.util.List;

public interface StatService {
    List<List<Object>> getNumberOfVisitsByDoctors();
    List<List<Object>> getNumberOfVisitsByMonths();
}
