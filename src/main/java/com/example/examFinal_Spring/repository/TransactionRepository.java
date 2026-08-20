package com.example.examFinal_Spring.repository;

import com.example.examFinal_Spring.model.Transaction;
import com.example.examFinal_Spring.model.TransactionType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    public List<Transaction> findAll(TransactionType type) {
        String sql = "SELECT id, created_at, transaction_type, amount, reason, account_id FROM transaction";
        if (type != null) {
            sql += " WHERE transaction_type = ?";
        }
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (type != null) {
                ps.setString(1, type.name());
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }

    public List<Transaction> findByAccountId(String accountId) {
        String sql = "SELECT id, created_at, transaction_type, amount, reason, account_id " +
                     "FROM transaction WHERE account_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions for account: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }

    public void save(Transaction transaction, String accountId) {
        String sql = "INSERT INTO transaction (id, created_at, transaction_type, amount, reason, account_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, transaction.getId());
            ps.setTimestamp(2, Timestamp.from(transaction.getCreatedAt()));
            ps.setString(3, transaction.getTransactionType().name());
            ps.setBigDecimal(4, transaction.getAmount());
            ps.setString(5, transaction.getReason());
            ps.setString(6, accountId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public BigDecimal getBalanceByAccountId(String accountId) {
        String sql = "SELECT COALESCE(SUM(CASE WHEN transaction_type = 'IN' THEN amount ELSE -amount END), 0) " +
                     "AS balance FROM transaction WHERE account_id = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error computing balance: " + e.getMessage());
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    private Transaction mapRow(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        Instant createdAt = rs.getTimestamp("created_at").toInstant();
        TransactionType type = TransactionType.valueOf(rs.getString("transaction_type"));
        BigDecimal amount = rs.getBigDecimal("amount");
        String reason = rs.getString("reason");
        return new Transaction(id, createdAt, type, amount, reason);
    }
}