package com.git.hubreeh.model.employer;

/**
 * Created by Hp on 1/30/2018.
 */

public class WithdrawalPendingModel {

    String requestedDate,pendingAmount,details,status,processingDate;


    public WithdrawalPendingModel(String requestedDate, String pendingAmount, String details, String status, String processingDate) {
        this.requestedDate = requestedDate;
        this.pendingAmount = pendingAmount;
        this.details = details;
        this.status = status;
        this.processingDate = processingDate;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(String pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }
}
