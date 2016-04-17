package com.kun.allgo.Services;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kun.allgo.Models.AppUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 12120 on 4/17/2016.
 */
public class UsersService {

    private String mUrl = "https://allgo.firebaseio.com/";
    private Firebase ref = new Firebase(mUrl);

    public UsersService() {

    }

    public boolean StoreNewUser(AppUser appUser) {

        ref.authWithPassword("jenny@example.com", "password",
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authentication just completed successfully :)
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("provider", authData.getProvider());
                        if(authData.getProviderData().containsKey("displayName")) {
                            map.put("displayName", authData.getProviderData().get("displayName").toString());
                        }
                        ref.child("users").child(authData.getUid()).setValue(map);
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        // Something went wrong :(
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

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public Firebase getRef() {
        return ref;
    }

    public void setRef(Firebase ref) {
        this.ref = ref;
    }
}
