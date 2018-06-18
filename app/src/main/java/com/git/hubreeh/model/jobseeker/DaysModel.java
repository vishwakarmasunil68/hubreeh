package com.git.hubreeh.model.jobseeker;

/**
 * Created by Hp on 2/17/2018.
 */

public class DaysModel {

    String days;
    boolean amChecked,pmChecked;

    public DaysModel(String days, boolean amChecked, boolean pmChecked) {
        this.days = days;
        this.amChecked = amChecked;
        this.pmChecked = pmChecked;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public boolean isAmChecked() {
        return amChecked;
    }

    public void setAmChecked(boolean amChecked) {
        this.amChecked = amChecked;
    }

    public boolean isPmChecked() {
        return pmChecked;
    }

    public void setPmChecked(boolean pmChecked) {
        this.pmChecked = pmChecked;
    }
}
