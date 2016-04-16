package com.kun.allgo.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
public class WorkspaceFragment extends Fragment {

    private WorkspaceAdapter workspaceAdapter;
    private RecyclerView recyclerViewWorkspace;
    private View view;

    private FloatingActionButton fab;

    public WorkspaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workspace, container, false);
        getFormWidget();
        addEvent();
        // Inflate the layout for this fragment
        return view;
    }

    private void addEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddWorkspaceActivity.class);
                startActivity(i);
            }
        });
    }

    private void getFormWidget() {
        recyclerViewWorkspace = (RecyclerView) view.findViewById(R.id.rcvWorkspace);
        workspaceAdapter = new WorkspaceAdapter(getContext(),getWorkspaceData());

        recyclerViewWorkspace.setAdapter(workspaceAdapter);

        fab = (FloatingActionButton) view.findViewById(R.id.fabAddWorkspace);

    }

    private List<Workspace> getWorkspaceData() {

        List<Workspace> workspaces = new ArrayList<Workspace>();
        workspaces.add(new Workspace());
        return workspaces;
    }
}
