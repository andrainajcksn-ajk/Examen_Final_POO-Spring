package com.example.examFinal_Spring.repository;

import com.example.examFinal_Spring.model.Account;
import com.example.examFinal_Spring.model.AccountType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AccountRepository {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    
    public Account findById(String id) {
        String sql = "SELECT id, account_type FROM account WHERE id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching account: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> findAll() {
        String sql = "SELECT id, account_type FROM account";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                accounts.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
            e.printStackTrace();
        }
        return accounts;
    }

    public void save(Account account) {
        String sql = "INSERT INTO account (id, account_type) VALUES (?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getId());
            ps.setString(2, account.getAccountType().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving account: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Account mapRow(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        AccountType type = AccountType.valueOf(rs.getString("account_type"));
        return new Account(id, type, Collections.emptyList());
    }
}