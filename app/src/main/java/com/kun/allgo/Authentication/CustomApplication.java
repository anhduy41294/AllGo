package com.kun.allgo.Authentication;

import android.app.Application;

import com.github.orangegangsters.lollipin.lib.managers.LockManager;
import com.kun.allgo.R;

/**
 * Created by Duy on 29-Jun-16.
 */
public class CustomApplication extends Application {

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate() {
        super.onCreate();

        LockManager<CustomAuthActivity> lockManager = LockManager.getInstance();
        lockManager.enableAppLock(this, CustomAuthActivity.class);
        lockManager.getAppLock().setLogoId(R.drawable.security_lock);
    }
}
