package com.git.hubreeh.model.jobseeker;

import java.util.List;

/**
 * Created by Warlock on 3/15/2018.
 */

public class MessageBeans {
    private int status;
    private String message;
    private List<ContactsBean> contacts;
    private List<MessagedBean> messaged;

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

    public List<ContactsBean> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactsBean> contacts) {
        this.contacts = contacts;
    }

    public List<MessagedBean> getMessaged() {
        return messaged;
    }

    public void setMessaged(List<MessagedBean> messaged) {
        this.messaged = messaged;
    }

    public static class ContactsBean {
        /**
         * room_id : 1
         * user_id : 119
         * business_id : 1
         * contactName : xhdjjd
         * address1 : ddhdh
         */

        private String room_id;
        private String user_id;
        private String business_id;
        private String contactName;
        private String address1;

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
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
    }

    public static class MessagedBean {
        /**
         * room_id : 1
         * user_id : 119
         * business_id : 1
         * contactName : xhdjjd
         * address1 : ddhdh
         * message_count : 5
         * last_message : hello
         * last_created : 10:10
         */

        private String room_id;
        private String user_id;
        private String business_id;
        private String contactName;
        private String address1;
        private String message_count;
        private String last_message;
        private String last_created;

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
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

        public String getMessage_count() {
            return message_count;
        }

        public void setMessage_count(String message_count) {
            this.message_count = message_count;
        }

        public String getLast_message() {
            return last_message;
        }

        public void setLast_message(String last_message) {
            this.last_message = last_message;
        }

        public String getLast_created() {
            return last_created;
        }

        public void setLast_created(String last_created) {
            this.last_created = last_created;
        }
    }
}
