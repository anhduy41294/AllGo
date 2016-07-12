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

import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.Models.Room;
import com.kun.allgo.R;
import com.kun.allgo.Views.Fragments.LocalAccountFragment;
import com.kun.allgo.Views.Fragments.WindowsAccountFragment;

import java.util.Collections;
import java.util.List;

/**
 * Created by 12120 on 4/16/2016.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<Room> data= Collections.emptyList();
    private Context context;
    FragmentManager fragmentManager;

    public RoomAdapter(Context context, List<Room>data, FragmentManager fragmentManager){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.data=data;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view=layoutInflater.inflate(R.layout.custom_item_room,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        final Room current = data.get(position);
        holder.txtRoomName.setText(current.getmRoomName());
        holder.txtRoomDescription.setText(current.getmRoomDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable.currentRoomId = current.getmIdRoom();
                Log.d("id", GlobalVariable.currentRoomId);
                LocalAccountFragment localAccountFragmentFragment = new LocalAccountFragment();
                WindowsAccountFragment windowsAccountFragment = new WindowsAccountFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, localAccountFragmentFragment)
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

        TextView txtRoomName;
        TextView txtRoomDescription;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtRoomName = (TextView) itemView.findViewById(R.id.txtRoomName);
            txtRoomDescription = (TextView) itemView.findViewById(R.id.txtRoomDescription);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}