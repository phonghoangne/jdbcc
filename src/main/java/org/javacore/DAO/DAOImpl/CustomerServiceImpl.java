package org.javacore.DAO.DAOImpl;

import org.javacore.DAO.CustomerService;
import org.javacore.Domain.Customer;
import org.javacore.Domain.Exception.BankingError;
import org.javacore.Helper.Ultils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Scanner;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public Boolean insert(Customer customer) {
            Scanner scanner= new Scanner(System.in);
            StringBuilder sql = new StringBuilder("insert into Customer(Name,Address,phoneNumber,Email) values(?,?,?,?);");
            try{
                Connection conn = Ultils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1,customer.getName());
                pstmt.setString(2,customer.getAddress());
                pstmt.setString(3,customer.getPhoneNumber());
                pstmt.setString(4,customer.getEmail());
                pstmt.executeUpdate();
                System.out.println("Tai khoan da duoc tao thanh cong .");
            }catch(Exception e){
                throw  new BankingError("Loi khi mo tai khoan ngan hang");
            }
        return true;
    }

    @Override
    public Boolean update(Customer customer) {
        return null;
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public List<Customer> read() {
        return null;
    }
}
