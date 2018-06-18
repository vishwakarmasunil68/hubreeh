package com.git.hubreeh.model.employer;

/**
 * Created by Hp on 1/30/2018.
 */

public class ViewMilestoneModel {

    String createdDate,employerName,projectName,amount,description,status;


    public ViewMilestoneModel(String createdDate, String employerName, String projectName, String amount, String description, String status) {
        this.createdDate = createdDate;
        this.employerName = employerName;
        this.projectName = projectName;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}



