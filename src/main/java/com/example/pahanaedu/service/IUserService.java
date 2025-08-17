package com.example.pahanaedu.service;

import com.example.pahanaedu.model.LoginHistory;
import com.example.pahanaedu.model.User;

import java.util.List;

public interface IUserService {
    User loginUser(String username, String password);

    User addUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);

    boolean updateUser(User user);

    boolean deleteUser(int id);

    boolean changePassword(int userId, String newPassword);

    List<LoginHistory> getLoginHistory();
}