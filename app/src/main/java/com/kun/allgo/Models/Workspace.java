package com.kun.allgo.Models;

import java.util.ArrayList;

/**
 * Work Space
 */
public class Workspace {
    private String mIdWorkspace;
    private String mWorkspaceName;
    private String mWorkspaceDescription;
    private String mImageWorkspace;
    private double mLongitude;
    private double mLatitude;


    public Workspace(String mIdWorkspace, String mWorkspaceName, String mWorkspaceDescription, String mImageWorkspace, double mLongitude, double mLatitude) {
        this.mIdWorkspace = mIdWorkspace;
        this.mWorkspaceName = mWorkspaceName;
        this.mWorkspaceDescription = mWorkspaceDescription;
        this.mImageWorkspace = mImageWorkspace;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
    }

    public Workspace() {
    }

    public String getmIdWorkspace() {
        return mIdWorkspace;
    }

    public void setmIdWorkspace(String mIdWorkspace) {
        this.mIdWorkspace = mIdWorkspace;
    }

    public String getmWorkspaceName() {
        return mWorkspaceName;
    }

    public void setmWorkspaceName(String mWorkspaceName) {
        this.mWorkspaceName = mWorkspaceName;
    }

    public String getmWorkspaceDescription() {
        return mWorkspaceDescription;
    }

    public void setmWorkspaceDescription(String mWorkspaceDescription) {
        this.mWorkspaceDescription = mWorkspaceDescription;
    }

    public String getmImageWorkspace() {
        return mImageWorkspace;
    }

    public void setmImageWorkspace(String mImageWorkspace) {
        this.mImageWorkspace = mImageWorkspace;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }
}

