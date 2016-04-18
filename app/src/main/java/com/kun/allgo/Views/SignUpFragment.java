package com.kun.allgo.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kun.allgo.Models.AppUser;
import com.kun.allgo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private EditText edtUserName;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtFullName;
    private Button btnSubmit;
    private View view;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        getFormWidget();
        addEvent();

        return view;

    }

    private void getFormWidget() {
        edtUserName = (EditText) view.findViewById(R.id.edtUserName);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
        edtFullName = (EditText) view.findViewById(R.id.edtFullName);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
    }

    private void addEvent() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thực hiện kiểm tra và lưu tài khoản mới
                AppUser appUser = new AppUser();

                //Thực hiểm kiểm tra password

                // Thực hiện get Id User
                appUser.setIdUser(autoCreateId());

                appUser.setUserName(edtUserName.getContext().toString());
                //appUser.setPassword(edtPassword.getContext().toString());
                appUser.setFullName(edtFullName.getContext().toString());

                saveAccount(appUser);
            }
        });
    }

    private int autoCreateId() {
        return 0;
    }

    private boolean checkPassword() {
        if(edtPassword.getText().toString() == edtConfirmPassword.getText().toString())
        {
            return true;
        }

        return false;
    }

    private boolean saveAccount(AppUser appUser) {
        return true;

    }
}
