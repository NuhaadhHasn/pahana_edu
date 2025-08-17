package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.LoginHistory;
import com.example.pahanaedu.service.IUserService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/login-history")
public class LoginHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;

    @Override
    public void init() {
        userService = ServiceFactory.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<LoginHistory> historyList = userService.getLoginHistory();
        request.setAttribute("historyList", historyList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("login-history.jsp");
        dispatcher.forward(request, response);
    }
}