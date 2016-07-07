package com.kun.allgo.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.ApplicationAccount;
import com.kun.allgo.Models.LocalAccount;
import com.kun.allgo.R;
import com.kun.allgo.Utils.AuthenticationHelper;
import com.kun.allgo.Views.Adapter.ApplicationAccountAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicationAccountFragment extends Fragment {

    private ApplicationAccountAdapter applicationAccountAdapter;
    private RecyclerView recyclerViewLA;
    private View view;
    private int allAccountCode;
    public List<ApplicationAccount> listApplicationAccount = new ArrayList<>();
    public List<String> listApplicationAccountId = new ArrayList<>();

    public ApplicationAccountFragment() {
        // Required empty public constructor
    }

    // newInstance constructor for creating fragment with arguments
    public static ApplicationAccountFragment newInstance(int page, String title, int code) {
        ApplicationAccountFragment applicationAccountFragment = new ApplicationAccountFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putInt("code", code);
        applicationAccountFragment.setArguments(args);
        return applicationAccountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
        allAccountCode = getArguments().getInt("code", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_application_account, container, false);

        //getFormWidget();
        listApplicationAccount.clear();
        listApplicationAccountId.clear();
        if (allAccountCode == 0) {
            getApplicationAccountIdData();
        } else {
            getAllAccountData();
        }

        return view;
    }

    private void getAllAccountData() {
        Firebase allAppliactionAccountRef = new Firebase(Constant.FIREBASE_URL_APPLICATIONACCOUNTS);
        allAppliactionAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    String userName = snapshot.child("userName").getValue().toString();
                    String password = snapshot.child("password").getValue().toString();
                    String accountDescription = snapshot.child("accountDescription").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String appType = snapshot.child("appType").getValue().toString();

                    //Workspace workspace = new Workspace(dataSnapshot.getKey(), workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
                    //listWorkspace.add(workspace);
                    //Room rom = new Room(dataSnapshot.getKey(), roomName, roomDescription, roomImage);
                    ApplicationAccount applicationAccount = new ApplicationAccount(snapshot.getKey(), userName, password, accountDescription, email, appType);
                    listApplicationAccount.add(applicationAccount);
                    getFormWidget();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getFormWidget() {
        recyclerViewLA = (RecyclerView) view.findViewById(R.id.rcvLocalAccount);
        recyclerViewLA.setHasFixedSize(true);
        recyclerViewLA.setLayoutManager(new LinearLayoutManager(getActivity()));
        applicationAccountAdapter = new ApplicationAccountAdapter(getContext(), listApplicationAccount);

        recyclerViewLA.setAdapter(applicationAccountAdapter);

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(recyclerViewLA,
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
                                    listApplicationAccount.remove(position);
                                    applicationAccountAdapter.notifyItemRemoved(position);
                                    deleteLocalAccount(listApplicationAccountId.get(position));
                                }
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    listApplicationAccount.remove(position);
                                    applicationAccountAdapter.notifyItemRemoved(position);
                                    deleteLocalAccount(listApplicationAccountId.get(position));
                                }
                            }
                        });

        recyclerViewLA.addOnItemTouchListener(swipeTouchListener);
    }

    private void deleteLocalAccount(final String applicationAccountId) {
        Firebase roomRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + GlobalVariable.currentRoomId);
        final Firebase appAccountRef = new Firebase(Constant.FIREBASE_URL_APPLICATIONACCOUNTS + "/" + applicationAccountId);

        roomRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.child("appAccounts/" + applicationAccountId).setValue(null);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                appAccountRef.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        mutableData.setValue(null);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                        applicationAccountAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void getApplicationAccountIdData() {
        Firebase roomRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + GlobalVariable.currentRoomId);
        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    if (snap.getKey() == "appAccounts") {
                        for (DataSnapshot rsnap : snap.getChildren()) {
                            //Log.d("manhduydl", wsnap.getKey());
                            String key = rsnap.getKey();
                            listApplicationAccountId.add(key);
                        }
                    }
                }
                getApplicationAccountData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getApplicationAccountData() {

        for (String appAccountId : listApplicationAccountId) {
            Firebase appliactionAccountRef = new Firebase(Constant.FIREBASE_URL_APPLICATIONACCOUNTS + "/" + appAccountId);
            appliactionAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userName = dataSnapshot.child("userName").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();
                    String accountDescription = dataSnapshot.child("accountDescription").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String appType = dataSnapshot.child("appType").getValue().toString();

                    //decrypt
                    userName = AuthenticationHelper.DecryptData(userName);
                    password = AuthenticationHelper.DecryptData(password);
                    email = AuthenticationHelper.DecryptData(email);

                    ApplicationAccount applicationAccount = new ApplicationAccount(dataSnapshot.getKey(), userName, password, accountDescription, email, appType);
                    listApplicationAccount.add(applicationAccount);
                    getFormWidget();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
}
