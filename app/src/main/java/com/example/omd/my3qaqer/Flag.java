package com.example.omd.my3qaqer;

/**
 * Created by Delta on 17/06/2017.
 */

public class Flag {

    public static boolean isUser_notf_readed() {
        return user_notf_readed;
    }

    public static void setUser_notf_readed(boolean user_notf_readed) {
        Flag.user_notf_readed = user_notf_readed;
    }

    public static boolean user_notf_readed = false;
    public static boolean notfReaded = false;

    public static boolean isNotfReaded() {
        return notfReaded;
    }

    public static void setNotfReaded(boolean notfReaded) {
        Flag.notfReaded = notfReaded;
    }

    public static boolean flag = false;
    public static boolean isFlag_notification() {
        return flag_notification;
    }

    public static void setFlag_notification(boolean flag_notification) {
        Flag.flag_notification = flag_notification;
    }

    public static boolean flag_notification = false;
    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        Flag.flag = flag;
    }
}
