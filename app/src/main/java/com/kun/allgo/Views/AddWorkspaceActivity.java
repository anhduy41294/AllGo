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
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.AppUser;
import com.kun.allgo.Models.Workspace;
import com.kun.allgo.R;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.kun.allgo.Services.WorkspaceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddWorkspaceActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    private EditText edtWordSpaceName;
    private EditText edtWorkspaceDescription;
    private EditText edtLongitude;
    private EditText edtLatitude;
    private Button btnFindPlace;
    private Button btnSubmit;
    private Button btnGetCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Firebase workspaceRef = new Firebase(Constant.FIREBASE_URL_WORKSPACES);
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    //private Workspace workspace = new Workspace();
    private WorkspaceService workspaceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workspace);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Create an instance of GoogleAPIClient.
        buildGoogleApiClient();
        //
        workspaceService = new WorkspaceService();

        getFormWidget();
        addEvent();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void getFormWidget() {
        edtWordSpaceName = (EditText) findViewById(R.id.edtWordSpaceName);
        edtWorkspaceDescription = (EditText) findViewById(R.id.edtWorkspaceDescription);
        edtLongitude = (EditText) findViewById(R.id.edtLongitude);
        edtLatitude = (EditText) findViewById(R.id.edtLatitde);
        btnFindPlace = (Button) findViewById(R.id.btnFindPlace);
        btnSubmit = (Button) findViewById(R.id.btnSubmitWorkspace);
        btnGetCurrentLocation = (Button) findViewById(R.id.btnGetCurrentLocation);

        edtLatitude.setText(GlobalVariable.latitude);
        edtLongitude.setText(GlobalVariable.longititude);
        GlobalVariable.latitude = "";
        GlobalVariable.longititude = "";
    }

    private void addEvent() {

        btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient != null) {
                    mGoogleApiClient.connect();
                }
            }
        });

        btnFindPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gọi Activity Map
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thực hiện save Workspace
                saveWorkspace();
            }
        });
    }

    private void saveWorkspace() {

        String workspaceName = edtWordSpaceName.getText().toString();
        String workspaceDescription = edtWorkspaceDescription.getText().toString();
        Double latitude = Double.valueOf(edtLatitude.getText().toString());
        Double longitude = Double.valueOf(edtLongitude.getText().toString());

        Workspace workspace = new Workspace("", workspaceName, workspaceDescription, "", latitude, longitude);
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
                if (firebaseError == null) {
                    Firebase workspaceOfCurrentUserRef = new Firebase(Constant.FIREBASE_URL_USERS + "/" + GlobalVariable.currentUserId).child("workSpaces");
                    workspaceOfCurrentUserRef.child(newWorkspaceRef.getKey()).setValue(true, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            updateCurrentAppUser();
                        }
                    });
                }
            }
        });
    }

    private void updateCurrentAppUser() {
        final String[] userEmail = new String[1];
        final String[] userName = new String[1];
        final ArrayList<String> listWorkSpace = new ArrayList<>();
        Firebase userRef = new Firebase(Constant.FIREBASE_URL_USERS + "/" + GlobalVariable.currentUserId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("manhduy", String.valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Log.d("manhduy", snap.getKey());
                    if (snap.getKey() == "userEmail") {
                        userEmail[0] = snap.getValue().toString();
                    } else {
                        if (snap.getKey() == "userName") {
                            userName[0] = snap.getValue().toString();
                        } else {
                            for (DataSnapshot wsnap : snap.getChildren()) {
                                Log.d("manhduydl", wsnap.getKey());
                                String key = wsnap.getKey();
                                listWorkSpace.add(key);
                            }
                        }
                    }
                }
                GlobalVariable.CurrentAppUser = new AppUser(GlobalVariable.currentUserId, userName[0], userEmail[0]);
                GlobalVariable.CurrentAppUser.setListWorkSpace(listWorkSpace);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (checkGPS()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                Toast.makeText(this.getApplicationContext(), "error permision", Toast.LENGTH_LONG).show();
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (mLastLocation != null) {

            edtLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
            edtLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case LOCATION_PERMISSION_REQUEST_CODE: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//                    if (mLastLocation != null) {
//
//                        edtLatitude.setText(String.valueOf(mLastLocation.getLatitude()));
//                        edtLongitude.setText(String.valueOf(mLastLocation.getLongitude()));
//                    }
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();
    }

    //Phan Get Location
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private boolean checkGPS()
    {
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
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
            FragmentManager fm = getSupportFragmentManager();
            MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance();
            alertDialog.show(fm, "fragment_alert");
            return false;
        }
        return true;
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
