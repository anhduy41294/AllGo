package com.kun.allgo.Views;


import android.os.Bundle;
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
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.LocalAccount;
import com.kun.allgo.R;
import com.kun.allgo.Views.Adapter.LocalAccountAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WindowsAccountFragment extends Fragment {

    private LocalAccountAdapter localAccountAdapter;
    private RecyclerView recyclerViewLA;
    private View view;
    private int allAccountCode;
    public List<LocalAccount> listLocalAccount = new ArrayList<>();
    public List<String> listLocalAccountId = new ArrayList<>();

    private String title;
    private int page;

    public WindowsAccountFragment() {
        // Required empty public constructor
    }

    // newInstance constructor for creating fragment with arguments
    public static WindowsAccountFragment newInstance(int page, String title, int code) {
        WindowsAccountFragment windowsAccountFragment = new WindowsAccountFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putInt("code", code);
        windowsAccountFragment.setArguments(args);
        return windowsAccountFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
        allAccountCode = getArguments().getInt("code", 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_windows_account, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Local Account");

        //getFormWidget();
        listLocalAccount.clear();
        listLocalAccountId.clear();
        if (allAccountCode == 0) {
            getLocalAccountIdData();
        } else {
            getAllAccountData();
        }

        return view;
    }

    private void getFormWidget() {
        recyclerViewLA = (RecyclerView) view.findViewById(R.id.rcvLocalAccount);
        recyclerViewLA.setHasFixedSize(true);
        recyclerViewLA.setLayoutManager(new LinearLayoutManager(getActivity()));
        localAccountAdapter = new LocalAccountAdapter(getContext(), listLocalAccount);

        recyclerViewLA.setAdapter(localAccountAdapter);

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
                                    listLocalAccount.remove(position);
                                    localAccountAdapter.notifyItemRemoved(position);
                                    deleteLocalAccount(listLocalAccountId.get(position));
                                }

                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    listLocalAccount.remove(position);
                                    localAccountAdapter.notifyItemRemoved(position);
                                }
                                localAccountAdapter.notifyDataSetChanged();
                            }
                        });

        recyclerViewLA.addOnItemTouchListener(swipeTouchListener);
    }

    private void getLocalAccountIdData() {

        Firebase roomRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + GlobalVariable.currentRoomId);
        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    if (snap.getKey() == "localAccounts") {
                        for (DataSnapshot rsnap : snap.getChildren()) {
                            //Log.d("manhduydl", wsnap.getKey());
                            String key = rsnap.getKey();
                            listLocalAccountId.add(key);
                        }
                    }
                }
                getLocalAccountData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getLocalAccountData() {

        for (String localAccountId : listLocalAccountId) {
            Firebase localAccountRef = new Firebase(Constant.FIREBASE_URL_LOCALACCOUNTS + "/" + localAccountId);
            localAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userName = dataSnapshot.child("userName").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();
                    String accountDescription = dataSnapshot.child("accountDescription").getValue().toString();

                    //Workspace workspace = new Workspace(dataSnapshot.getKey(), workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
                    //listWorkspace.add(workspace);
                    //Room rom = new Room(dataSnapshot.getKey(), roomName, roomDescription, roomImage);
                    LocalAccount localAccount = new LocalAccount(dataSnapshot.getKey(), userName, password, accountDescription);
                    listLocalAccount.add(localAccount);
                    getFormWidget();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }

    private void deleteLocalAccount(final String localAccountId) {
        Firebase roomRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + GlobalVariable.currentRoomId);
        final Firebase localAccountRef = new Firebase(Constant.FIREBASE_URL_LOCALACCOUNTS + "/" + localAccountId);

        roomRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.child("localAccounts/" + localAccountId).setValue(null);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                localAccountRef.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        mutableData.setValue(null);
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                        localAccountAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void getAllAccountData() {
        Firebase allAccountRef = new Firebase(Constant.FIREBASE_URL_LOCALACCOUNTS);
        allAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    String userName = snapshot.child("userName").getValue().toString();
                    String password = snapshot.child("password").getValue().toString();
                    String accountDescription = snapshot.child("accountDescription").getValue().toString();

                    //Workspace workspace = new Workspace(dataSnapshot.getKey(), workspaceName, workspaceDescription, workspaceImage, latitude, longitude);
                    //listWorkspace.add(workspace);
                    //Room rom = new Room(dataSnapshot.getKey(), roomName, roomDescription, roomImage);
                    LocalAccount localAccount = new LocalAccount(snapshot.getKey(), userName, password, accountDescription);
                    listLocalAccount.add(localAccount);
                    getFormWidget();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
