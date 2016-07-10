package com.kun.allgo.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.orangegangsters.lollipin.lib.managers.AppLock;
import com.github.orangegangsters.lollipin.lib.managers.AppLockActivity;
import com.kun.allgo.FaceIdentification.ImageHelper;
import com.kun.allgo.FaceIdentification.SelectImageActivity;
import com.kun.allgo.FaceIdentification.StorageHelper;
import com.kun.allgo.Login.LoginAcitivity;
import com.kun.allgo.R;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.contract.TrainingStatus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import uk.me.lewisdeane.ldialogs.BaseDialog;
import uk.me.lewisdeane.ldialogs.CustomDialog;

public class CustomAuthActivity extends AppLockActivity {

    private static final int REQUEST_CODE_ENABLE = 11;
    private static final int REQUEST_SELECT_IMAGE = 0;

    // The image selected to detect.
    private Bitmap mBitmap;
    // Progress dialog popped up when communicating with server.
    ProgressDialog progressDialog;

    boolean detected;

    //FaceListAdapter mFaceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(getContentView());

        detected = false;

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
    }

    @Override
    public void showForgotDialog() {
        Resources res = getResources();
        // Create the builder with required paramaters - Context, Title, Positive Text
        CustomDialog.Builder builder = new CustomDialog.Builder(this,
                "Make choices",
                "New Pin");
        builder.content("Create new Pin or use Face identification?");
        builder.negativeText("Face");

        //Set theme
        builder.darkTheme(false);
        builder.typeface(Typeface.SANS_SERIF);
        builder.positiveColor(res.getColor(R.color.light_blue_500)); // int res, or int colorRes parameter versions available as well.
        builder.negativeColor(res.getColor(R.color.light_blue_500));
        builder.rightToLeft(false); // Enables right to left positioning for languages that may require so.
        builder.titleAlignment(BaseDialog.Alignment.CENTER);
        builder.buttonAlignment(BaseDialog.Alignment.CENTER);
        builder.setButtonStacking(false);

        //Set text sizes
        builder.titleTextSize((int) res.getDimension(R.dimen.activity_dialog_title_size));
        builder.contentTextSize((int) res.getDimension(R.dimen.activity_dialog_content_size));
        builder.positiveButtonTextSize((int) res.getDimension(R.dimen.activity_dialog_positive_button_size));
        builder.negativeButtonTextSize((int) res.getDimension(R.dimen.activity_dialog_negative_button_size));

        //Build the dialog.
        CustomDialog customDialog = builder.build();
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.setClickListener(new CustomDialog.ClickListener() {
            @Override
            public void onConfirmClick() {
                //Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CustomAuthActivity.this, CustomAuthActivity.class);
                intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
                startActivityForResult(intent, REQUEST_CODE_ENABLE);
            }

            @Override
            public void onCancelClick() {
                //Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CustomAuthActivity.this, SelectImageActivity.class);
                startActivityForResult(intent, REQUEST_SELECT_IMAGE);
            }
        });

        // Show the dialog.
        customDialog.show();
    }

    @Override
    public void onPinFailure(int attempts) {

    }

    @Override
    public void onPinSuccess(int attempts) {

    }

    @Override
    public int getPinLength() {
        return super.getPinLength();//you can override this method to change the pin length from the default 4
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_ENABLE:
                Toast.makeText(this, "PinCode enabled", Toast.LENGTH_SHORT).show();
                break;
            case REQUEST_SELECT_IMAGE:
                if(resultCode == RESULT_OK) {
                    //detected = false;

                    // If image is selected successfully, set the image URI and bitmap.
                    Uri imageUri = data.getData();
                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                            imageUri, getContentResolver());
                    if (mBitmap != null) {
                        // Show the image on screen.
//                        ImageView imageView = (ImageView) findViewById(R.id.image);
//                        imageView.setImageBitmap(mBitmap);
                    }

                    // Clear the identification result.
//                    FaceListAdapter faceListAdapter = new FaceListAdapter(null);
//                    ListView listView = (ListView) findViewById(R.id.list_identified_faces);
//                    listView.setAdapter(faceListAdapter);

                    // Clear the information panel.
                    //setInfo("");

                    // Start detecting in image.
                    detect(mBitmap);
                }
                break;
        }
    }

    // Start detecting in image.
    private void detect(Bitmap bitmap) {
        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        //setAllButtonsEnabledStatus(false);

        // Start a background task to detect faces in the image.
        new DetectionTask().execute(inputStream);
    }

    // Background task of face detection.
    private class DetectionTask extends AsyncTask<InputStream, String, Face[]> {
        @Override
        protected Face[] doInBackground(InputStream... params) {
            // Get an instance of face service client to detect faces in image.
            FaceServiceClient faceServiceClient = CustomApplication.getFaceServiceClient();
            try{
                publishProgress("Detecting...");

                // Start detection.
                return faceServiceClient.detect(
                        params[0],  /* Input stream of image to detect */
                        true,       /* Whether to return face ID */
                        false,       /* Whether to return face landmarks */
                        /* Which face attributes to analyze, currently we support:
                           age,gender,headPose,smile,facialHair */
                        null);
            }  catch (Exception e) {
                publishProgress(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            setUiBeforeBackgroundTask();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // Show the status of background detection task on screen.
            setUiDuringBackgroundTask(values[0]);
        }

        @Override
        protected void onPostExecute(Face[] result) {
            progressDialog.dismiss();

            //setAllButtonsEnabledStatus(true);

            if (result != null) {
                // Set the adapter of the ListView which contains the details of detected faces.
//                mFaceListAdapter = new FaceListAdapter(result);
//                ListView listView = (ListView) findViewById(R.id.list_identified_faces);
//                listView.setAdapter(mFaceListAdapter);

                if (result.length == 0) {
                    detected = false;
                    //setInfo("No faces detected!");
                    Toast.makeText(CustomAuthActivity.this, "No face detected", Toast.LENGTH_LONG).show();
                } else {
                    detected = true;
                    //setInfo("Click on the \"Identify\" button to identify the faces in image.");
                    Set<String> personGroupIds = StorageHelper.getAllPersonGroupIds(getBaseContext());
                    for (String personGroupId : personGroupIds) {
                        // Start a background task to identify faces in the image.
                        List<UUID> faceIds = new ArrayList<>();
                        for (Face face: Arrays.asList(result)) {
                            faceIds.add(face.faceId);
                        }

                        //setAllButtonsEnabledStatus(false);

                        new IdentificationTask(personGroupId).execute(
                                faceIds.toArray(new UUID[faceIds.size()]));
                    }
                }
            } else {
                detected = false;
            }

            //refreshIdentifyButtonEnabledStatus();
        }
    }

    // Background task of face identification.
    private class IdentificationTask extends AsyncTask<UUID, String, IdentifyResult[]> {
        private boolean mSucceed = true;
        String mPersonGroupId;
        IdentificationTask(String personGroupId) {
            this.mPersonGroupId = personGroupId;
        }

        @Override
        protected IdentifyResult[] doInBackground(UUID... params) {
            String logString = "Request: Identifying faces ";
            for (UUID faceId: params) {
                logString += faceId.toString() + ", ";
            }
            logString += " in group " + mPersonGroupId;
            //addLog(logString);

            // Get an instance of face service client to detect faces in image.
            FaceServiceClient faceServiceClient = CustomApplication.getFaceServiceClient();
            try{
                publishProgress("Getting person group status...");

                TrainingStatus trainingStatus = faceServiceClient.getPersonGroupTrainingStatus(
                        this.mPersonGroupId);     /* personGroupId */

                if (trainingStatus.status != TrainingStatus.Status.Succeeded) {
                    publishProgress("Person group training status is " + trainingStatus.status);
                    mSucceed = false;
                    return null;
                }

                publishProgress("Identifying...");

                // Start identification.
                return faceServiceClient.identity(
                        this.mPersonGroupId,   /* personGroupId */
                        params,                  /* faceIds */
                        1);                      /* maxNumOfCandidatesReturned */
            }  catch (Exception e) {
                mSucceed = false;
                publishProgress(e.getMessage());
                //addLog(e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            setUiBeforeBackgroundTask();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            // Show the status of background detection task on screen.a
            setUiDuringBackgroundTask(values[0]);
        }

        @Override
        protected void onPostExecute(IdentifyResult[] result) {
            // Show the result on screen when detection is done.
            //setUiAfterIdentification(result, mSucceed);
            progressDialog.dismiss();
            for (IdentifyResult identifyResult: result) {
                //int c = identifyResult.candidates.size();
                if (identifyResult.candidates.size() > 0){
                    CustomAuthActivity.super.onAuthenticated();
                    return;
                } else {
                    Toast.makeText(getBaseContext(), "Face is not identified", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void setUiBeforeBackgroundTask() {
        progressDialog.show();
    }

    // Show the status of background detection task on screen.
    private void setUiDuringBackgroundTask(String progress) {
        progressDialog.setMessage(progress);

        //setInfo(progress);
    }

//    @Override
//    public int getContentView() {
//        return R.layout.activity_pin;
//    }

    @Override
    public String getForgotText() {
        return "Options";
    }
}
