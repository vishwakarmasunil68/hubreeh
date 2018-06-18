package com.git.hubreeh.model.employer;

/**
 * Created by Hp on 1/26/2018.
 */

public class PaymentMethodModel {

    String status;

    public PaymentMethodModel(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
