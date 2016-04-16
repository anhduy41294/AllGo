package com.kun.allgo.Models;

/**
 * Local Account
 */
public class LocalAccount {

    private int mIdLocalAccount;
    private String mUserNameLC;
    private String mPasswordLC;
    private AccountType mAccountType;

    public LocalAccount(int mIdLocalAccount, String mUserNameLC, String mPasswordLC, AccountType mAccountType) {
        this.mIdLocalAccount = mIdLocalAccount;
        this.mUserNameLC = mUserNameLC;
        this.mPasswordLC = mPasswordLC;
        this.mAccountType = mAccountType;
    }

    public LocalAccount() {
    }

    public int getIdLocalAccount() {
        return mIdLocalAccount;
    }

    public void setIdLocalAccount(int mIdLocalAccount) {
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
}
