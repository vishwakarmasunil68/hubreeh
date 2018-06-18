package com.git.hubreeh.model.jobseeker;

import java.util.List;

/**
 * Created by Hp on 2/17/2018.
 */

public class CategoryBean {


    /**
     * status : 1
     * message : successfully get.
     * hospitality : [{"category_id":"1","name":"Bar Back","image":"https://png.icons8.com/metro/1600/bank-cards.png"},{"category_id":"2","name":"Barista","image":"https://png.icons8.com/metro/1600/bank-cards.png"}]
     * warehousing : [{"category_id":"3","name":"Delivery Driver","image":"https://png.icons8.com/metro/1600/bank-cards.png"},{"category_id":"4","name":"Food","image":"https://png.icons8.com/metro/1600/bank-cards.png"}]
     */

    private int status;
    private String message;
    private List<HospitalityBean> hospitality;
    private List<WarehousingBean> warehousing;
    private String isSelected="";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
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

    public List<HospitalityBean> getHospitality() {
        return hospitality;
    }

    public void setHospitality(List<HospitalityBean> hospitality) {
        this.hospitality = hospitality;
    }

    public List<WarehousingBean> getWarehousing() {
        return warehousing;
    }

    public void setWarehousing(List<WarehousingBean> warehousing) {
        this.warehousing = warehousing;
    }

    public static class HospitalityBean {
        /**
         * category_id : 1
         * name : Bar Back
         * image : https://png.icons8.com/metro/1600/bank-cards.png
         */

        private String category_id;
        private String name;
        private String image;
        boolean isSelect ;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class WarehousingBean {
        /**
         * category_id : 3
         * name : Delivery Driver
         * image : https://png.icons8.com/metro/1600/bank-cards.png
         */

        private String category_id;
        private String name;
        private String image;
        boolean isSeleted;

        public boolean isSeleted() {
            return isSeleted;
        }

        public void setSeleted(boolean seleted) {
            isSeleted = seleted;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
