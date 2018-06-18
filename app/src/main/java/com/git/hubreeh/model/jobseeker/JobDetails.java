package com.git.hubreeh.model.jobseeker;

import java.util.List;

/**
 * Created by Warlock on 3/12/2018.
 */

public class JobDetails {

    /**
     * status : 1
     * message : get
     * result : {"jobName":"Food type","aboutJob":"test","jobPrice":"","bid_count":"1","job_id":"1","business_name":"xhdjjd","business_id":"1","bidings":[{"user_name":"subhqm lbjkk","review_count":"2","review_count2":"3.5000"}]}
     */

    private int status;
    private String message;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * jobName : Food type
         * aboutJob : test
         * jobPrice :
         * bid_count : 1
         * job_id : 1
         * business_name : xhdjjd
         * business_id : 1
         * bidings : [{"user_name":"subhqm lbjkk","review_count":"2","review_count2":"3.5000"}]
         */

        private String jobName;
        private String aboutJob;
        private String jobPrice;
        private String bid_count;
        private String job_id;
        private String business_name;
        private String business_id;
        private String urgentDate;
        private List<BidingsBean> bidings;

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getAboutJob() {
            return aboutJob;
        }

        public void setAboutJob(String aboutJob) {
            this.aboutJob = aboutJob;
        }

        public String getJobPrice() {
            return jobPrice;
        }

        public void setJobPrice(String jobPrice) {
            this.jobPrice = jobPrice;
        }

        public String getUrgentDate() {
            return urgentDate;
        }

        public void setUrgentDate(String urgentDate) {
            this.urgentDate = urgentDate;
        }

        public String getBid_count() {
            return bid_count;
        }

        public void setBid_count(String bid_count) {
            this.bid_count = bid_count;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public List<BidingsBean> getBidings() {
            return bidings;
        }

        public void setBidings(List<BidingsBean> bidings) {
            this.bidings = bidings;
        }

        public static class BidingsBean {
            /**
             * user_name : subhqm lbjkk
             * review_count : 2
             * review_count2 : 3.5000
             */

            private String user_name;
            private String review_count;
            private String review_count2;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getReview_count() {
                return review_count;
            }

            public void setReview_count(String review_count) {
                this.review_count = review_count;
            }

            public String getReview_count2() {
                return review_count2;
            }

            public void setReview_count2(String review_count2) {
                this.review_count2 = review_count2;
            }
        }
    }
}
