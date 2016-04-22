package com.kun.allgo.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kun.allgo.Models.Room;
import com.kun.allgo.R;
import com.kun.allgo.Services.RoomService;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRoomFragment extends Fragment {

    EditText edtRoomName;
    EditText edtRoomDescription;
    Button submitBtn;
    RoomService roomService;

    public AddRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_room, container, false);

        roomService = new RoomService();
        edtRoomName = (EditText) view.findViewById(R.id.edtRoomName);
        edtRoomDescription = (EditText) view.findViewById(R.id.edtRoomDescription);
        submitBtn = (Button) view.findViewById(R.id.btnSubmitRoom);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = edtRoomName.getText().toString();
                String roomDescription = edtRoomDescription.getText().toString();

                Room room = new Room("", roomName, roomDescription, "");
                roomService.SaveNewRoom(room);
            }
        });

        return view;
    }

}
