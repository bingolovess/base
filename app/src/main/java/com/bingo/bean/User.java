package com.bingo.bean;

import java.util.List;

/**
 * Created by bingo on 2018/12/28.
 * Time:2018/12/28
 */

public class User {

    /**
     * code : 200
     * msg : 数据访问成功
     * data : [{"id":1,"name":"bingo1","age":20,"address":"安徽合肥"},{"id":2,"name":"bingo2","age":20,"address":"安徽安庆"},{"id":3,"name":"bingo3","age":20,"address":"四川成都"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : bingo1
         * age : 20
         * address : 安徽合肥
         */

        private int id;
        private String name;
        private int age;
        private String address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
