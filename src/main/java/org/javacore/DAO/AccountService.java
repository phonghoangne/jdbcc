package org.javacore.DAO;

import org.javacore.Domain.Account;

import java.sql.SQLException;

public interface AccountService extends CRUD<Account> {

    Boolean chuyenKhoan(Integer fromAccount, Integer toAccount, Double money) throws SQLException;

    Account findByAccountId(Integer accountId);

    boolean closeAccount(Integer accountId);

}
