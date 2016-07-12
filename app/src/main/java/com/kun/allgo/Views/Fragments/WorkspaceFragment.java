package com.kun.allgo.Views.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.kun.allgo.Views.Adapter.WorkspaceAdapter;
import com.kun.allgo.Views.Activities.AddWorkspaceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkspaceFragment extends Fragment {

    private WorkspaceAdapter workspaceAdapter;
    private RecyclerView recyclerViewWorkspace;
    private View view;

    private FloatingActionButton fab;
    public List<Workspace> listWorkspace = new ArrayList<>();
    ProgressDialog progressDialog;

    public WorkspaceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workspace, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fabAddWorkspace);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Workspaces");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.show();

        listWorkspace.clear();
        addEvent();
        for (String workSpaceId : GlobalVariable.CurrentAppUser.getListWorkSpace()) {
            Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES + "/" + workSpaceId);
            workspaceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String workspaceName = dataSnapshot.child("workspaceName").getValue().toString();
                    String workspaceDescription = dataSnapshot.child("workspaceDescription").getValue().toString();
                    String workspaceImage = dataSnapshot.child("workspaceImage").getValue().toString();
                    Double latitude = Double.valueOf(dataSnapshot.child("latitude").getValue().toString());
                    Double longitude = Double.valueOf(dataSnapshot.child("longitude").getValue().toString());

                    float distance = 0.0f;
                    if (GlobalVariable.currentLocation != null) {
                        Location workspaceLocation = new Location("workspace");
                        workspaceLocation.setLatitude(latitude);
                        workspaceLocation.setLongitude(longitude);

                        distance = workspaceLocation.distanceTo(GlobalVariable.currentLocation);
                    }

                    Workspace workspace = new Workspace(dataSnapshot.getKey(), workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
                    workspace.setmDistance(distance);
                    listWorkspace.add(workspace);
                    getFormWidget();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 1000);
        // Inflate the layout for this fragment
        return view;
    }

    private void addEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AddWorkspaceActivity.class);
                startActivity(i);
            }
        });
    }

    private void getFormWidget() {
        recyclerViewWorkspace = (RecyclerView) view.findViewById(R.id.rcvWorkspace);
        recyclerViewWorkspace.setHasFixedSize(true);
        recyclerViewWorkspace.setLayoutManager(new LinearLayoutManager(getActivity()));
        workspaceAdapter = new WorkspaceAdapter(getContext(),listWorkspace, getActivity().getSupportFragmentManager());

        recyclerViewWorkspace.setAdapter(workspaceAdapter);
    }

    private List<Workspace> getWorkspaceData() {

        List<Workspace> workspaces = new ArrayList<Workspace>();
        workspaces.add(new Workspace());
        return workspaces;
    }
}
