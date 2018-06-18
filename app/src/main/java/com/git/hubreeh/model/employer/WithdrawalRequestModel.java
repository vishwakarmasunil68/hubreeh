package com.git.hubreeh.model.employer;

/**
 * Created by Hp on 1/30/2018.
 */

public class WithdrawalRequestModel {
    String method,descreption,fees,currency,total;

    public WithdrawalRequestModel(String method, String descreption, String fees, String currency, String total) {
        this.method = method;
        this.descreption = descreption;
        this.fees = fees;
        this.currency = currency;
        this.total = total;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
