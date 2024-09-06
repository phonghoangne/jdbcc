package org.javacore.Domain;

import java.sql.Time;
import java.sql.Timestamp;

public class CreditLimit {
    private int id;
    private int accountId;
    private double creditLimitAmount;
    private Timestamp assignedDate;

    public CreditLimit(int id, int accountId, double creditLimitAmount, Timestamp assignedDate) {
        this.id = id;
        this.accountId = accountId;
        this.creditLimitAmount = creditLimitAmount;
        this.assignedDate = assignedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getCreditLimitAmount() {
        return creditLimitAmount;
    }

    public void setCreditLimitAmount(double creditLimitAmount) {
        this.creditLimitAmount = creditLimitAmount;
    }

    public Timestamp getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Timestamp assignedDate) {
        this.assignedDate = assignedDate;
    }

    @Override
    public String toString() {
        return "CreditLimit{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", creditLimitAmount=" + creditLimitAmount +
                ", assignedDate=" + assignedDate +
                '}';
    }
}
