package com.kun.allgo.Services;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Models.AppUser;

/**
 * Created by 12120 on 4/17/2016.
 */
public class UsersService {

    private Firebase userRef = new Firebase(Constant.FIREBASE_URL_USERS);

    public UsersService() {

    }

    public boolean StoreNewUser(final AppUser appUser) {

        final Firebase newUserRef = userRef.child(appUser.getmUserId());

        /**
         * See if there is already a user (for example, if they already logged in with an associated
         * Google account.
         */
        newUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /* If there is no user, make one */
                if (dataSnapshot.getValue() == null) {
                 /* Set raw version of date to the ServerValue.TIMESTAMP value and save into dateCreatedMap */
//                    HashMap<String, Object> timestampJoined = new HashMap<>();
//                    timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

                    //AppUser newUser = new AppUser(mUserName, mUserEmail);
                    //userLocation.setValue(newUser);
                    newUserRef.child("userName").setValue(appUser.getUserName());
                    newUserRef.child("userEmail").setValue(appUser.getmEmail());
                    newUserRef.child("workSpaces").setValue(null);
                    newUserRef.child("randomP").setValue(appUser.getRandomP());
                    newUserRef.child("randomK").setValue(appUser.getRandomK());
                    newUserRef.child("hashP").setValue(appUser.getHashP());
                    newUserRef.child("masterEncryptedKey").setValue(appUser.getMasterEncryptedKey());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("store new user", "Error occurred: " + firebaseError.getMessage());
            }
        });
        return true;
    }

    public boolean EditUser(AppUser appUser) {
        return false;
    }

    public boolean RemoveUser(AppUser appUser) {
        return false;
    }

    public boolean LoginUser(AppUser appUser) {
        return false;
    }

    public boolean LogOutUser(AppUser appUser) {
        return false;
    }

}
