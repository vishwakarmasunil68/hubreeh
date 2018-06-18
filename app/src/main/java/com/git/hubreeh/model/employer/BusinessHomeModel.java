package com.git.hubreeh.model.employer;

import java.util.List;

/**
 * Created by Hp on 1/24/2018.
 */

public class BusinessHomeModel {

    /**
     * status : 1
     * message : get data.
     * result : [{"job_id":"1","business_id":"1","category_id":"3","jobName":"Food type","jobPrice":"","aboutJob":"test","image":"cc36f7f49b17d3ce9b61a5c65c0fe143.png","isUrgent":"1","urgentDate":"","isJobShared":"1","jobAddress":"","paymentMethod":"cash","category_name":"Food"},{"job_id":"2","business_id":"1","category_id":"3","jobName":"Food type3","jobPrice":"","aboutJob":"test","image":"9ce1c90b288b4327f73601bf7f1864c2.png","isUrgent":"1","urgentDate":"","isJobShared":"1","jobAddress":"","paymentMethod":"cash","category_name":"Food"}]
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
         * job_id : 1
         * business_id : 1
         * category_id : 3
         * jobName : Food type
         * jobPrice :
         * aboutJob : test
         * image : cc36f7f49b17d3ce9b61a5c65c0fe143.png
         * isUrgent : 1
         * urgentDate :
         * isJobShared : 1
         * jobAddress :
         * paymentMethod : cash
         * category_name : Food
         */

        private String job_id;
        private String business_id;
        private String category_id;
        private String jobName;
        private String jobPrice;
        private String aboutJob;
        private String image;
        private String isUrgent;
        private String urgentDate;
        private String isJobShared;
        private String jobAddress;
        private String paymentMethod;
        private String category_name;

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getJobPrice() {
            return jobPrice;
        }

        public void setJobPrice(String jobPrice) {
            this.jobPrice = jobPrice;
        }

        public String getAboutJob() {
            return aboutJob;
        }

        public void setAboutJob(String aboutJob) {
            this.aboutJob = aboutJob;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIsUrgent() {
            return isUrgent;
        }

        public void setIsUrgent(String isUrgent) {
            this.isUrgent = isUrgent;
        }

        public String getUrgentDate() {
            return urgentDate;
        }

        public void setUrgentDate(String urgentDate) {
            this.urgentDate = urgentDate;
        }

        public String getIsJobShared() {
            return isJobShared;
        }

        public void setIsJobShared(String isJobShared) {
            this.isJobShared = isJobShared;
        }

        public String getJobAddress() {
            return jobAddress;
        }

        public void setJobAddress(String jobAddress) {
            this.jobAddress = jobAddress;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }
    }
}
