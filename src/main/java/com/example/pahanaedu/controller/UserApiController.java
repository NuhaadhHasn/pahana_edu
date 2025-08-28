package com.example.pahanaedu.controller;

import com.example.pahanaedu.service.IUserService;
import com.example.pahanaedu.service.ServiceFactory;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/users/check-username")
public class UserApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;

    @Override
    public void init() {
        userService = ServiceFactory.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        System.out.println("--- API CHECK: Received username to check: '" + username + "' ---");

        boolean exists = userService.usernameExists(username);
        System.out.println("--- API CHECK: Service returned: " + exists + " ---");

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("exists", exists);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }
}