package com.kun.allgo.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rooms");

        getFormWidget();
        addEvent();
        return view;
    }
    private void addEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRoomFragment addRoomFragment = new AddRoomFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, addRoomFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void getFormWidget() {
        recyclerViewRoom = (RecyclerView) view.findViewById(R.id.rcvRoom);
        recyclerViewRoom.setHasFixedSize(true);
        recyclerViewRoom.setLayoutManager(new LinearLayoutManager(getActivity()));
        roomAdapter = new RoomAdapter(getContext(),getRoomData());

        recyclerViewRoom.setAdapter(roomAdapter);

        fab = (FloatingActionButton) view.findViewById(R.id.fabAddRoom);

    }

    private List<Room> getRoomData() {

        List<Room> room = new ArrayList<Room>();
        return room;
    }
}

