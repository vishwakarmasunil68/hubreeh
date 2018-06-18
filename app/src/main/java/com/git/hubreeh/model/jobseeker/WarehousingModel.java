package com.git.hubreeh.model.jobseeker;

import java.io.Serializable;

/**
 * Created by Hp on 2/12/2018.
 */

public class WarehousingModel implements Serializable{

    String jobROleWarehousingId;
    String jobRoleWarehousing;
    String jobIconsWarehousing;
    boolean isIconSelectedWarehousing;

    public WarehousingModel(String jobROleWarehousingId, String jobRoleWarehousing, String jobIconsWarehousing, boolean isIconSelectedWarehousing) {
        this.jobROleWarehousingId = jobROleWarehousingId;
        this.jobRoleWarehousing = jobRoleWarehousing;
        this.jobIconsWarehousing = jobIconsWarehousing;
        this.isIconSelectedWarehousing = isIconSelectedWarehousing;
    }

    public String getJobROleWarehousingId() {
        return jobROleWarehousingId;
    }

    public void setJobROleWarehousingId(String jobROleWarehousingId) {
        this.jobROleWarehousingId = jobROleWarehousingId;
    }

    public String getJobRoleWarehousing() {
        return jobRoleWarehousing;
    }

    public void setJobRoleWarehousing(String jobRoleWarehousing) {
        this.jobRoleWarehousing = jobRoleWarehousing;
    }

    public String getJobIconsWarehousing() {
        return jobIconsWarehousing;
    }

    public void setJobIconsWarehousing(String jobIconsWarehousing) {
        this.jobIconsWarehousing = jobIconsWarehousing;
    }

    public boolean isIconSelectedWarehousing() {
        return isIconSelectedWarehousing;
    }

    public void setIconSelectedWarehousing(boolean iconSelectedWarehousing) {
        isIconSelectedWarehousing = iconSelectedWarehousing;
    }
}
