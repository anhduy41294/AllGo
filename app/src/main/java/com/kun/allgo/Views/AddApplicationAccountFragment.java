package com.kun.allgo.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.R;
import com.kun.allgo.Utils.AuthenticationHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddApplicationAccountFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    EditText edtAppUsername, edtAppPassword, edtAppConfirmPassword, edtAppEmail, edtAppDescription;
    Button btnAppSubmit;
    Spinner spinerAppType;
    String appType;
    private Firebase applicationAccountRef = new Firebase(Constant.FIREBASE_URL_APPLICATIONACCOUNTS);

    public AddApplicationAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_application_account, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Application Account");

        edtAppUsername = (EditText) view.findViewById(R.id.edtAppUsername);
        edtAppEmail = (EditText) view.findViewById(R.id.edtAppEmail);
        edtAppPassword = (EditText) view.findViewById(R.id.edtAppPassword);
        edtAppConfirmPassword = (EditText) view.findViewById(R.id.edtAppConfirmPassword);
        edtAppDescription = (EditText) view.findViewById(R.id.edtAppDescription);
        spinerAppType = (Spinner) view.findViewById(R.id.spinerAppType);
        btnAppSubmit = (Button) view.findViewById(R.id.btnAppSubmit);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Skype");
        categories.add("Yahoo");
        categories.add("Outlook");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinerAppType.setAdapter(dataAdapter);

        // Spinner click listener
        spinerAppType.setOnItemSelectedListener(this);

        btnAppSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAppAccount();
            }
        });

        return view;
    }

    private void saveAppAccount() {
        String userName = edtAppUsername.getText().toString();
        String email = edtAppEmail.getText().toString();
        String password = edtAppPassword.getText().toString();
        String description = edtAppDescription.getText().toString();

        //encrypt
        password = AuthenticationHelper.EncryptData(password);
        email = AuthenticationHelper.EncryptData(email);
        userName = AuthenticationHelper.EncryptData(userName);

        final Firebase newAppAccountRef = applicationAccountRef.push();

        Map<String, Object> newLocalAccount = new HashMap<>();
        newLocalAccount.put("userName", userName);
        newLocalAccount.put("email", email);
        newLocalAccount.put("password", password);
        newLocalAccount.put("accountDescription", description);
        newLocalAccount.put("appType", appType);
        newAppAccountRef.setValue(newLocalAccount);
        newAppAccountRef.child("rooms").child(GlobalVariable.currentRoomId).setValue(true, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                Firebase roomOfCurrentAccountRef = new Firebase(Constant.FIREBASE_URL_ROMS + "/" + GlobalVariable.currentRoomId).child("appAccounts");
                roomOfCurrentAccountRef.child(newAppAccountRef.getKey()).setValue(true, new Firebase.CompletionListener() {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        appType = item;
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
