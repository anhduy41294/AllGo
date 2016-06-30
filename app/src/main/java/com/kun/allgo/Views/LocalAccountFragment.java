package com.kun.allgo.Views;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kun.allgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalAccountFragment extends Fragment {

    private View view;
    private FloatingActionButton fab;
    FragmentPagerAdapter adapterViewPager;
    LockableViewPager vpPager;

    public LocalAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_account, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fabAddAccount);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("LocalAccount");

        addEvent();
        //getFormWidget();
//        listLocalAccount.clear();
//        listLocalAccountId.clear();
//        getLocalAccountIdData();

        vpPager = (LockableViewPager) view.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setSwipeable(false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        return view;
    }

    private void addEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vpPager.getCurrentItem() == 0) {
                    AddLocalAccountFragment addLocalAccountFragmentFragment = new AddLocalAccountFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, addLocalAccountFragmentFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    AddApplicationAccountFragment addApplicationAccountFragment = new AddApplicationAccountFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, addApplicationAccountFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WindowsAccountFragment.newInstance(0, "Windows Account");
                case 1:
                    return ApplicationAccountFragment.newInstance(1, "Application Account");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Windows Account";
                case 1:
                    return "Application Account";
                default:
                    return null;
            }
        }

    }
}