package org.javacore.DAO.DAOImpl;

import org.javacore.DAO.AccountService;
import org.javacore.Domain.Account;
import org.javacore.Domain.Exception.BankingError;
import org.javacore.Domain.Exception.ObjectNotFoundException;
import org.javacore.Helper.Ultils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService {
    StringBuilder sql;

    @Override
    public Boolean insert(Account account) {
        return true;
    }

    @Override
    public Boolean update(Account account) {
        return null;
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public List<Account> read() {
        return null;
    }


    @Override
    public Boolean chuyenKhoan(Integer fromAccount, Integer toAccount, Double money) throws SQLException {

        Connection conn = null;
        try {
             conn = Ultils.getConnection();
            conn.setAutoCommit(false);

            Account fromAccounts = findByAccountId(fromAccount);
            double balance = fromAccounts.getBalance();
            if (balance < money) {
                throw new BankingError("So du khong du de thuc hien giao dich");
            }

            // tru tien tai khoan gui tien .
            sql = new StringBuilder("update Account set Balance = Balance - ? where AccountId = ? ;");
            try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
                pstmt.setDouble(1, money);
                pstmt.setInt(2, fromAccount);
                pstmt.executeUpdate();
            }
            // cong tien cho tai khoan nhan tien .
            sql = new StringBuilder("update Account set Balance = Balance + ? where AccountId = ? ;");
            try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
                pstmt.setDouble(1, money);
                pstmt.setInt(2, toAccount);
                pstmt.executeUpdate();
            }
            // ghi lai thong tin giao dich
            sql = new StringBuilder("insert into Transfer(FromAccountId,ToAccountId,Amount) values (?,?,?);");
            try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
                pstmt.setInt(1, fromAccount);
                pstmt.setInt(2, toAccount);
                pstmt.setDouble(3, money);
                pstmt.executeUpdate();
            }
            conn.commit();
            System.out.println("Chuyển khoản thành công .");
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
            throw new BankingError("gap loi khi thuc hien chuyen khoan .");
        }
        return true;
    }

    @Override
    public Account findByAccountId(Integer accountId) {
        sql = new StringBuilder("select * from Account where AccountId = ?;");

        try {
            Connection conn = Ultils.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();
            Account account = new Account(rs.getInt("AccountId"), rs.getInt("CustomerId"), rs.getString("AccountType"), rs.getDouble("Balance"), rs.getString("status"), rs.getTimestamp("CreateDate"));
            return account;
        } catch (Exception e) {
            throw new ObjectNotFoundException("Khong tim thay thuc the");
        }
    }

    @Override
    public boolean closeAccount(Integer accountId) {
        try{
            Connection conn = Ultils.getConnection();
            sql = new StringBuilder("update Account set Status = 'Closed' where AccountId = ?;" );
            try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                pstmt.setInt(1,accountId);
                int checkSoDong = pstmt.executeUpdate();
                if(checkSoDong > 0){
                    System.out.println("Dong tai khoan thanh cong.");
                }else{
                    System.out.println("Gap loi khi dong tai khoan!!!");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
