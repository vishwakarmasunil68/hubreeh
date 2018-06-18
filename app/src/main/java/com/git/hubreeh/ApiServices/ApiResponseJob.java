package com.git.hubreeh.ApiServices;

import java.util.List;

/**
 * Created by Hp on 2/16/2018.
 */

public class ApiResponseJob {

    /**
     * status : 1
     * message : user created successfully.
     * user_id : 3
     */

    private int status;
    private String message;
    private String  user_id;
    private String  amount;
    private List<ResultBean> resultBean;


    public List<ResultBean> getResultBean() {
        return resultBean;
    }

    public void setResultBean(List<ResultBean> resultBean) {
        this.resultBean = resultBean;
    }

    /**
     * result : {"company_id":"4","name":"cbsmdc f"}
     */

    private ResultBean result;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * company_id : 4
         * name : cbsmdc f
         */

        private String company_id;
        private String name;

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }






}
