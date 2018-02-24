package com.espressif.iot.esptouch.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/2/3 15:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Tag {


    /**
     * status : 0
     * message : 请求完成
     * data : [{"tagId":888,"status":1},{"tagId":110,"status":0},{"tagId":1,"status":1},{"tagId":16,"status":1}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tagId : 888
         * status : 1
         */

        private String tagId;
        private int status;

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
