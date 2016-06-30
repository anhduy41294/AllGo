package com.kun.allgo.Models;

import java.util.ArrayList;

/**
 * Room
 */
public class Room {
    private String mIdRoom;
    private String mRoomName;
    private String mRoomDescription;

    public Room(String mIdRoom, String mRoomName, String mRoomDescription) {
        this.mIdRoom = mIdRoom;
        this.mRoomName = mRoomName;
        this.mRoomDescription = mRoomDescription;
    }

    public Room() {
    }

    public String getmIdRoom() {
        return mIdRoom;
    }

    public void setmIdRoom(String mIdRoom) {
        this.mIdRoom = mIdRoom;
    }

    public String getmRoomName() {
        return mRoomName;
    }

    public void setmRoomName(String mRoomName) {
        this.mRoomName = mRoomName;
    }

    public String getmRoomDescription() {
        return mRoomDescription;
    }

    public void setmRoomDescription(String mRoomDescription) {
        this.mRoomDescription = mRoomDescription;
    }
}
