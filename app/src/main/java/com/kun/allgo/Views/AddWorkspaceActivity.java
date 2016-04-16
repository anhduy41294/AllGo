package com.kun.allgo.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kun.allgo.Models.Workspace;
import com.kun.allgo.R;

public class AddWorkspaceActivity extends AppCompatActivity {

    private EditText edtWordSpaceName;
    private EditText edtWorkspaceDescription;
    private EditText edtLongitude;
    private EditText edtLatitde;
    private Button btnFindPlace;
    private Button btnSubmit;
    private Button btnGetCurrentLocation;

    private Workspace workspace = new Workspace();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workspace);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFormWidget();
        //addEvent();
    }

    private void getFormWidget() {
        edtWordSpaceName = (EditText) findViewById(R.id.edtWordSpaceName);
        edtWorkspaceDescription = (EditText) findViewById(R.id.edtWorkspaceDescription);
        edtLongitude = (EditText) findViewById(R.id.edtLongitude);
        edtLatitde = (EditText) findViewById(R.id.edtLatitde);
        btnFindPlace = (Button) findViewById(R.id.btnFindPlace);
        btnSubmit = (Button) findViewById(R.id.btnSubmitWorkspace);
        btnGetCurrentLocation = (Button) findViewById(R.id.btnGetCurrentLocation);
    }

    private void addEvent() {
        btnFindPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gọi Activity Map
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thực hiện save Workspace
                //saveWorkspace();
            }
        });
    }

    private boolean saveWorkspace() {
        return true;
    }

}
