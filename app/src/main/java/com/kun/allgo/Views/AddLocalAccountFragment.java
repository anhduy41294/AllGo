package com.kun.allgo.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    EditText edtUserName, edtPassword, edtAccountDescription;
    Button submitBtn;
    private Firebase localAccountRef = new Firebase(Constant.FIREBASE_URL_LOCALACCOUNTS);

    public AddLocalAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_local_account, container, false);

        edtUserName = (EditText) view.findViewById(R.id.edtLCName);
        edtPassword = (EditText) view.findViewById(R.id.edtLCPassword);
        edtAccountDescription = (EditText) view.findViewById(R.id.edtLCDescription);
        submitBtn = (Button) view.findViewById(R.id.btnSubmitLC);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocalAccount();
            }
        });

        return view;
    }

    private void saveLocalAccount() {
        String userName = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();
        String description = edtAccountDescription.getText().toString();

        final Firebase newLocalAccountRef = localAccountRef.push();

        Map<String, Object> newLocalAccount = new HashMap<>();
        newLocalAccount.put("userName", userName);
        newLocalAccount.put("password", password);
        newLocalAccount.put("accountDescription", description);
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
