package com.kun.allgo.Views.Adapter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.ApplicationAccount;
import com.kun.allgo.R;
import com.kun.allgo.SocketClient.SocketClientAutoLoginController;
import com.kun.allgo.Utils.EncyptionHelper;

import java.util.Collections;
import java.util.List;

import uk.me.lewisdeane.ldialogs.BaseDialog;
import uk.me.lewisdeane.ldialogs.CustomDialog;

/**
 * Created by Duy on 30-Jun-16.
 */
public class ApplicationAccountAdapter extends RecyclerView.Adapter<ApplicationAccountAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<ApplicationAccount> data = Collections.emptyList();
    private Context context;
    String appType = "";
    String encryptedUser ="";
    String encryptedPass ="";
    String encryptedEmail ="";
    private static final int WINFORM_CODE = 1;
    private static final int WEBFORM_CODE = 2;

    public ApplicationAccountAdapter(Context context, List<ApplicationAccount> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_item_local_account, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ApplicationAccount current = data.get(position);
        holder.txtLocalAccountName.setText(current.getmAppUsername());
        holder.txtLocalAccountDescription.setText(current.getmAppDescription());
        appType = current.getmAppType();
        if (appType.equals("Skype")) {
                holder.imageViewAccount.setImageDrawable(context.getDrawable(R.drawable.skype_500));
        } else {
            if (appType.equals("Yahoo")) {
                holder.imageViewAccount.setImageDrawable(context.getDrawable(R.drawable.yahoo_500));
            } else {
                if (appType.equals("Outlook")) {
                    holder.imageViewAccount.setImageDrawable(context.getDrawable(R.drawable.outlook_500));
                }
            }
        }
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVariable.IPCurrentPC.equals("")) {
                    showDialogNotConnect();
                } else {
                    //Encryption
                    encryptedUser = EncyptionHelper.EncryptDataAutoLogin(current.getmAppUsername());
                    encryptedPass = EncyptionHelper.EncryptDataAutoLogin(current.getmAppPassword());
                    encryptedEmail = EncyptionHelper.EncryptDataAutoLogin(current.getmAppEmail());
                    appType = current.getmAppType();

                    showDialog();
                }
            }
        });
    }

    private void showDialogNotConnect() {
        Resources res = context.getResources();
        // Create the builder with required paramaters - Context, Title, Positive Text
        CustomDialog.Builder builder = new CustomDialog.Builder(context,
                "Warning!!!",
                "Ok");
        builder.content("You need connect a PC to login application");
        //builder.negativeText("Cancel");

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
            }

            @Override
            public void onCancelClick() {
            }
        });

        // Show the dialog.
        customDialog.show();
    }

    private void showDialog() {
        Resources res = context.getResources();
        // Create the builder with required paramaters - Context, Title, Positive Text
        CustomDialog.Builder builder = new CustomDialog.Builder(context,
                "Chose type of app",
                "Webform");
        builder.content("You want to use "+appType+" in Winform or Webform?");
        builder.negativeText("Winform");

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
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Scanning...");
                progressDialog.show();
                SocketClientAutoLoginController socketClientAutoLoginController =
                        new SocketClientAutoLoginController(GlobalVariable.IPCurrentPC,
                                GlobalVariable.PortCurrentPC, encryptedUser, encryptedPass, appType, WEBFORM_CODE, encryptedEmail, context, progressDialog);
                socketClientAutoLoginController.execute();
            }

            @Override
            public void onCancelClick() {
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Scanning...");
                progressDialog.show();
                SocketClientAutoLoginController socketClientAutoLoginController =
                        new SocketClientAutoLoginController(GlobalVariable.IPCurrentPC,
                                GlobalVariable.PortCurrentPC, encryptedUser, encryptedPass, appType, WINFORM_CODE, encryptedEmail, context, progressDialog);
                socketClientAutoLoginController.execute();
            }
        });

        // Show the dialog.
        customDialog.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtLocalAccountName;
        TextView txtLocalAccountDescription;
        ImageView imageViewAccount;
        CardView cv;

        public MyViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            txtLocalAccountName = (TextView) itemView.findViewById(R.id.txtLocalAccountName);
            txtLocalAccountDescription = (TextView) itemView.findViewById(R.id.txtLocalAccountDescription);
            imageViewAccount = (ImageView) itemView.findViewById(R.id.imgLocalAccount);
        }
    }
}
