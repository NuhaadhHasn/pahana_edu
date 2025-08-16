package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Promotion;
import com.example.pahanaedu.service.PromotionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.servlet.RequestDispatcher;
import java.util.List;

@WebServlet("/promotions")
public class PromotionController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PromotionService promotionService;

    @Override
    public void init() {
        promotionService = new PromotionService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "add" : action;

        switch (action) {
            case "update":
                updatePromotion(request, response);
                break;
            case "add":
            default:
                addPromotion(request, response);
                break;
        }
    }

    private void addPromotion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String promoCode = request.getParameter("promoCode");
        double discount = Double.parseDouble(request.getParameter("discountPercentage"));
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        boolean isActive = "on".equals(request.getParameter("isActive"));

        Promotion promo = new Promotion();
        promo.setPromoCode(promoCode);
        promo.setDiscountPercentage(discount);
        promo.setActive(isActive);

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                promo.setStartDate(LocalDate.parse(startDateStr));
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                promo.setEndDate(LocalDate.parse(endDateStr));
            }
        } catch (DateTimeParseException e) {
            // Handle date parsing error, maybe redirect back with an error message
            e.printStackTrace();
        }

        promotionService.addPromotion(promo);
        response.sendRedirect(request.getContextPath() + "/promotions");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "list" : action;

        switch (action) {
            // We will add 'edit' and 'delete' cases later
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deletePromotion(request, response);
                break;
            case "list":
            default:
                listPromotions(request, response);
                break;
        }
    }

    private void listPromotions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Promotion> promotionList = promotionService.getAllPromotions();
        request.setAttribute("promotionList", promotionList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("promotion-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("promotion-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Promotion existingPromo = promotionService.getPromotionById(id);
        request.setAttribute("promotion", existingPromo);
        // We reuse the same form for editing
        RequestDispatcher dispatcher = request.getRequestDispatcher("promotion-form.jsp");
        dispatcher.forward(request, response);
    }

    private void updatePromotion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int promoId = Integer.parseInt(request.getParameter("promoId"));
        String promoCode = request.getParameter("promoCode");
        double discount = Double.parseDouble(request.getParameter("discountPercentage"));
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        boolean isActive = "on".equals(request.getParameter("isActive"));

        Promotion promo = new Promotion();
        promo.setPromoId(promoId);
        promo.setPromoCode(promoCode);
        promo.setDiscountPercentage(discount);
        promo.setActive(isActive);

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                promo.setStartDate(LocalDate.parse(startDateStr));
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                promo.setEndDate(LocalDate.parse(endDateStr));
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        promotionService.updatePromotion(promo);
        response.sendRedirect(request.getContextPath() + "/promotions");
    }

    private void deletePromotion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        promotionService.deletePromotion(id);
        response.sendRedirect(request.getContextPath() + "/promotions");
    }
}