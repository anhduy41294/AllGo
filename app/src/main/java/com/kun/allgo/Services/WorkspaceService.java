package com.kun.allgo.Services;

import android.util.Log;

import com.firebase.client.Firebase;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.Workspace;

import java.util.HashMap;
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

        Firebase newWorkspaceRef = workspaceRef.push();

        Map<String, Object> newWorkspace = new HashMap<String, Object>();
        newWorkspace.put("workspaceName", workspace.getmWorkspaceName());
        newWorkspace.put("workspaceDescription", workspace.getmWorkspaceDescription());
        newWorkspace.put("workspaceImage", workspace.getmImageWorkspace());
        newWorkspace.put("latitude", workspace.getmLatitude());
        newWorkspace.put("longitude", workspace.getmLongitude());
        newWorkspaceRef.setValue(newWorkspace);

        Firebase workspaceOfCurrentUserRef = new Firebase(Constant.FIREBASE_URL_USERS + "/" + GlobalVariable.currentUserId).child("workSpaces");
        workspaceOfCurrentUserRef.child(newWorkspaceRef.getKey()).setValue(true);

        return true;
    }
}
