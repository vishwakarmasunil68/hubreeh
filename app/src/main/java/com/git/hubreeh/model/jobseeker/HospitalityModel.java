package com.git.hubreeh.model.jobseeker;

import java.io.Serializable;

/**
 * Created by Hp on 12/16/2017.
 */

public class HospitalityModel implements Serializable {


    String jobRoleId;
    String jobRole;
    String jobIcons;
    boolean isIconSelected;

    public HospitalityModel(String jobRoleId, String jobRole, String jobIcons, boolean isIconSelected) {
        this.jobRoleId = jobRoleId;
        this.jobRole = jobRole;
        this.jobIcons = jobIcons;
        this.isIconSelected = isIconSelected;
    }


    public String getJobRoleId() {
        return jobRoleId;
    }

    public void setJobRoleId(String jobRoleId) {
        this.jobRoleId = jobRoleId;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobIcons() {
        return jobIcons;
    }

    public void setJobIcons(String jobIcons) {
        this.jobIcons = jobIcons;
    }

    public boolean isIconSelected() {
        return isIconSelected;
    }

    public void setIconSelected(boolean iconSelected) {
        isIconSelected = iconSelected;
    }

}
