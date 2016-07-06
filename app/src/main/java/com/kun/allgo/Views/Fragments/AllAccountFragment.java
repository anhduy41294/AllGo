package com.kun.allgo.Views.Fragments;


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
import com.kun.allgo.Views.ApplicationAccountFragment;
import com.kun.allgo.Views.LockableViewPager;
import com.kun.allgo.Views.WindowsAccountFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllAccountFragment extends Fragment {

    private View view;
    private FloatingActionButton fab;
    FragmentPagerAdapter adapterViewPager;
    LockableViewPager vpPager;
    private static final int ALL_ACCOUNT_CODE = 1;

    public AllAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_account, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("All Account");

        //getFormWidget();
//        listWindowAccount.clear();
//        listWindowAccountId.clear();
//        getLocalAccountIdData();

        vpPager = (LockableViewPager) view.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setSwipeable(false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        return view;
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
                    return WindowsAccountFragment.newInstance(0, "Windows Account", ALL_ACCOUNT_CODE);
                case 1:
                    return ApplicationAccountFragment.newInstance(1, "Application Account", ALL_ACCOUNT_CODE);
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
