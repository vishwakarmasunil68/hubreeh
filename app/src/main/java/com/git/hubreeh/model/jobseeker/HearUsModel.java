package com.git.hubreeh.model.jobseeker;

/**
 * Created by Hp on 2/17/2018.
 */

public class HearUsModel {

    String reasonHearUs;
    boolean hearIsChecked;

    public HearUsModel(String reasonHearUs, boolean hearIsChecked) {
        this.reasonHearUs = reasonHearUs;
        this.hearIsChecked = hearIsChecked;
    }


    public String getReasonHearUs() {
        return reasonHearUs;
    }

    public void setReasonHearUs(String reasonHearUs) {
        this.reasonHearUs = reasonHearUs;
    }

    public boolean isHearIsChecked() {
        return hearIsChecked;
    }

    public void setHearIsChecked(boolean hearIsChecked) {
        this.hearIsChecked = hearIsChecked;
    }
}
