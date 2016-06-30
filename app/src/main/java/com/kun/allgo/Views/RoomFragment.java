package com.kun.allgo.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.Room;
import com.kun.allgo.Models.Workspace;
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
    public List<Room> listRoom = new ArrayList<>();
    public List<String> listRoomId = new ArrayList<>();
    public RoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fabAddRoom);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rooms");

        addEvent();
        listRoom.clear();
        listRoomId.clear();
        getRoomIdData();
        return view;
    }

    private void addEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRoomFragment addRoomFragment = new AddRoomFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addRoomFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void getFormWidget() {
        recyclerViewRoom = (RecyclerView) view.findViewById(R.id.rcvRoom);
        recyclerViewRoom.setHasFixedSize(true);
        recyclerViewRoom.setLayoutManager(new LinearLayoutManager(getActivity()));
        roomAdapter = new RoomAdapter(getContext(), listRoom, getActivity().getSupportFragmentManager());

        recyclerViewRoom.setAdapter(roomAdapter);

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerViewRoom,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {
                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    listRoom.remove(position);
                                    roomAdapter.notifyItemRemoved(position);
                                }
                                roomAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    listRoom.remove(position);
                                    roomAdapter.notifyItemRemoved(position);
                                }
                                roomAdapter.notifyDataSetChanged();
                            }
                        });

        recyclerViewRoom.addOnItemTouchListener(swipeTouchListener);

    }

    private void getRoomIdData() {

        Firebase workSpaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES + "/" + GlobalVariable.currentWorkspaceId);
        workSpaceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    if (snap.getKey() == "rooms") {
                        for (DataSnapshot rsnap : snap.getChildren()) {
                            //Log.d("manhduydl", wsnap.getKey());
                            String key = rsnap.getKey();
                            listRoomId.add(key);
                        }
                    }
                }
                getRoomData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getRoomData() {
        for (String RoomId : listRoomId) {
            Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + RoomId);
            workspaceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String roomName = dataSnapshot.child("roomName").getValue().toString();
                    String roomDescription = dataSnapshot.child("roomDescription").getValue().toString();
                    //String roomImage = dataSnapshot.child("roomImage").getValue().toString();

                    //Workspace workspace = new Workspace(dataSnapshot.getKey(), workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
                    //listWorkspace.add(workspace);
                    Room rom = new Room(dataSnapshot.getKey(), roomName, roomDescription);
                    listRoom.add(rom);
                    getFormWidget();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
}

