package com.kun.allgo.Models;

import java.util.ArrayList;

/**
 * App User
 */
public class AppUser {

    private int mIdUser;
    private String mUserName;
    private String mEmail;
    private String mFullName;
    private ArrayList<Workspace> mListWorkspace;

    public AppUser(int mIdUser, String mUserName, String mEmail, String mFullName, ArrayList<Workspace> mListWorkspace) {
        this.mIdUser = mIdUser;
        this.mUserName = mUserName;
        this.mEmail = mEmail;
        this.mFullName = mFullName;
        this.mListWorkspace = mListWorkspace;
    }

    public AppUser(String mUserName, String mEmail){
        this.mUserName = mUserName;
        this.mEmail = mEmail;
    }

    public AppUser() {
    }

    public int getIdUser() {
        return mIdUser;
    }

    public void setIdUser(int mIdUser) {
        this.mIdUser = mIdUser;
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

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public ArrayList<Workspace> getListWorkspace() {
        return mListWorkspace;
    }

    public void setListWorkspace(ArrayList<Workspace> mListWorkspace) {
        this.mListWorkspace = mListWorkspace;
    }
}
