package com.kun.allgo.Models;

/**
 * Account Type
 */
public class AccountType {
    private int mIdAccountType;
    private String mAccountTypeName;

    public AccountType(int mIdAccountType, String mAccountTypeName) {
        this.mIdAccountType = mIdAccountType;
        this.mAccountTypeName = mAccountTypeName;
    }

    public AccountType() {
    }

    public int getIdAccountType() {
        return mIdAccountType;
    }

    public void setIdAccountType(int mIdAccountType) {
        this.mIdAccountType = mIdAccountType;
    }

    public String getAccountTypeName() {
        return mAccountTypeName;
    }

    public void setAccountTypeName(String mAccountTypeName) {
        this.mAccountTypeName = mAccountTypeName;
    }
}
