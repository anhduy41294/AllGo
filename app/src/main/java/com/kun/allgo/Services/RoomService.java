package com.kun.allgo.Services;

import com.firebase.client.Firebase;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.Room;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duy on 22-Apr-16.
 */
public class RoomService {

    private Firebase roomRef = new Firebase(Constant.FIREBASE_URL_ROMS);

    public RoomService(){

    }

    public boolean SaveNewRoom(Room room){

        Firebase newRoomRef = roomRef.push();

        Map<String, Object> newRoom = new HashMap<>();
        newRoom.put("roomName", room.getmRoomName());
        newRoom.put("roomDescription", room.getmRoomDescription());
        //newRoom.put("roomImage", room.getmImageRoom());
        newRoomRef.setValue(newRoom);
        newRoomRef.child("workspaces").child(GlobalVariable.currentWorkspaceId).setValue(true);

        return true;
    }
}
