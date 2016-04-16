package com.kun.allgo.Views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kun.allgo.R;

public class AddLocalAccountActivity extends AppCompatActivity {

    private EditText edtLCName;
    private EditText edtLCPassword;
    private EditText edtLCConfirmPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_local_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFormWidget();
        addEvent();
    }

    private void getFormWidget() {
        edtLCName = (EditText) findViewById(R.id.edtLCName);
        edtLCPassword = (EditText) findViewById(R.id.edtLCPassword);
        edtLCConfirmPassword = (EditText) findViewById(R.id.edtLCConfirmPassword);
        btnSubmit = (Button) findViewById(R.id.btnSubmitLC);
    }

    private void addEvent() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thực hiện save Workspace
                saveLocalAccount();
            }
        });
    }

    private boolean saveLocalAccount() {
        return true;
    }

}
