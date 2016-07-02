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
    private String randomP;
    private String masterEncryptedKey;
    private String randomK;
    private String hashP;

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getRandomP() {
        return randomP;
    }

    public void setRandomP(String randomP) {
        this.randomP = randomP;
    }

    public String getMasterEncryptedKey() {
        return masterEncryptedKey;
    }

    public void setMasterEncryptedKey(String masterEncryptedKey) {
        this.masterEncryptedKey = masterEncryptedKey;
    }

    public String getRandomK() {
        return randomK;
    }

    public void setRandomK(String randomK) {
        this.randomK = randomK;
    }

    public String getHashP() {
        return hashP;
    }

    public void setHashP(String hashP) {
        this.hashP = hashP;
    }

    public AppUser(String mUserId , String mUserName, String mEmail) {
        this.mUserId = mUserId;
        this.mUserName = mUserName;
        this.mEmail = mEmail;
    }

    public AppUser(String mUserId, String mUserName, String mEmail, String randomP, String masterEncryptedKey, String randomK, String hashP) {
        this.mUserId = mUserId;
        this.mUserName = mUserName;
        this.mEmail = mEmail;
        this.randomP = randomP;
        this.masterEncryptedKey = masterEncryptedKey;
        this.randomK = randomK;
        this.hashP = hashP;
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
