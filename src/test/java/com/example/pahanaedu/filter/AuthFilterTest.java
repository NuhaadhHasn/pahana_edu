package com.example.pahanaedu.filter;

import com.example.pahanaedu.model.User;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Proxy;

import static org.junit.Assert.assertEquals;

/**
 * Tests the access rules of AuthFilter without a server: the request, session, response and filter chain
 * are small fakes made with java.lang.reflect.Proxy, so no extra library is needed.
 */
public class AuthFilterTest {

    private static final String CONTEXT = "/pahana-edu";

    /** What the filter did with a request. */
    private static String run(String servletPath, String uriSuffix, String role) throws Exception {
        final String[] outcome = {"?"};
        final User user;
        if (role == null) {
            user = null;
        } else {
            user = new User();
            user.setRole(role);
        }
        HttpSession session = (HttpSession) Proxy.newProxyInstance(AuthFilterTest.class.getClassLoader(),
                new Class<?>[]{HttpSession.class},
                (proxy, method, args) -> "getAttribute".equals(method.getName()) && "user".equals(args[0]) ? user : null);
        HttpServletRequest request = (HttpServletRequest) Proxy.newProxyInstance(AuthFilterTest.class.getClassLoader(),
                new Class<?>[]{HttpServletRequest.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "getServletPath": return servletPath;
                        case "getPathInfo": return null;
                        case "getContextPath": return CONTEXT;
                        // the raw URI, as sent by the browser: may carry ";x.css" style path parameters
                        case "getRequestURI": return CONTEXT + servletPath + uriSuffix;
                        case "getSession": return user == null ? null : session;
                        default: return null;
                    }
                });
        HttpServletResponse response = (HttpServletResponse) Proxy.newProxyInstance(AuthFilterTest.class.getClassLoader(),
                new Class<?>[]{HttpServletResponse.class},
                (proxy, method, args) -> {
                    if ("sendRedirect".equals(method.getName())) outcome[0] = "redirect to " + args[0];
                    if ("sendError".equals(method.getName())) outcome[0] = "error " + args[0];
                    return null;
                });
        FilterChain chain = (ServletRequest req, ServletResponse res) -> outcome[0] = "allowed";
        new AuthFilter().doFilter(request, response, chain);
        return outcome[0];
    }

    private static final String TO_LOGIN = "redirect to " + CONTEXT + "/login";

    @Test
    public void anonymousVisitorsCanOnlySeeLoginAndStaticFiles() throws Exception {
        assertEquals("allowed", run("/login", "", null));
        assertEquals("allowed", run("/login.jsp", "", null));
        assertEquals("allowed", run("/css/style.css", "", null));
        assertEquals("allowed", run("/images/background.jpg", "", null));
        assertEquals(TO_LOGIN, run("/users", "", null));
        assertEquals(TO_LOGIN, run("/login-history", "", null));   // used to count as the public path "/login"
        assertEquals(TO_LOGIN, run("/dashboard.jsp", "", null));
    }

    @Test
    public void aFakeFileEndingOrPathParameterDoesNotSkipTheLoginCheck() throws Exception {
        assertEquals(TO_LOGIN, run("/users", ";x.css", null));       // /users;x.css
        assertEquals(TO_LOGIN, run("/users", ";.js", null));
        assertEquals(TO_LOGIN, run("/users.js", "", null));
        assertEquals(TO_LOGIN, run("/reports", ";jsessionid=1.css", null));
    }

    @Test
    public void adminReachesAdminAndStaffPages() throws Exception {
        assertEquals("allowed", run("/users", "", "ADMIN"));
        assertEquals("allowed", run("/login-history", "", "ADMIN"));
        assertEquals("allowed", run("/billing", "", "ADMIN"));
        assertEquals("allowed", run("/dashboard.jsp", "", "ADMIN"));
    }

    @Test
    public void staffGetBillDetailsAndTheSearchApiButNotAdminPages() throws Exception {
        assertEquals("allowed", run("/billing", "", "STAFF"));
        assertEquals("allowed", run("/view-bill-details", "", "STAFF"));
        assertEquals("allowed", run("/api/search-items", "", "STAFF"));
        assertEquals("error 403", run("/users", "", "STAFF"));
        assertEquals("error 403", run("/login-history", "", "STAFF"));
        assertEquals("error 403", run("/users", ";x.css", "STAFF"));
    }

    @Test
    public void customersOnlyGetTheirOwnPages() throws Exception {
        assertEquals("allowed", run("/customer-dashboard.jsp", "", "CUSTOMER"));
        assertEquals("allowed", run("/my-bills", "", "CUSTOMER"));
        assertEquals("allowed", run("/view-items", "", "CUSTOMER"));
        assertEquals("allowed", run("/customer-make-payment", "", "CUSTOMER"));
        assertEquals("error 403", run("/items", "", "CUSTOMER"));      // the item management servlet
        assertEquals("error 403", run("/customers", "", "CUSTOMER"));
        assertEquals("error 403", run("/login-history", "", "CUSTOMER"));
        assertEquals("error 403", run("/items", ";x.css", "CUSTOMER"));
    }
}
