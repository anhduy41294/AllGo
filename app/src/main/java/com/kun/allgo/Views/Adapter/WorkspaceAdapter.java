package com.kun.allgo.Views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kun.allgo.Models.Workspace;
import com.kun.allgo.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by 12120 on 4/16/2016.
 */
public class WorkspaceAdapter extends RecyclerView.Adapter<WorkspaceAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<Workspace> data= Collections.emptyList();
    private Context context;

    public WorkspaceAdapter(Context context,List<Workspace>data){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=layoutInflater.inflate(R.layout.custom_item_workspace,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        Workspace current= data.get(position);
        holder.txtWorkspaceName.setText(current.getWorkspaceName());
        holder.txtWorkspaceDescription.setText(current.getWorkspaceDescription());
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtWorkspaceName;
        TextView txtWorkspaceDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtWorkspaceName = (TextView) itemView.findViewById(R.id.txtWorkspaceName);
            txtWorkspaceDescription = (TextView) itemView.findViewById(R.id.txtWorkspaceDescription);
        }
    }
}