package com.kun.allgo.Views.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
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
import com.kun.allgo.Models.WindowAccount;
import com.kun.allgo.R;
import com.kun.allgo.Utils.AuthenticationHelper;
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
    public List<WindowAccount> listWindowAccount = new ArrayList<>();
    public List<String> listWindowAccountId = new ArrayList<>();
    ProgressDialog progressDialog;

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
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");

        allAccountCode = getArguments().getInt("code", 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_windows_account, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Local Account");

        //getFormWidget();
        progressDialog.show();
        listWindowAccount.clear();
        listWindowAccountId.clear();
        if (allAccountCode == 0) {
            getWindowAccountIdData();
        } else {
            getAllAccountData();
        }

        return view;
    }

    private void getFormWidget() {
        recyclerViewLA = (RecyclerView) view.findViewById(R.id.rcvLocalAccount);
        recyclerViewLA.setHasFixedSize(true);
        recyclerViewLA.setLayoutManager(new LinearLayoutManager(getActivity()));
        localAccountAdapter = new LocalAccountAdapter(getContext(), listWindowAccount);

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
                                    listWindowAccount.remove(position);
                                    localAccountAdapter.notifyItemRemoved(position);
                                    deleteWindowAccount(listWindowAccountId.get(position));
                                }

                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    listWindowAccount.remove(position);
                                    localAccountAdapter.notifyItemRemoved(position);
                                    deleteWindowAccount(listWindowAccountId.get(position));
                                }
                            }
                        });

        recyclerViewLA.addOnItemTouchListener(swipeTouchListener);
    }

    private void getWindowAccountIdData() {

        Firebase roomRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + GlobalVariable.currentRoomId);
        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap:dataSnapshot.getChildren()) {
                    if (snap.getKey() == "windowAccounts") {
                        for (DataSnapshot rsnap : snap.getChildren()) {
                            //Log.d("manhduydl", wsnap.getKey());
                            String key = rsnap.getKey();
                            listWindowAccountId.add(key);
                        }
                    }
                }
                getWindowAccountData();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void getWindowAccountData() {

        for (String windowAccountId : listWindowAccountId) {
            Firebase windowAccountRef = new Firebase(Constant.FIREBASE_URL_WINDOWACCOUNTS + "/" + windowAccountId);
            windowAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userName = dataSnapshot.child("userName").getValue().toString();
                    String password = dataSnapshot.child("password").getValue().toString();
                    String IP = dataSnapshot.child("IP").getValue().toString();
                    String accountDescription = dataSnapshot.child("accountDescription").getValue().toString();

                    //decrypt
                    userName = AuthenticationHelper.DecryptData(userName);
                    password = AuthenticationHelper.DecryptData(password);
                    IP = AuthenticationHelper.DecryptData(IP);

                    WindowAccount windowAccount = new WindowAccount(dataSnapshot.getKey(), userName, password, accountDescription, IP);
                    listWindowAccount.add(windowAccount);
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
    }

    private void deleteWindowAccount(final String windowAccountId) {
        Firebase roomRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + GlobalVariable.currentRoomId);
        final Firebase windowAccountRef = new Firebase(Constant.FIREBASE_URL_WINDOWACCOUNTS + "/" + windowAccountId);

        roomRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                mutableData.child("windowAccounts/" + windowAccountId).setValue(null);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                windowAccountRef.runTransaction(new Transaction.Handler() {
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
        Firebase allAccountRef = new Firebase(Constant.FIREBASE_URL_WINDOWACCOUNTS);
        allAccountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    String userName = snapshot.child("userName").getValue().toString();
                    String password = snapshot.child("password").getValue().toString();
                    String IP = snapshot.child("IP").getValue().toString();
                    String accountDescription = snapshot.child("accountDescription").getValue().toString();

                    //decrypt
                    userName = AuthenticationHelper.DecryptData(userName);
                    password = AuthenticationHelper.DecryptData(password);
                    IP = AuthenticationHelper.DecryptData(IP);

                    WindowAccount windowAccount = new WindowAccount(snapshot.getKey(), userName, password, accountDescription, IP);
                    listWindowAccount.add(windowAccount);
                    getFormWidget();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
