package com.kun.allgo.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.Room;
import com.kun.allgo.R;
import com.kun.allgo.Services.RoomService;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRoomFragment extends Fragment {

    EditText edtRoomName;
    EditText edtRoomDescription;
    Button submitBtn;
    RoomService roomService;
    private Firebase roomRef = new Firebase(Constant.FIREBASE_URL_ROMS);

    public AddRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Room");

        roomService = new RoomService();
        edtRoomName = (EditText) view.findViewById(R.id.edtRoomName);
        edtRoomDescription = (EditText) view.findViewById(R.id.edtRoomDescription);
        submitBtn = (Button) view.findViewById(R.id.btnSubmitRoom);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRoom();
            }
        });

        return view;
    }

    private void saveRoom() {
        String roomName = edtRoomName.getText().toString();
        String roomDescription = edtRoomDescription.getText().toString();

        Room room = new Room("", roomName, roomDescription);
        final Firebase newRoomRef = roomRef.push();

        Map<String, Object> newRoom = new HashMap<>();
        newRoom.put("roomName", room.getmRoomName());
        newRoom.put("roomDescription", room.getmRoomDescription());
//        newRoom.put("roomImage", room.getmImageRoom());
        newRoomRef.setValue(newRoom);

        newRoomRef.child("workspaces").child(GlobalVariable.currentWorkspaceId).setValue(true, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Firebase workspaceOfCurrentRoomRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES + "/" + GlobalVariable.currentWorkspaceId).child("rooms");
                workspaceOfCurrentRoomRef.child(newRoomRef.getKey()).setValue(true, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                        RoomFragment roomFragment = new RoomFragment();
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragment_container, roomFragment)
//                                .addToBackStack(null)
//                                .commit();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });
    }

}
