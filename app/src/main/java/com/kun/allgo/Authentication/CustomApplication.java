package com.kun.allgo.Authentication;

import android.app.Application;

import com.github.orangegangsters.lollipin.lib.managers.LockManager;
import com.kun.allgo.R;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;

/**
 * Created by Duy on 29-Jun-16.
 */
public class CustomApplication extends Application {

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate() {
        super.onCreate();

        sFaceServiceClient = new FaceServiceRestClient(getString(R.string.subscription_key));

        LockManager<CustomAuthActivity> lockManager = LockManager.getInstance();
        lockManager.enableAppLock(this, CustomAuthActivity.class);
        lockManager.getAppLock().setLogoId(R.mipmap.icon);
    }

    public static FaceServiceClient getFaceServiceClient() {
        return sFaceServiceClient;
    }

    private static FaceServiceClient sFaceServiceClient;
}
