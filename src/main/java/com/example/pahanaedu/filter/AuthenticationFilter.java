package com.example.pahanaedu.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A Servlet Filter that acts as a gatekeeper for our application.
 * It manages access control for both authenticated and unauthenticated users.
 */
@WebFilter("/*") // This filter intercepts ALL requests.
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // false = do not create a new session if one doesn't exist

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Check if the user is currently logged in
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        // Define pages that are only for logged-out users
        boolean isLoginPath = path.equals("/login") || path.equals("/login.jsp");

        // Define truly public assets that anyone can access
        boolean isPublicAsset = path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png");


        if (isLoginPath) {
            if (isLoggedIn) {
                // SCENARIO 1: User is already logged in but tries to access the login page.
                // ACTION: Redirect them to the dashboard.
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/dashboard.jsp");
            } else {
                // SCENARIO 2: User is not logged in and is accessing the login page.
                // ACTION: Allow the request to proceed.
                chain.doFilter(request, response);
            }
        } else if (isPublicAsset) {
            // SCENARIO 3: Request is for a public asset like CSS.
            // ACTION: Allow the request to proceed.
            chain.doFilter(request, response);
        } else {
            // All other pages are considered protected
            if (isLoggedIn) {
                // SCENARIO 4: User is logged in and accessing a protected page.
                // ACTION: Allow the request to proceed.
                chain.doFilter(request, response);
            } else {
                // SCENARIO 5: User is NOT logged in and is trying to access a protected page.
                // ACTION: Redirect them to the login page.
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }
        }
    }

    // Other filter methods (init, destroy) can be left empty.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}