package com.git.hubreeh.ApiServices;

import java.util.List;

/**
 * Created by Hp on 2/19/2018.
 */

public class ApiResponseFinal {


    /**
     * status : 1
     * message : final register successfully.
     * result : [{"user_id":"130","email":"hshhshsh@ndjdjjd.bdhsh","password":"94664464646","title":"hshjsjsj","firstName":"jshjsjsj","lastName":"jhshsjsj","dateOfBirth":"17/2/2018","MobileNumber":"hshushsns","otp":"123456","isUae":"1","category_id":"0","rate":"","isAgree":"0","isFull":"0","image":"","aboutMe":"0","hearUs":"","InsuranceNumber":"sjdbsabd8365278","dateSituation":"1","studentLoan":"2","status":"1","isActive":"1","device_id":"sakjbdcjksabcfj","osType":"Android","fcmToken":"scujgbsacfsdfcsdufv","isRegister":"1"}]
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
         * user_id : 130
         * email : hshhshsh@ndjdjjd.bdhsh
         * password : 94664464646
         * title : hshjsjsj
         * firstName : jshjsjsj
         * lastName : jhshsjsj
         * dateOfBirth : 17/2/2018
         * MobileNumber : hshushsns
         * otp : 123456
         * isUae : 1
         * category_id : 0
         * rate :
         * isAgree : 0
         * isFull : 0
         * image :
         * aboutMe : 0
         * hearUs :
         * InsuranceNumber : sjdbsabd8365278
         * dateSituation : 1
         * studentLoan : 2
         * status : 1
         * isActive : 1
         * device_id : sakjbdcjksabcfj
         * osType : Android
         * fcmToken : scujgbsacfsdfcsdufv
         * isRegister : 1
         */

        private String user_id;
        private String email;
        private String password;
        private String title;
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String MobileNumber;
        private String otp;
        private String isUae;
        private String category_id;
        private String rate;
        private String isAgree;
        private String isFull;
        private String image;
        private String aboutMe;
        private String hearUs;
        private String InsuranceNumber;
        private String dateSituation;
        private String studentLoan;
        private String status;
        private String isActive;
        private String device_id;
        private String osType;
        private String fcmToken;
        private String isRegister;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getMobileNumber() {
            return MobileNumber;
        }

        public void setMobileNumber(String MobileNumber) {
            this.MobileNumber = MobileNumber;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getIsUae() {
            return isUae;
        }

        public void setIsUae(String isUae) {
            this.isUae = isUae;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getIsAgree() {
            return isAgree;
        }

        public void setIsAgree(String isAgree) {
            this.isAgree = isAgree;
        }

        public String getIsFull() {
            return isFull;
        }

        public void setIsFull(String isFull) {
            this.isFull = isFull;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAboutMe() {
            return aboutMe;
        }

        public void setAboutMe(String aboutMe) {
            this.aboutMe = aboutMe;
        }

        public String getHearUs() {
            return hearUs;
        }

        public void setHearUs(String hearUs) {
            this.hearUs = hearUs;
        }

        public String getInsuranceNumber() {
            return InsuranceNumber;
        }

        public void setInsuranceNumber(String InsuranceNumber) {
            this.InsuranceNumber = InsuranceNumber;
        }

        public String getDateSituation() {
            return dateSituation;
        }

        public void setDateSituation(String dateSituation) {
            this.dateSituation = dateSituation;
        }

        public String getStudentLoan() {
            return studentLoan;
        }

        public void setStudentLoan(String studentLoan) {
            this.studentLoan = studentLoan;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getOsType() {
            return osType;
        }

        public void setOsType(String osType) {
            this.osType = osType;
        }

        public String getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
        }

        public String getIsRegister() {
            return isRegister;
        }

        public void setIsRegister(String isRegister) {
            this.isRegister = isRegister;
        }
    }
}
