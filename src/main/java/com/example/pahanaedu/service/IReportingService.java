package com.example.pahanaedu.service;

import java.util.List;
import java.util.Map;

public interface IReportingService {
    double getTotalSales();

    List<Map<String, Object>> getTopSellingItems(int limit);
}