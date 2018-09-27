package com.diva.appdemo.Entity;


import com.huashi.bluetooth.IDCardInfo;

/**
 * Created by 刘迪 on 2018/9/25 14:40.
 * 邮箱：divaliu1408@qq.com
 */

public class Info {

    static private String info_PeopleName = "";
    static private String info_Sex = "";
    static private String info_People = "";
    static private String info_BirthDay = "";
    static private String info_Addr = "";
    static private String info_IDCard = "";
    static private String info_Department = "";
    static private String info_StartDate = "";
    static private String info_newAddr = "";
    static private String info_EndDate = "";
    static private boolean info_check ;
    static private int info_num;

    public static String getInfo_PeopleName() {
        return info_PeopleName;
    }

    public static void setInfo_PeopleName(String info_PeopleName) {
        Info.info_PeopleName = info_PeopleName;
    }

    public static String getInfo_Sex() {
        return info_Sex;
    }

    public static void setInfo_Sex(String info_Sex) {
        Info.info_Sex = info_Sex;
    }

    public static String getInfo_People() {
        return info_People;
    }

    public static void setInfo_People(String info_People) {
        Info.info_People = info_People;
    }

    public static String getInfo_BirthDay() {
        return info_BirthDay;
    }

    public static void setInfo_BirthDay(String info_BirthDay) {
        Info.info_BirthDay = info_BirthDay;
    }

    public static String getInfo_Addr() {
        return info_Addr;
    }

    public static void setInfo_Addr(String info_Addr) {
        Info.info_Addr = info_Addr;
    }

    public static String getInfo_IDCard() {
        return info_IDCard;
    }

    public static void setInfo_IDCard(String info_IDCard) {
        Info.info_IDCard = info_IDCard;
    }

    public static String getInfo_Department() {
        return info_Department;
    }

    public static void setInfo_Department(String info_Department) {
        Info.info_Department = info_Department;
    }

    public static String getInfo_StartDate() {
        return info_StartDate;
    }

    public static void setInfo_StartDate(String info_StartDate) {
        Info.info_StartDate = info_StartDate;
    }

    public static String getInfo_newAddr() {
        return info_newAddr;
    }

    public static void setInfo_newAddr(String info_newAddr) {
        Info.info_newAddr = info_newAddr;
    }

    public static String getInfo_EndDate() {
        return info_EndDate;
    }

    public static void setInfo_EndDate(String info_EndDate) {
        Info.info_EndDate = info_EndDate;
    }

    public static boolean isInfo_check() {
        return info_check;
    }

    public static void setInfo_check(boolean info_check) {
        Info.info_check = info_check;
    }

    public static int getInfo_num() {
        return info_num;
    }

    public static void setInfo_num(int info_num) {
        Info.info_num = info_num;
    }


    public static String toShow() {
        return "Info{"+
                "name = "+ getInfo_PeopleName()
                +",sex = "+getInfo_Sex()
                +",people = "+getInfo_People()
                +",birthday = "+getInfo_BirthDay()
                +",address = "+getInfo_Addr()
                +",idCard = "+getInfo_IDCard()
                +",department = " +getInfo_Department()
                +",startDate = "+getInfo_StartDate()
                +",endDate = " +getInfo_EndDate()
                +"}";
    }
    public static void saveInfo(IDCardInfo ic) {
        Info.setInfo_PeopleName(ic.getPeopleName());
        Info.setInfo_Sex(ic.getSex());
        Info.setInfo_People(ic.getPeople());
        Info.setInfo_BirthDay(ic.getBirthDay());
        Info.setInfo_Addr(ic.getAddr());
        Info.setInfo_IDCard(ic.getIDCard());
        Info.setInfo_Department(ic.getDepartment());
        Info.setInfo_StartDate(ic.getStrartDate());
        Info.setInfo_EndDate(ic.getEndDate());
        Info.setInfo_check(true);
    }
}
