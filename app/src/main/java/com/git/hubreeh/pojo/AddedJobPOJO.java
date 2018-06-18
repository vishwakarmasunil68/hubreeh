package com.git.hubreeh.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddedJobPOJO {
    @SerializedName("PrevJobId")
    @Expose
    private String prevJobId;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("JobRole")
    @Expose
    private String jobRole;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("JobDescription")
    @Expose
    private String jobDescription;
    @SerializedName("AddedOn")
    @Expose
    private String addedOn;

    public String getPrevJobId() {
        return prevJobId;
    }

    public void setPrevJobId(String prevJobId) {
        this.prevJobId = prevJobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
