package com.kun.allgo.Models;

import java.util.ArrayList;

/**
 * Work Space
 */
public class Workspace {
    private int mIdWorkspace;
    private String mWorkspaceName;
    private String mWorkspaceDescription;
    private int mImageWorkspace;
    private double mLongitude;
    private double mLatitude;
    private ArrayList<Room> mListRoom;

    public Workspace(int mIdWorkspace, String mWorkspaceName, String mWorkspaceDescription, int mImageWorkspace, double mLongitude, double mLatitude, ArrayList<Room> mListRoom) {
        this.mIdWorkspace = mIdWorkspace;
        this.mWorkspaceName = mWorkspaceName;
        this.mWorkspaceDescription = mWorkspaceDescription;
        this.mImageWorkspace = mImageWorkspace;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
        this.mListRoom = mListRoom;
    }

    public Workspace() {
    }

    public int getIdWorkspace() {
        return mIdWorkspace;
    }

    public void setIdWorkspace(int mIdWorkspace) {
        this.mIdWorkspace = mIdWorkspace;
    }

    public String getWorkspaceName() {
        return mWorkspaceName;
    }

    public void setWorkspaceName(String mWorkspaceName) {
        this.mWorkspaceName = mWorkspaceName;
    }

    public String getWorkspaceDescription() {
        return mWorkspaceDescription;
    }

    public void setWorkspaceDescription(String mWorkspaceDescription) {
        this.mWorkspaceDescription = mWorkspaceDescription;
    }

    public int getImageWorkspace() {
        return mImageWorkspace;
    }

    public void setImageWorkspace(int mImageWorkspace) {
        this.mImageWorkspace = mImageWorkspace;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public ArrayList<Room> getListRoom() {
        return mListRoom;
    }

    public void setListRoom(ArrayList<Room> mListRoom) {
        this.mListRoom = mListRoom;
    }
}

