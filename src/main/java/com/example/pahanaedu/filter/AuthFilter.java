package com.example.pahanaedu.filter;

import com.example.pahanaedu.model.User; // Make sure this import is present

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * A robust filter that handles both Authentication (are you logged in?)
 * and Authorization (do you have permission to be here?).
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    // Define which paths are accessible to which roles

    private static final List<String> PUBLIC_PATHS = Arrays.asList("/login", "/logout");
    private static final List<String> ADMIN_PATHS = Arrays.asList("/users", "/promotions", "/login-history", "/reports");
    private static final List<String> STAFF_PATHS = Arrays.asList("/customers", "/items", "/billing", "/bill-history", "/record-payment");
    private static final List<String> CUSTOMER_PATHS = Arrays.asList("/customer-dashboard.jsp", "/my-bills", "/view-items", "/make-payment", "/view-bill-details", "/customer-make-payment");
    private static final List<String> SHARED_API_PATHS = Arrays.asList("/api/dashboard-stats", "/api/notifications/mark-as-read", "/notifications");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Allow public assets and the login page for everyone, always.
        if (path.startsWith("/login") || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow the logout page for everyone who is logged in.
        if (path.startsWith("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        if (session == null || session.getAttribute("user") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String userRole = currentUser.getRole();

        boolean isAuthorized = false;

        if ("ADMIN".equals(userRole)) {
            // Admin can access ADMIN, STAFF, SHARED_API paths, and their dashboard.
            if (path.startsWith("/dashboard.jsp") || isPathAllowed(path, ADMIN_PATHS) || isPathAllowed(path, STAFF_PATHS) || isPathAllowed(path, SHARED_API_PATHS)) {
                isAuthorized = true;
            }
        } else if ("STAFF".equals(userRole)) {
            // Staff can access STAFF, SHARED_API paths, and their dashboard.
            if (path.startsWith("/dashboard.jsp") || isPathAllowed(path, STAFF_PATHS) || isPathAllowed(path, SHARED_API_PATHS)) {
                isAuthorized = true;
            }
        } else if ("CUSTOMER".equals(userRole)) {
            // Customer can only access CUSTOMER paths.
            if (isPathAllowed(path, CUSTOMER_PATHS)) {
                isAuthorized = true;
            }
        }

        if (isAuthorized) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to access this page.");
        }
    }

    private boolean isPathAllowed(String path, List<String> allowedPaths) {
        for (String allowedPath : allowedPaths) {
            if (path.startsWith(allowedPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}