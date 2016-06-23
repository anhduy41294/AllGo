package com.kun.allgo.Views;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddLocalAccountFragment extends Fragment {

    EditText edtUserName, edtPassword, edtAccountDescription, edtIP, edtPCName;
    Button submitBtn, qrcodeBtn;
    private Firebase localAccountRef = new Firebase(Constant.FIREBASE_URL_LOCALACCOUNTS);

    public AddLocalAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_local_account, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Windows Account");

        edtUserName = (EditText) view.findViewById(R.id.edtLCName);
        edtPassword = (EditText) view.findViewById(R.id.edtLCPassword);
        edtAccountDescription = (EditText) view.findViewById(R.id.edtLCDescription);
        edtIP = (EditText) view.findViewById(R.id.edtLCIP);
        edtPCName = (EditText) view.findViewById(R.id.edtLCPCName);
        submitBtn = (Button) view.findViewById(R.id.btnSubmitLC);
        qrcodeBtn = (Button) view.findViewById(R.id.btnLCScanQRcode);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocalAccount();
            }
        });

        qrcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ScanQRCodeActivity.class);
                startActivityForResult(i, 1);
                //startActivity(i);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                edtIP.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void saveLocalAccount() {
        String userName = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();
        String description = edtAccountDescription.getText().toString();
        String IP = edtIP.getText().toString();
        String PCName = edtPCName.getText().toString();

        final Firebase newLocalAccountRef = localAccountRef.push();

        Map<String, Object> newLocalAccount = new HashMap<>();
        newLocalAccount.put("userName", userName);
        newLocalAccount.put("password", password);
        newLocalAccount.put("accountDescription", description);
        newLocalAccount.put("IP", IP);
        newLocalAccount.put("PCName", PCName);
        newLocalAccountRef.setValue(newLocalAccount);
        newLocalAccountRef.child("rooms").child(GlobalVariable.currentRoomId).setValue(true, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Firebase roomOfCurrentAccountRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + GlobalVariable.currentRoomId).child("localAccounts");
                roomOfCurrentAccountRef.child(newLocalAccountRef.getKey()).setValue(true, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                        LocalAccountFragment localAccountFragment = new LocalAccountFragment();
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.fragment_container, localAccountFragment)
//                                .addToBackStack(null)
//                                .commit();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });
    }

}
