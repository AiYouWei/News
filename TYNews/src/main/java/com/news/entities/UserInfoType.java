package com.news.entities;

import android.text.TextUtils;

import org.androidx.frames.entity.BaseType;

/**
 * @author slioe shu
 */
public class UserInfoType implements BaseType {
    private String work;
    private String home;
    private String sex;
    private String phone;
    private String nick;
    private String age;
    private String name;
    private String contact;

    public String getWork() {
        return replaceNull(work);
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHome() {
        return replaceNull(home);
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getSex() {
        if ("1".equals(sex)) {
            return "男";
        } else if ("0".equals(sex)) {
            return "女";
        }
        return "";
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return replaceNull(phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNick() {
        return replaceNull(nick);
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAge() {
        return replaceNull(age);
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return replaceNull(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return replaceNull(contact);
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    private String replaceNull(String str) {
        return (TextUtils.isEmpty(str)) ? "" : str;
    }
}
