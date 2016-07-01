package com.kun.allgo.Views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.ApplicationAccount;
import com.kun.allgo.Models.LocalAccount;
import com.kun.allgo.R;
import com.kun.allgo.SocketClient.SocketClient;
import com.kun.allgo.SocketClient.SocketClientAutoLoginController;
import com.kun.allgo.Utils.EncyptionHelper;

import java.util.Collections;
import java.util.List;

/**
 * Created by Duy on 30-Jun-16.
 */
public class ApplicationAccountAdapter extends RecyclerView.Adapter<ApplicationAccountAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<ApplicationAccount> data = Collections.emptyList();
    private Context context;

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

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ApplicationAccount current = data.get(position);
        holder.txtLocalAccountName.setText(current.getmAppUsername());
        holder.txtLocalAccountDescription.setText(current.getmAppDescription());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Encryption
                String encryptedUser ="";
                String encryptedPass ="";
                encryptedUser = EncyptionHelper.EncryptDataAutoLogin(current.getmAppUsername());
                encryptedPass = EncyptionHelper.EncryptDataAutoLogin(current.getmAppPassword());



                SocketClientAutoLoginController socketClientAutoLoginController =
                        new SocketClientAutoLoginController(GlobalVariable.IPCurrentPC,
                        GlobalVariable.PortCurrentPC, encryptedUser, encryptedPass);
                socketClientAutoLoginController.execute();
            }
        });
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
