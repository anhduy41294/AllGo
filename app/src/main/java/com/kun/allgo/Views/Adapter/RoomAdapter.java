package com.kun.allgo.Views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kun.allgo.Models.Room;
import com.kun.allgo.Models.Workspace;
import com.kun.allgo.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by 12120 on 4/16/2016.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<Room> data= Collections.emptyList();
    private Context context;

    public RoomAdapter(Context context,List<Room>data){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=layoutInflater.inflate(R.layout.custom_item_room,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        Room current= data.get(position);
        holder.txtRoomName.setText(current.getmRoomName());
        holder.txtRoomDescription.setText(current.getmRoomDescription());
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtRoomName;
        TextView txtRoomDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtRoomName = (TextView) itemView.findViewById(R.id.txtRoomName);
            txtRoomDescription = (TextView) itemView.findViewById(R.id.txtRoomDescription);
        }
    }
}