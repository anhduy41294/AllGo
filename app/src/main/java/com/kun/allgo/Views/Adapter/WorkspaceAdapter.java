package com.kun.allgo.Views.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kun.allgo.Global.Constant;
import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.Workspace;
import com.kun.allgo.R;
import com.kun.allgo.Views.RoomFragment;

import java.util.Collections;
import java.util.List;

/**
 * Created by 12120 on 4/16/2016.
 */
public class WorkspaceAdapter extends RecyclerView.Adapter<WorkspaceAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<Workspace> data= Collections.emptyList();
    public Context context;
    FragmentManager fragmentManager;

    public WorkspaceAdapter(Context context, List<Workspace>data, FragmentManager fragmentManager){
        this.context=context;
        layoutInflater = LayoutInflater.from(context);
        this.data=data;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=layoutInflater.inflate(R.layout.custom_item_workspace,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        final Workspace workspace = data.get(position);
        holder.txtWorkspaceName.setText(workspace.getmWorkspaceName());
        holder.txtWorkspaceDescription.setText(workspace.getmWorkspaceDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable.currentWorkspaceId = workspace.getmIdWorkspace();
                Log.d("id", GlobalVariable.currentWorkspaceId);
                RoomFragment roomFragment = new RoomFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, roomFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtWorkspaceName;
        TextView txtWorkspaceDescription;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtWorkspaceName = (TextView) itemView.findViewById(R.id.txtWorkspaceName);
            txtWorkspaceDescription = (TextView) itemView.findViewById(R.id.txtWorkspaceDescription);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}