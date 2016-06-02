package com.kun.allgo.Models;

/**
 * Local Account
 */
public class LocalAccount {

    private String mIdLocalAccount;
    private String mUserNameLC;
    private String mPasswordLC;
    private AccountType mAccountType;
    private String mAccountDescription;

    public LocalAccount(String mIdLocalAccount, String mUserNameLC, String mPasswordLC, String mAccountDescription) {
        this.mIdLocalAccount = mIdLocalAccount;
        this.mUserNameLC = mUserNameLC;
        this.mPasswordLC = mPasswordLC;
        this.mAccountDescription = mAccountDescription;
    }

    public LocalAccount() {
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

    public AccountType getAccountType() {
        return mAccountType;
    }

    public void setAccountType(AccountType mAccountType) {
        this.mAccountType = mAccountType;
    }

    public String getmAccountDescription() {
        return mAccountDescription;
    }

    public void setmAccountDescription(String mAccountDescription) {
        this.mAccountDescription = mAccountDescription;
    }
}
