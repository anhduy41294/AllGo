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

public class AddRoomActivity extends AppCompatActivity {

    private EditText edtRoomName;
    private EditText edtRoomDescription;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFormWidget();
        addEvent();

    }

    private void getFormWidget() {
        edtRoomName = (EditText) findViewById(R.id.edtRoomName);
        edtRoomDescription = (EditText) findViewById(R.id.edtRoomDescription);
        btnSubmit = (Button) findViewById(R.id.btnSubmitRoom);
    }

    private void addEvent() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thực hiện save Workspace
                saveRoom();
            }
        });
    }

    private boolean saveRoom() {
        return true;
    }

}
