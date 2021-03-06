package com.kun.allgo.Views.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kun.allgo.Models.LocalAccount;
import com.kun.allgo.Models.Room;
import com.kun.allgo.Models.WindowAccount;
import com.kun.allgo.R;
import com.kun.allgo.SocketClient.SocketClient;
import com.kun.allgo.SocketClient.SocketClientWindowLogin;

import java.util.Collections;
import java.util.List;

/**
 * Created by 12120 on 4/16/2016.
 */
public class LocalAccountAdapter extends RecyclerView.Adapter<LocalAccountAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<WindowAccount> data = Collections.emptyList();
    private Context context;

    public LocalAccountAdapter(Context context, List<WindowAccount> data) {
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
        final WindowAccount current = data.get(position);
        holder.txtLocalAccountName.setText(current.getUserNameLC());
        holder.txtLocalAccountDescription.setText(current.getmAccountDescription());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SocketClient myClient = new SocketClient("192.168.1.76", 54015);
//                myClient.execute();
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Scanning...");
                progressDialog.show();

                SocketClientWindowLogin socketClientWindowLogin = new SocketClientWindowLogin(current.getmIP(), 54015, current.getPasswordLC(), context, progressDialog);
                socketClientWindowLogin.execute();
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
        CardView cv;

        public MyViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            txtLocalAccountName = (TextView) itemView.findViewById(R.id.txtLocalAccountName);
            txtLocalAccountDescription = (TextView) itemView.findViewById(R.id.txtLocalAccountDescription);
        }
    }
}