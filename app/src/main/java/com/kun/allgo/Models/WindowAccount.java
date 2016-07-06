package com.kun.allgo.Models;

/**
 * Created by Duy on 04-Jul-16.
 */
public class WindowAccount {
    private String mIdLocalAccount;
    private String mUserNameLC;
    private String mPasswordLC;
    private String mAccountDescription;
    private String mIP;

    public WindowAccount(String mIdLocalAccount, String mUserNameLC, String mPasswordLC, String mAccountDescription) {
        this.mIdLocalAccount = mIdLocalAccount;
        this.mUserNameLC = mUserNameLC;
        this.mPasswordLC = mPasswordLC;
        this.mAccountDescription = mAccountDescription;
    }

    public WindowAccount(String mIdLocalAccount, String mUserNameLC, String mPasswordLC, String mAccountDescription, String mIP) {
        this.mIdLocalAccount = mIdLocalAccount;
        this.mUserNameLC = mUserNameLC;
        this.mPasswordLC = mPasswordLC;
        this.mAccountDescription = mAccountDescription;
        this.mIP = mIP;
    }

    public WindowAccount() {
    }

    public String getIdLocalAccount() {
        return mIdLocalAccount;
    }

    public void setIdLocalAccount(String mIdLocalAccount) {
        this.mIdLocalAccount = mIdLocalAccount;
    }

    public String getUserNameLC() {
        return mUserNameLC;
    }

    public void setUserNameLC(String mUserNameLC) {
        this.mUserNameLC = mUserNameLC;
    }

    public String getPasswordLC() {
        return mPasswordLC;
    }

    public void setPasswordLC(String mPasswordLC) {
        this.mPasswordLC = mPasswordLC;
    }

    public String getmAccountDescription() {
        return mAccountDescription;
    }

    public void setmAccountDescription(String mAccountDescription) {
        this.mAccountDescription = mAccountDescription;
    }

    public String getmIP() {
        return mIP;
    }

    public void setmIP(String mIP) {
        this.mIP = mIP;
    }
}

