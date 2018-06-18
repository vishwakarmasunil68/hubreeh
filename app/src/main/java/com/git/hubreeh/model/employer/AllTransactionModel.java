package com.git.hubreeh.model.employer;

/**
 * Created by Hp on 1/30/2018.
 */

public class AllTransactionModel {

    String date,comments,currency,amount;


    public AllTransactionModel(String date, String comments, String currency, String amount) {
        this.date = date;
        this.comments = comments;
        this.currency = currency;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
}
