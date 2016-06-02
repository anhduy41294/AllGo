package com.kun.allgo.Services;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.Workspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Duy on 21-Apr-16.
 */
public class WorkspaceService {

    private Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES);

    public WorkspaceService() {

    }

    public boolean SaveNewWorkspace(Workspace workspace){

        final Firebase newWorkspaceRef = workspaceRef.push();

        Map<String, Object> newWorkspace = new HashMap<String, Object>();
        newWorkspace.put("workspaceName", workspace.getmWorkspaceName());
        newWorkspace.put("workspaceDescription", workspace.getmWorkspaceDescription());
        newWorkspace.put("workspaceImage", workspace.getmImageWorkspace());
        newWorkspace.put("latitude", workspace.getmLatitude());
        newWorkspace.put("longitude", workspace.getmLongitude());
        newWorkspaceRef.setValue(newWorkspace);
        newWorkspaceRef.child("users").child(GlobalVariable.currentUserId).setValue(true, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Firebase workspaceOfCurrentUserRef = new Firebase(Constant.FIREBASE_URL_USERS + "/" + GlobalVariable.currentUserId).child("workSpaces");
                    workspaceOfCurrentUserRef.child(newWorkspaceRef.getKey()).setValue(true, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                        }
                    });
                }
            }
        });



        return true;
    }

//    public Workspace LoadWorkspace(final String workspaceId){
//        workspaceRef.child(workspaceId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String workspaceName = dataSnapshot.child("workspaceName").getValue().toString();
//                String workspaceDescription = dataSnapshot.child("workspaceDescription").getValue().toString();
//                String workspaceImage = dataSnapshot.child("workspaceImage").getValue().toString();
//                Double latitude = Double.valueOf(dataSnapshot.child("latitude").getValue().toString());
//                Double longitude = Double.valueOf(dataSnapshot.child("longitude").getValue().toString());
//
//                Workspace workspace = new Workspace(workspaceId, workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//    }

//    public List<Workspace> LoadAllWorkspaceOfCurrentUser() {
//        final List<Workspace> workspaces = new ArrayList<Workspace>();
//
//        Firebase workspaceOfUserRef = new Firebase(Constant.FIREBASE_URL_USERS + "/" + GlobalVariable.currentUserId).child("workSpaces");
//        Log.d("abc", Constant.FIREBASE_URL_USERS + "/" + GlobalVariable.currentUserId + "/workSpaces");
//
//        workspaceOfUserRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                final String workspaceKey = dataSnapshot.getKey();
//                Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES + "/" + workspaceKey);
//
//               workspaceRef.addValueEventListener(new ValueEventListener() {
//                   @Override
//                   public void onDataChange(DataSnapshot dataSnapshot) {
//                       String workspaceName = dataSnapshot.child("workspaceName").getValue().toString();
//                       String workspaceDescription = dataSnapshot.child("workspaceDescription").getValue().toString();
//                       String workspaceImage = dataSnapshot.child("workspaceImage").getValue().toString();
//                       Double latitude = Double.valueOf(dataSnapshot.child("latitude").getValue().toString());
//                       Double longitude = Double.valueOf(dataSnapshot.child("longitude").getValue().toString());
//
//                       Workspace workspace = new Workspace(workspaceKey, workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
//                       workspaces.add(workspace);
//                   }
//
//                   @Override
//                   public void onCancelled(FirebaseError firebaseError) {
//
//                   }
//               });
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//
//        });
//
//        return workspaces;
//    }
}
