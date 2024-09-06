package org.javacore.Domain;

import java.sql.Timestamp;

public class CustomerCreditScore {
    private int id;
    private int creditScore;
    private Timestamp lastUpdate;

    public CustomerCreditScore(int id, int creditScore, Timestamp lastUpdate) {
        this.id = id;
        this.creditScore = creditScore;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "CustomerCreditScore{" +
                "id=" + id +
                ", creditScore=" + creditScore +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
