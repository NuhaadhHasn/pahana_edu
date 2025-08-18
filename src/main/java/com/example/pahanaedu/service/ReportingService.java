package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.ReportingDAO;

import java.util.List;
import java.util.Map;

public class ReportingService implements IReportingService {
    private final ReportingDAO reportingDAO;

    public ReportingService() {
        this.reportingDAO = new ReportingDAO();
    }

    @Override
    public double getTotalSales() {
        return reportingDAO.getTotalSales();
    }

    @Override
    public List<Map<String, Object>> getTopSellingItems(int limit) {
        return reportingDAO.getTopSellingItems(limit);
    }
}