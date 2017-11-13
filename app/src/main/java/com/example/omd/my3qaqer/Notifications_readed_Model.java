package com.example.omd.my3qaqer;

/**
 * Created by Delta on 23/06/2017.
 */

public class Notifications_readed_Model {
    boolean Value;
    String  User_id;

    public Notifications_readed_Model() {
    }

    public Notifications_readed_Model(boolean value, String user_id) {
        Value = value;
        User_id = user_id;
    }

    public boolean isValue() {
        return Value;
    }

    public void setValue(boolean value) {
        Value = value;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }
}
