package com.kun.allgo.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.allgo.Models.Workspace;
import com.kun.allgo.R;
import com.kun.allgo.Views.Adapter.WorkspaceAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private WorkspaceAdapter workspaceAdapter;
    private RecyclerView recyclerViewWorkspace;
    private View view;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        getFormWidget();
        // Inflate the layout for this fragment
        return view;
    }

    private void getFormWidget() {
        recyclerViewWorkspace = (RecyclerView) view.findViewById(R.id.rcvWorkspace);
        recyclerViewWorkspace.setHasFixedSize(true);
        recyclerViewWorkspace.setLayoutManager(new LinearLayoutManager(getActivity()));
        workspaceAdapter = new WorkspaceAdapter(getContext(),getWorkspaceData());

        recyclerViewWorkspace.setAdapter(workspaceAdapter);


    }

    private List<Workspace> getWorkspaceData() {
        List<Workspace> workspaces = new ArrayList<Workspace>();
        workspaces.add(new Workspace(1,"Nha tro", "day la nha tro", 1, 1, 1, null));
        workspaces.add(new Workspace(1,"Nha tro", "day la nha tro", 1, 1, 1, null));
        workspaces.add(new Workspace(1,"Nha tro", "day la nha tro", 1, 1, 1, null));
        workspaces.add(new Workspace(1,"Nha tro", "day la nha tro", 1, 1, 1, null));
        return workspaces;
    }

}
