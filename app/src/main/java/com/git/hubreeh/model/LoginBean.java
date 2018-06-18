package com.git.hubreeh.model;

import java.util.List;

/**
 * Created by Warlock on 2/24/2018.
 */

public class LoginBean {


    /**
     * status : 1
     * message : User LoggedIn.
     * result : {"user_id":"15","business_id":"15","email":"nnn@nnn.nnn","password":"123456789","title":"Mr.","firstName":"ganesh","lastName":"suthar","dateOfBirth":"24/2/2000","MobileNumber":"123456","otp":"123456","isUae":"0","category_id":"1","rate":"10","isAgree":"0","isFull":"0","image":"","aboutMe":"ehh","hearUs":"Internet Search(Google or Bing)","InsuranceNumber":"0","dateSituation":"3","studentLoan":"3","status":"1","isActive":"1","device_id":"9d1cc3933b9c8b7c","osType":"Android","fcmToken":"cuGuItFzbtw:APA91bEjsYnH3-XicZWS1hQ076ypwdjmmkVsbQH0ZK3hqD7gDOsP-kAicUQ3h0fq3mI1E141P7SyPnwEWr-DdZPkXdfs0dzgaUIEHk_KYwKyXCKa4TocH3l40cy4aGpFDkzqCRogUOsn","isRegister":"1"}
     */

    private int status;
    private String message;
    private String user_id;
    private String business_id;
    private String mobile;
    private ResultBean result;

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }


    public static class ResultBean {

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
        private String business_id;
        private List<ResultBean.AvailabilityBean> availability;
        private List<ResultBean.PreJobsBean> pre_jobs;


        private String companyName;
        private String brandName;
        private String contactName;
        private String mobileNumber;
        private String address1;
        private String address2;
        private String landmark;
        private String city;
        private String pincode;
        private String isHospitality;
        private String capicity;


        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getIsHospitality() {
            return isHospitality;
        }

        public void setIsHospitality(String isHospitality) {
            this.isHospitality = isHospitality;
        }

        public String getCapicity() {
            return capicity;
        }

        public void setCapicity(String capicity) {
            this.capicity = capicity;
        }

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

        public List<ResultBean.AvailabilityBean> getAvailability() {
            return availability;
        }

        public void setAvailability(List<ResultBean.AvailabilityBean> availability) {
            this.availability = availability;
        }

        public List<ResultBean.PreJobsBean> getPre_jobs() {
            return pre_jobs;
        }

        public void setPre_jobs(List<ResultBean.PreJobsBean> pre_jobs) {
            this.pre_jobs = pre_jobs;
        }

        public static class AvailabilityBean {
            /**
             * id : 57
             * user_id : 119
             * monday : 0,0
             * tuesday : 1,1
             * wednesday : 0,0
             * thursday : 0,0
             * friday : 0,0
             * saturday : 0,0
             * sunday : 0,0
             */

            private String id;
            private String user_id;
            private String monday;
            private String tuesday;
            private String wednesday;
            private String thursday;
            private String friday;
            private String saturday;
            private String sunday;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getMonday() {
                return monday;
            }

            public void setMonday(String monday) {
                this.monday = monday;
            }

            public String getTuesday() {
                return tuesday;
            }

            public void setTuesday(String tuesday) {
                this.tuesday = tuesday;
            }

            public String getWednesday() {
                return wednesday;
            }

            public void setWednesday(String wednesday) {
                this.wednesday = wednesday;
            }

            public String getThursday() {
                return thursday;
            }

            public void setThursday(String thursday) {
                this.thursday = thursday;
            }

            public String getFriday() {
                return friday;
            }

            public void setFriday(String friday) {
                this.friday = friday;
            }

            public String getSaturday() {
                return saturday;
            }

            public void setSaturday(String saturday) {
                this.saturday = saturday;
            }

            public String getSunday() {
                return sunday;
            }

            public void setSunday(String sunday) {
                this.sunday = sunday;
            }
        }

        public static class PreJobsBean {
            /**
             * company_id : 31
             * user_id : 119
             * name : jcjfj
             * role : mvmfck
             * start : 03/2018
             * end : 03/2018
             * description : jcjc
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


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
