package com.kun.allgo.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Models.AppUser;
import com.kun.allgo.R;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String LOG_TAG = CreateAccountActivity.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;
    private Firebase mFirebaseRef;
    private EditText mEditTextUsernameCreate, mEditTextEmailCreate, mEditTextPasswordCreate;
    private String mUserName, mUserEmail, mPassword;
    //Thêm
    private Button mButtonCreateAccount;
    private TextView mTextViewSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirebaseRef = new Firebase(Constant.FIREBASE_URL);
        initializeScreen();
        addEvent();
    }

    private void addEvent() {

        mTextViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginAcitivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        mButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    public void initializeScreen() {
        mEditTextUsernameCreate = (EditText) findViewById(R.id.edit_text_username_create);
        mEditTextEmailCreate = (EditText) findViewById(R.id.edit_text_email_create);
        mEditTextPasswordCreate = (EditText) findViewById(R.id.edit_text_password_create);
        mButtonCreateAccount = (Button) findViewById(R.id.btn_create_account_final);
        mTextViewSignIn = (TextView) findViewById(R.id.tv_sign_in);

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_creating_user_with_firebase));
        mAuthProgressDialog.setCancelable(false);
    }

    public void createAccount() {
        mUserName = mEditTextUsernameCreate.getText().toString();
        mUserEmail = mEditTextEmailCreate.getText().toString().toLowerCase();
        mPassword = mEditTextPasswordCreate.getText().toString();

        /**
         * Check that email and user name are okay
         */
        boolean validEmail = isEmailValid(mUserEmail);
        boolean validUserName = isUserNameValid(mUserName);
        boolean validPassword = isPasswordValid(mPassword);
        if (!validEmail || !validUserName || !validPassword) return;

        /**
         * If everything was valid show the progress dialog to indicate that
         * account creation has started
         */
        mAuthProgressDialog.show();

        /**
         * Create new user with specified email and password
         */
        mFirebaseRef.createUser(mUserEmail, mPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                /* Dismiss the progress dialog */
                mAuthProgressDialog.dismiss();
                Log.i(LOG_TAG, getString(R.string.log_message_auth_successful));
                String uid = (String) result.get("uid");
                createUserInFirebaseHelper(uid);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                /* Error occurred, log the error and dismiss the progress dialog */
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) +
                        firebaseError);
                mAuthProgressDialog.dismiss();
                /* Display the appropriate error message */
                if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN) {
                    mEditTextEmailCreate.setError(getString(R.string.error_email_taken));
                } else {
                    Toast.makeText(CreateAccountActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    /**
     * Creates a new user in Firebase from the Java POJO
     */
    private void createUserInFirebaseHelper(String uid) {

        final Firebase userLocation = new Firebase(Constant.FIREBASE_URL_USERS).child(uid);
        /**
         * See if there is already a user (for example, if they already logged in with an associated
         * Google account.
         */
        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* If there is no user, make one */
                if (dataSnapshot.getValue() == null) {
                 /* Set raw version of date to the ServerValue.TIMESTAMP value and save into dateCreatedMap */
//                    HashMap<String, Object> timestampJoined = new HashMap<>();
//                    timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    AppUser newUser = new AppUser(mUserName, mUserEmail);
                    userLocation.setValue(newUser);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
            }
        });

    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEditTextEmailCreate.setError(String.format(getString(R.string.error_invalid_email_not_valid),
                    email));
            return false;
        }
        return isGoodEmail;
    }

    private boolean isUserNameValid(String userName) {
        if (userName.equals("")) {
            mEditTextUsernameCreate.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            mEditTextPasswordCreate.setError(getResources().getString(R.string.error_invalid_password_not_valid));
            return false;
        }
        return true;
    }
}
