package org.javacore;
import org.javacore.Domain.Exception.BankingError;
import org.javacore.Domain.Exception.ObjectNotFoundException;
import org.javacore.Helper.Ultils;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println("\n--- Ngân hàng Menu ---");
                System.out.println("1. Mở tài khoản ngân hàng");
                System.out.println("2. Giao dịch chuyển khoản");
                System.out.println("3. Kiểm tra số dư");
                System.out.println("4. Lịch sử giao dịch");
                System.out.println("5. Đóng tài khoản");
                System.out.println("6. Cấp hạn mức tín dụng");
                System.out.println("7. Thoát");
                System.out.print("Vui lòng chọn (1-7): ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        taoTaiKhoan(scanner);
                        break;
                    case 2:
                        chuyenKhoan(scanner);
                        break;
                    case 3:
                        kiemTraSoDu(scanner);
                        break;
                    case 4:
                        xemLichSuGiaoDich(scanner);
                        break;
                    case 5:
                        dongTaiKhoan(scanner);
                        break;
                    case 6:
                        capHanMucTinDung(scanner);
                        break;
                    case 7:
                        System.out.println("Cảm ơn bạn đã sử dụng dịch vụ ngân hàng!");
                        exit = true;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại.");
                }
            }
        scanner.close();
        }
        public static void taoTaiKhoan(Scanner scanner){
        StringBuilder sql = new StringBuilder("insert into Customer(Name,Address,phoneNumber,Email) values(?,?,?,?);");
            System.out.println("Nhap ten khach hang : ");
            String name = scanner.nextLine();
            System.out.println("Nhap dia chi khach hang :");
            String address = scanner.nextLine();
            System.out.println("Nhap so dien thoai khach hang :");
            String phoneNumber = scanner.nextLine();
            System.out.println("Nhap email khach hang :");
            String email = scanner.nextLine();
            try{
                Connection conn = Ultils.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1,name);
                pstmt.setString(2,address);
                pstmt.setString(3,phoneNumber);
                pstmt.setString(4,email);
                pstmt.executeUpdate();
                System.out.println("Tai khoan da duoc tao thanh cong .");
            }catch(Exception e){
                e.printStackTrace();
                throw  new BankingError("Loi khi mo tai khoan ngan hang");
            }
        }
        public static void chuyenKhoan(Scanner scanner){
        StringBuilder sql = new StringBuilder();
            System.out.println("Nhap ID tai khoan chuyen tien : ");
            int fromAccountId = scanner.nextInt();
            System.out.println("Nhap ID tai khoan nhan tien : ");
            int toAccountId = scanner.nextInt();
            System.out.println("Nhap so tien can chuyen : ");
            double amount = scanner.nextDouble();
        try{
            Connection conn = Ultils.getConnection();
            conn.setAutoCommit(false);
            sql = new StringBuilder("select Balance from Account where AccountId = ?;");
            try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                pstmt.setInt(1,fromAccountId);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    double balance = rs.getDouble("Balance");
                    if(balance < amount){
                        throw new BankingError("So du khong du de thuc hien giao dich");

                    }
                }else {
                    throw new BankingError("Tai khoan khong ton tai.");
                }
            }
          // tru tien tai khoan gui tien .
            sql = new StringBuilder("update Account set Balance = Balance - ? where AccountId = ? ;");
            try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                pstmt.setDouble(1,amount);
                pstmt.setInt(2,fromAccountId);
                pstmt.executeUpdate();
            }
            // cong tien cho tai khoan nhan tien .
            sql = new StringBuilder("update Account set Balance = Balance + ? where AccountId = ? ;");
            try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                pstmt.setDouble(1,amount);
                pstmt.setInt(2,toAccountId);
                pstmt.executeUpdate();
            }
            // ghi lai thong tin giao dich
            sql =  new StringBuilder("insert into Transfer(FromAccountId,ToAccountId,Amount) values (?,?,?);");
            try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                pstmt.setInt(1,fromAccountId);
                pstmt.setInt(2,toAccountId);
                pstmt.setDouble(3,amount);
                pstmt.executeUpdate();
            }
            conn.commit();
            System.out.println("Chuyển khoản thành công .");
        }catch(Exception e){e.printStackTrace();
            throw new BankingError("gap loi khi thuc hien chuyen khoan .");
        }
        }

        public static void kiemTraSoDu(Scanner scanner){
        StringBuilder sql;
            System.out.println("Hãy nhập id tài khoản cần kiểm tra : ");
            int accountId = scanner.nextInt();
            try{
                Connection conn = Ultils.getConnection();
                sql = new StringBuilder("select Balance from Account where AccountId = ?;");
                try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                    pstmt.setInt(1,accountId);
                    ResultSet rs = pstmt.executeQuery();
                    if(rs.next()){
                        double balance = rs.getDouble("Balance");
                        System.out.println("Số dư tài khoản là : " + balance);
                    }else {
                        System.out.println("Tài khoản không tồn tại .");
                    }
                }
            }catch(Exception e ){
                e.printStackTrace();
                throw new BankingError("Lỗi khi kiểm tra số dư .");
            }
        }
        private static void xemLichSuGiaoDich(Scanner scanner){
        StringBuilder  sql;
            System.out.println("Nhập id để xem lịch sử giao dịch");
            int accountId = scanner.nextInt();
            try{
                Connection conn = Ultils.getConnection();
                sql = new StringBuilder("select * from Transaction where AccountId = ?;");
                try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                    pstmt.setInt(1,accountId);
                    ResultSet rs = pstmt.executeQuery();
                    //
                    while(rs.next()){
                        System.out.println("Id : " + rs.getInt("TransactionID"));
                        System.out.println("Loai giao dich :" + rs.getString("TransactionType"));
                        System.out.println("So tien :"+rs.getDouble("Amount"));
                        System.out.println("Ngay giao dich :" + rs.getTimestamp("TransactionDate"));
                        System.out.println(rs.getString("Description"));


                    }
                }
            }catch(Exception e ){
                e.printStackTrace();
                throw new BankingError("Loi khong the xem lich su giao dich");
            }
        }
        private static void dongTaiKhoan(Scanner scanner){
        StringBuilder sql;
            System.out.println("Nhap id tai khoan can dong : ");
            int accountId = scanner.nextInt();
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
        }
        private static void capHanMucTinDung(Scanner scanner){
        StringBuilder sql;
            System.out.println("Nhap id tai khoan de cap han muc tin dung  :");
            int accountId = scanner.nextInt();
            System.out.println("Nhap han muc tin dung : ");
            double getCreditLimitAmount =  scanner.nextDouble();
            try{
                Connection conn = Ultils.getConnection();
                sql = new StringBuilder("insert into CreditLimit(AccountId,CreditLimitAmount) values (?,?);");
                try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                    pstmt.setInt(1,accountId);
                    pstmt.setDouble(2,getCreditLimitAmount);
                    pstmt.executeUpdate();
                    System.out.println("Han muc tin dung da duoc cap nhat .");
                }

            }catch(Exception e ){

                e.printStackTrace();
            }
        }


}

