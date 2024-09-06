package org.javacore.DAO.DAOImpl;

import org.javacore.DAO.CreditLimiService;
import org.javacore.Domain.CreditLimit;
import org.javacore.Helper.Ultils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class CreditLimitService implements CreditLimiService {
    StringBuilder sql ;
    @Override
    public Boolean insert(CreditLimit creditLimit) {
        return null;
    }

    @Override
    public Boolean update(CreditLimit creditLimit) {
        return null;
    }

    @Override
    public Boolean deleteById(Integer id) {
        return null;
    }

    @Override
    public List<CreditLimit> read() {
        return null;
    }

    @Override
    public Boolean capHanMucTinDung(int accountId, double CreditLimitAmount) {
        try{
            Connection conn = Ultils.getConnection();
            sql = new StringBuilder("insert into CreditLimit(AccountId,CreditLimitAmount) values (?,?);");
            try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
                pstmt.setInt(1,accountId);
                pstmt.setDouble(2,CreditLimitAmount);
                pstmt.executeUpdate();
                System.out.println("Han muc tin dung da duoc cap nhat .");
            }

        }catch(Exception e ){

            e.printStackTrace();
        }
        return  true;
    }
}
