package com.kun.allgo.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.allgo.Models.LocalAccount;
import com.kun.allgo.R;
import com.kun.allgo.Views.Adapter.LocalAccountAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalAccountFragment extends Fragment {

    private LocalAccountAdapter localAccountAdapter;
    private RecyclerView recyclerViewLA;
    private View view;
    private FloatingActionButton fab;

    public LocalAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_account, container, false);

        getFormWidget();
        addEvent();
        return view;
    }
    private void addEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddRoomActivity.class);
                startActivity(i);
            }
        });
    }

    private void getFormWidget() {
        recyclerViewLA = (RecyclerView) view.findViewById(R.id.rcvLocalAccount);
        localAccountAdapter = new LocalAccountAdapter(getContext(),getLocalAccountData());

        recyclerViewLA.setAdapter(localAccountAdapter);

        fab = (FloatingActionButton) view.findViewById(R.id.fabAddRoom);

    }

    private List<LocalAccount> getLocalAccountData() {

        List<LocalAccount> la = new ArrayList<LocalAccount>();
        la.add(new LocalAccount());
        return la;
    }
}