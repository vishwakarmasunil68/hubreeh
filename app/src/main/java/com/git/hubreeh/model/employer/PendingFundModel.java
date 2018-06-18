package com.git.hubreeh.model.employer;

/**
 * Created by Hp on 1/30/2018.
 */

public class PendingFundModel {
    String date,transaction,reason,currency,amount,expectedDate;


    public PendingFundModel(String date, String transaction, String reason, String currency, String amount, String expectedDate) {
        this.date = date;
        this.transaction = transaction;
        this.reason = reason;
        this.currency = currency;
        this.amount = amount;
        this.expectedDate = expectedDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }
}
