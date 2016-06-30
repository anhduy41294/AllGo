package com.kun.allgo.Models;

/**
 * Created by Duy on 30-Jun-16.
 */
public class ApplicationAccount {

    private String mIdApplicationAccount;
    private String mAppUsername;
    private String mAppPassword;
    private String mAppDescription;
    private String mAppEmail;
    private String mAppType;

    public ApplicationAccount(String Id, String Username, String Pass, String Description, String Email, String AppType) {
        this.mIdApplicationAccount = Id;
        this.mAppUsername = Username;
        this.mAppPassword = Pass;
        this.mAppEmail = Email;
        this.mAppDescription = Description;
        this.mAppType = AppType;
    }

    public String getmAppEmail() {
        return mAppEmail;
    }

    public void setmAppEmail(String mAppEmail) {
        this.mAppEmail = mAppEmail;
    }

    public String getmIdApplicationAccount() {
        return mIdApplicationAccount;
    }

    public void setmIdApplicationAccount(String mIdApplicationAccount) {
        this.mIdApplicationAccount = mIdApplicationAccount;
    }

    public String getmAppUsername() {
        return mAppUsername;
    }

    public void setmAppUsername(String mAppUsername) {
        this.mAppUsername = mAppUsername;
    }

    public String getmAppPassword() {
        return mAppPassword;
    }

    public void setmAppPassword(String mAppPassword) {
        this.mAppPassword = mAppPassword;
    }

    public String getmAppDescription() {
        return mAppDescription;
    }

    public void setmAppDescription(String mAppDescription) {
        this.mAppDescription = mAppDescription;
    }

    public String getmAppType() {
        return mAppType;
    }

    public void setmAppType(String mAppType) {
        this.mAppType = mAppType;
    }

}
