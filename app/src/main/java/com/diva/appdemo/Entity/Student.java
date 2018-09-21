package com.diva.appdemo.Entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by 刘迪 on 2018/8/8 14:30.
 * 邮箱：divaliu1408@qq.com
 */
@Table(name = "student")
public class Student {
    @Column(name = "id",isId = true)
    private int id;
    @Column(name = "s_id")
    private int sID;
    @Column(name = "s_name")
    private String name;
    @Column(name = "s_sex")
    private String sex;
    @Column(name = "Math")
    private int math;
    @Column(name = "Chinese")
    private int Chinese;
    @Column(name = "English")
    private int English;

    public Student() {
    }

    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getChinese() {
        return Chinese;
    }

    public void setChinese(int chinese) {
        Chinese = chinese;
    }

    public int getEnglish() {
        return English;
    }

    public void setEnglish(int english) {
        English = english;
    }
    public String toString(){
        return "name:"+getName()
                +",id："+getId()
                +",s_id:"+getsID()
                +",sex:"+getSex()
                +",English:"+getEnglish()
                +",Math:"+getMath()
                + ",Chinese:"+getChinese();
    }
}
