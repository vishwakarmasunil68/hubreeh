package com.git.hubreeh.ApiServices;

import java.util.List;

/**
 * Created by Hp on 2/17/2018.
 */

public class ApiResponseGetJob {


    /**
     * status : 1
     * message : successfully get.
     * result : [{"company_id":"18","user_id":"98","name":"hh","role":"hh","start":"03/2018","end":"03/2018","description":"bbb"},{"company_id":"19","user_id":"98","name":"ggg","role":"hhh","start":"03/2018","end":"03/2018","description":"jvj"}]
     */

    private int status;
    private String message;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * company_id : 18
         * user_id : 98
         * name : hh
         * role : hh
         * start : 03/2018
         * end : 03/2018
         * description : bbb
         */

        private String company_id;
        private String user_id;
        private String name;
        private String role;
        private String start;
        private String end;
        private String description;

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
