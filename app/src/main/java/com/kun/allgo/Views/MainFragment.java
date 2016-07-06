package com.kun.allgo.Views;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
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
public class MainFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener {

    private WorkspaceAdapter workspaceAdapter;
    private RecyclerView recyclerViewWorkspace;
    private View view;
    private WorkspaceService workspaceService;
    public List<Workspace> listWorkspace = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recommend");
        listWorkspace.clear();

        // Create an instance of GoogleAPIClient.
        buildGoogleApiClient();

//        Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES);
//        workspaceRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot workspaceSnap: dataSnapshot.getChildren()) {
//                    if (workspaceSnap.child("users/" + GlobalVariable.currentUserId).getValue() != null){
//                        String workspaceName = workspaceSnap.child("workspaceName").getValue().toString();
//                        String workspaceDescription = workspaceSnap.child("workspaceDescription").getValue().toString();
//                        String workspaceImage = workspaceSnap.child("workspaceImage").getValue().toString();
//                        Double latitude = Double.valueOf(workspaceSnap.child("latitude").getValue().toString());
//                        Double longitude = Double.valueOf(workspaceSnap.child("longitude").getValue().toString());
//
//                        Workspace workspace = new Workspace(workspaceSnap.getKey(), workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
//                        listWorkspace.add(workspace);
//                    }
//                }
//                getFormWidget();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

        mGoogleApiClient.connect();
        //getWorkSpaceData();
        //getFormWidget();
        // Inflate the layout for this fragment
        return view;
    }

    private void getWorkSpaceData() {
        if (GlobalVariable.CurrentAppUser.getListWorkSpace() != null) {
            for (String workSpaceId : GlobalVariable.CurrentAppUser.getListWorkSpace()) {
                Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES + "/" + workSpaceId);
                workspaceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String workspaceName = dataSnapshot.child("workspaceName").getValue().toString();
                        String workspaceDescription = dataSnapshot.child("workspaceDescription").getValue().toString();
                        String workspaceImage = dataSnapshot.child("workspaceImage").getValue().toString();
                        double latitude = Double.valueOf(dataSnapshot.child("latitude").getValue().toString());
                        double longitude = Double.valueOf(dataSnapshot.child("longitude").getValue().toString());

                        Location workspaceLocation = new Location("workspace");
                        workspaceLocation.setLatitude(latitude);
                        workspaceLocation.setLongitude(longitude);

                        Location currentLocation = new Location("current");
                        currentLocation.setLatitude(mLastLocation.getLatitude());
                        currentLocation.setLongitude(mLastLocation.getLongitude());

                        float distance = workspaceLocation.distanceTo(currentLocation);
                        if (distance < 500.0f) {
                            Workspace workspace = new Workspace(dataSnapshot.getKey(), workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
                            listWorkspace.add(workspace);
                            getFormWidget();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        } else {
            getFormWidget();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (checkGPS()) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // Should we show an explanation?
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//
//                    // Show an expanation to the user *asynchronously* -- don't block
//                    // this thread waiting for the user's response! After the user
//                    // sees the explanation, try again to request the permission.
//
//                } else {
//
//                    // No explanation needed, we can request the permission.
//
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//
//                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                    // app-defined int constant. The callback method gets the
//                    // result of the request.
//                }
                Toast.makeText(getContext(), "error permision", Toast.LENGTH_LONG).show();
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (mLastLocation != null) {
            getWorkSpaceData();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "Failed to connect...", Toast.LENGTH_SHORT).show();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean checkGPS()
    {
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            FragmentManager fm = getFragmentManager();
            MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance();
            alertDialog.show(fm, "fragment_alert");
            return false;
        }
        return true;
    }

    private void getFormWidget() {
        recyclerViewWorkspace = (RecyclerView) view.findViewById(R.id.rcvWorkspace);
        recyclerViewWorkspace.setHasFixedSize(true);
        recyclerViewWorkspace.setLayoutManager(new LinearLayoutManager(getActivity()));
        workspaceAdapter = new WorkspaceAdapter(getContext(), listWorkspace, getActivity().getSupportFragmentManager());

        recyclerViewWorkspace.setAdapter(workspaceAdapter);


    }

    public static class MyAlertDialogFragment extends DialogFragment {
        public MyAlertDialogFragment() {
            // Empty constructor required for DialogFragment
        }

        public static MyAlertDialogFragment newInstance() {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
//            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setMessage("Để xác định vị trí hiện tại, bạn phải mở dịch vụ GPS của thiết bị.");
            dialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });

            return dialog.create();
        }
    }

}
