package org.javacore;
import org.javacore.DAO.AccountService;
import org.javacore.DAO.CreditLimiService;
import org.javacore.DAO.CustomerService;
import org.javacore.DAO.DAOImpl.AccountServiceImpl;
import org.javacore.DAO.DAOImpl.CreditLimitService;
import org.javacore.DAO.DAOImpl.CustomerServiceImpl;
import org.javacore.DAO.DAOImpl.TransactionServiceImpl;
import org.javacore.DAO.TransactionService;
import org.javacore.Domain.Account;
import org.javacore.Domain.Customer;
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
        CustomerService customerService = new CustomerServiceImpl();
        AccountService accountService = new AccountServiceImpl();
        CreditLimiService creditLimiService = new CreditLimitService();
        TransactionService transactionService = new TransactionServiceImpl();


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
                    Customer customer =  new Customer();
                    System.out.println("Nhap ten khach hang : ");
                    customer.setName(scanner.nextLine());
                    System.out.println("Nhap dia chi khach hang :");
                    customer.setAddress(scanner.nextLine());
                    System.out.println("Nhap so dien thoai khach hang :");
                    customer.setPhoneNumber(scanner.nextLine());
                    System.out.println("Nhap email khach hang :");
                    customer.setEmail( scanner.nextLine());
                    customerService.insert(customer);
                    break;
                case 2:
                    System.out.println("Nhap ID tai khoan chuyen tien : ");
                    int fromAccountId = scanner.nextInt();
                    System.out.println("Nhap ID tai khoan nhan tien : ");
                    int toAccountId = scanner.nextInt();
                    System.out.println("Nhap so tien can chuyen : ");
                    double amount = scanner.nextDouble();
                    try {
                        accountService.chuyenKhoan(fromAccountId,toAccountId,amount);
                    }catch (Exception e){
                        e.getMessage();
                    }
                    break;
                case 3:
                    System.out.println("Hãy nhập id tài khoản cần kiểm tra : ");
                    int idAccount = scanner.nextInt();
                    Account acc =  accountService.findByAccountId(idAccount);
                    System.out.println("So du la : "+acc.getBalance());
                    break;
                case 4:
                    System.out.println("Nhập id để xem lịch sử giao dịch");
                    int accountId1 = scanner.nextInt();
                    transactionService.findByAccountId(accountId1);
                    break;
                case 5:
                    System.out.println("Nhap id tai khoan can dong : ");
                    int accountId = scanner.nextInt();
                    accountService.closeAccount(accountId);
                    break;
                case 6:
                    System.out.println("Nhap id tai khoan de cap han muc tin dung  :");
                    int accountIds = scanner.nextInt();
                    System.out.println("Nhap han muc tin dung : ");
                    double getCreditLimitAmount =  scanner.nextDouble();
                    creditLimiService.capHanMucTinDung(accountIds,getCreditLimitAmount);
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
}

