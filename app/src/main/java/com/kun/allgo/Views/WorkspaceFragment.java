package com.kun.allgo.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

    public WorkspaceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workspace, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Workspaces");
        Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES);
        workspaceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listWorkspace.clear();
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
                addEvent();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        Firebase workspaceOfUserRef = new Firebase(Constant.FIREBASE_URL_USERS + "/" + GlobalVariable.currentUserId).child("workSpaces");
//        workspaceOfUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                listWorkspace.clear();
//                for (DataSnapshot workspaceSnap: dataSnapshot.getChildren()) {
//                    final String workspaceKey = workspaceSnap.getKey();
//                    Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES + "/" + workspaceKey);
//
//                    workspaceRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String workspaceName = dataSnapshot.child("workspaceName").getValue().toString();
//                            String workspaceDescription = dataSnapshot.child("workspaceDescription").getValue().toString();
//                            String workspaceImage = dataSnapshot.child("workspaceImage").getValue().toString();
//                            Double latitude = Double.valueOf(dataSnapshot.child("latitude").getValue().toString());
//                            Double longitude = Double.valueOf(dataSnapshot.child("longitude").getValue().toString());
//
//                            Workspace workspace = new Workspace(workspaceKey, workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
//                            listWorkspace.add(workspace);
//                            workspaceAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    });
//                }
//                getFormWidget();
//                addEvent();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

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

        fab = (FloatingActionButton) view.findViewById(R.id.fabAddWorkspace);

    }

    private List<Workspace> getWorkspaceData() {

        List<Workspace> workspaces = new ArrayList<Workspace>();
        workspaces.add(new Workspace());
        return workspaces;
    }
}
