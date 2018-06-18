package com.git.hubreeh.model.jobseeker;

/**
 * Created by Hp on 2/17/2018.
 */

public class AddJobModel {
    String id;
    String name;
    String role;
    String start;
    String end;
    String dec;

    public AddJobModel(String id,String name, String role, String start, String end, String dec) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.start = start;
        this.end = end;
        this.dec = dec;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }
}
