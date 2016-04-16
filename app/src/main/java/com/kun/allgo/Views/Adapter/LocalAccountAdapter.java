package com.kun.allgo.Views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kun.allgo.Models.LocalAccount;
import com.kun.allgo.Models.Room;
import com.kun.allgo.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by 12120 on 4/16/2016.
 */
public class LocalAccountAdapter extends RecyclerView.Adapter<LocalAccountAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<LocalAccount> data = Collections.emptyList();
    private Context context;

    public LocalAccountAdapter(Context context, List<LocalAccount> data) {
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
        LocalAccount current = data.get(position);
        holder.txtLocalAccountName.setText(current.getUserNameLC());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtLocalAccountName;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtLocalAccountName = (TextView) itemView.findViewById(R.id.txtLocalAccountName);

        }
    }
}