package org.javacore.DAO;

import org.javacore.Domain.CreditLimit;

public interface CreditLimiService extends CRUD<CreditLimit>{
    Boolean capHanMucTinDung(int accountId,   double CreditLimitAmount);
}
