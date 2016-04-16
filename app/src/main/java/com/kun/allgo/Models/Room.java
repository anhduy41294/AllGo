package com.kun.allgo.Models;

import java.util.ArrayList;

/**
 * Room
 */
public class Room {
    private int mIdRoom;
    private String mRoomName;
    private String mRoomDescription;
    private int mImageRoom;
    private ArrayList<LocalAccount> mListLocalAccount;

    public Room(int mIdRoom, String mRoomName, String mRoomDescription, int mImageRoom, ArrayList<LocalAccount> mListLocalAccount) {
        this.mIdRoom = mIdRoom;
        this.mRoomName = mRoomName;
        this.mRoomDescription = mRoomDescription;
        this.mImageRoom = mImageRoom;
        this.mListLocalAccount = mListLocalAccount;
    }

    public Room() {
    }

    public int getIdRoom() {
        return mIdRoom;
    }

    public void setIdRoom(int mIdRoom) {
        this.mIdRoom = mIdRoom;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String mRoomName) {
        this.mRoomName = mRoomName;
    }

    public String getRoomDescription() {
        return mRoomDescription;
    }

    public void setRoomDescription(String mRoomDescription) {
        this.mRoomDescription = mRoomDescription;
    }

    public int getImageRoom() {
        return mImageRoom;
    }

    public void setImageRoom(int mImageRoom) {
        this.mImageRoom = mImageRoom;
    }

    public ArrayList<LocalAccount> getListLocalAccount() {
        return mListLocalAccount;
    }

    public void setListLocalAccount(ArrayList<LocalAccount> mListLocalAccount) {
        this.mListLocalAccount = mListLocalAccount;
    }
}
