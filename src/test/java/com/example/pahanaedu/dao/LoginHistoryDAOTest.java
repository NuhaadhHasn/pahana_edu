package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.LoginHistory;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginHistoryDAOTest {

    @Test
    public void testLogAttempt() {
        LoginHistoryDAO loginHistoryDAO = new LoginHistoryDAO();

        // We can use a real user ID from our sample data
        int userId = 1;
        String status = "SUCCESS";

        boolean result = loginHistoryDAO.logAttempt(userId, status);

        assertTrue("Login attempt should be logged successfully.", result);
    }
}