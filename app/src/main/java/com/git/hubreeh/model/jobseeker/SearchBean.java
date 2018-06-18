package com.git.hubreeh.model.jobseeker;

import java.util.List;

/**
 * Created by Warlock on 3/13/2018.
 */

public class SearchBean {

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
         * business_id : 33
         * user_id : 0
         * category_id : 1
         * jobName : job 1
         * jobPrice : 150
         * aboutJob : Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
         * image :
         * isUrgent : 1
         * urgentDate : 13-03-2018 15:15 pm
         * isJobShared : 0
         * jobAddress :
         * paymentMethod : cash
         * post_status : pending
         * latitude : 26.85043
         * longitude : 75.812932
         * radius : 10
         * bid_count : 1
         */

        private String job_id;
        private String business_id;
        private String user_id;
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
        private String post_status;
        private String latitude;
        private String longitude;
        private String radius;
        private String bid_count;

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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getPost_status() {
            return post_status;
        }

        public void setPost_status(String post_status) {
            this.post_status = post_status;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public String getBid_count() {
            return bid_count;
        }

        public void setBid_count(String bid_count) {
            this.bid_count = bid_count;
        }
    }
}
