package com.kun.allgo.Models;

import java.util.ArrayList;

/**
 * App User
 */
public class AppUser {
    private String mUserId;
    private String mUserName;
    private String mEmail;
    private ArrayList<String> listWorkSpace;


    public AppUser(String mUserId ,String mUserName, String mEmail) {
        this.mUserId = mUserId;
        this.mUserName = mUserName;
        this.mEmail = mEmail;
    }

    public AppUser() {
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public ArrayList<String> getListWorkSpace() {
        return listWorkSpace;
    }

    public void setListWorkSpace(ArrayList<String> listWorkSpace) {
        this.listWorkSpace = listWorkSpace;
    }
}
