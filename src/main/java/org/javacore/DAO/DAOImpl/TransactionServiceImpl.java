package org.javacore.DAO.DAOImpl;

import org.javacore.DAO.TranferService;
import org.javacore.DAO.TransactionService;
import org.javacore.Domain.Exception.BankingError;
import org.javacore.Domain.Transaction;
import org.javacore.Domain.Transfer;
import org.javacore.Helper.Ultils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    StringBuilder sql;

    @Override
    public Boolean insert(Transaction transaction) {
        return null;
    }

    @Override
    public Boolean update(Transaction transaction) {
        return null;
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public List<Transaction> read() {
        return null;
    }

    @Override
    public List<Transaction> findByAccountId(Integer accountId) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            sql = new StringBuilder("select * from Transaction where AccountId = ?;");
            Connection conn = Ultils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            //
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("TransactionID"));
                transaction.setTransactionType(rs.getString("TransactionType"));
                transaction.setAmount(rs.getInt("Amount"));
                transaction.setTransactionDate( rs.getTimestamp("TransactionDate"));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BankingError("Loi khong the xem lich su giao dich");
        }
        return transactions;
    }
}
