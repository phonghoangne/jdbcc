package org.javacore.Domain.Exception;
public class BankingError extends RuntimeException {
    public BankingError(String msg,Throwable cause){
        super(msg,cause);
    }
    public BankingError(String msg){
        super(msg);
    }
}


