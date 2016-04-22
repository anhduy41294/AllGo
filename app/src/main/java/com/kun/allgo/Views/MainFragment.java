package com.kun.allgo.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.Workspace;
import com.kun.allgo.R;
import com.kun.allgo.Services.WorkspaceService;
import com.kun.allgo.Views.Adapter.WorkspaceAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private WorkspaceAdapter workspaceAdapter;
    private RecyclerView recyclerViewWorkspace;
    private View view;
    private WorkspaceService workspaceService;
    public List<Workspace> listWorkspace = new ArrayList<>();

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES);
        workspaceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot workspaceSnap: dataSnapshot.getChildren()) {
                    if (workspaceSnap.child("users/" + GlobalVariable.currentUserId).getValue() != null){
                        String workspaceName = workspaceSnap.child("workspaceName").getValue().toString();
                        String workspaceDescription = workspaceSnap.child("workspaceDescription").getValue().toString();
                        String workspaceImage = workspaceSnap.child("workspaceImage").getValue().toString();
                        Double latitude = Double.valueOf(workspaceSnap.child("latitude").getValue().toString());
                        Double longitude = Double.valueOf(workspaceSnap.child("longitude").getValue().toString());

                        Workspace workspace = new Workspace(workspaceSnap.getKey(), workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
                        listWorkspace.add(workspace);
                    }
                }
                getFormWidget();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //getFormWidget();
        // Inflate the layout for this fragment
        return view;
    }

    private void getFormWidget() {
        recyclerViewWorkspace = (RecyclerView) view.findViewById(R.id.rcvWorkspace);
        recyclerViewWorkspace.setHasFixedSize(true);
        recyclerViewWorkspace.setLayoutManager(new LinearLayoutManager(getActivity()));
        workspaceAdapter = new WorkspaceAdapter(getContext(), listWorkspace);

        recyclerViewWorkspace.setAdapter(workspaceAdapter);


    }


}
