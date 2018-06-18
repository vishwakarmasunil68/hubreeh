package com.git.hubreeh.model.jobseeker;

/**
 * Created by Hp on 1/10/2018.
 */

public class ContactsModel {

    String name,msg;
    boolean isHeader;

    public ContactsModel(String name, String msg, boolean isHeader) {
        this.name = name;
        this.msg = msg;
        this.isHeader = isHeader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

}
