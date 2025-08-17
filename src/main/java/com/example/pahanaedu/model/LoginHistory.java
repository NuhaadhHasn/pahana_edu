package com.example.pahanaedu.model;

import java.time.LocalDateTime;
import java.util.Date;

public class LoginHistory {
    private int logId;
    private int userId;
    private LocalDateTime loginTime;
    private String status;

    // --- Add Getters and Setters for all fields ---
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLoginTimeAsDate() {
        if (this.loginTime == null) {
            return null;
        }
        return java.sql.Timestamp.valueOf(this.loginTime);
    }
}