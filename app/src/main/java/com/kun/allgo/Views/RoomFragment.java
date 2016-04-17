package com.kun.allgo.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.allgo.Models.Room;
import com.kun.allgo.R;
import com.kun.allgo.Views.Adapter.RoomAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {

    private RoomAdapter roomAdapter;
    private RecyclerView recyclerViewRoom;
    private View view;
    private FloatingActionButton fab;
    public RoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room, container, false);

        getFormWidget();
        addEvent();
        return view;
    }
    private void addEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddRoomActivity.class);
                startActivity(i);
            }
        });
    }

    private void getFormWidget() {
        recyclerViewRoom = (RecyclerView) view.findViewById(R.id.rcvWorkspace);
        roomAdapter = new RoomAdapter(getContext(),getRoomData());

        recyclerViewRoom.setAdapter(roomAdapter);

        fab = (FloatingActionButton) view.findViewById(R.id.fabAddRoom);

    }

    private List<Room> getRoomData() {

        List<Room> room = new ArrayList<Room>();
        room.add(new Room());
        return room;
    }
}

