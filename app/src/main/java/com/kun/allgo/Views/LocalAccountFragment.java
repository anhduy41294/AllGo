package com.kun.allgo.Views;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kun.allgo.Global.GlobalVariable;
import com.kun.allgo.R;
import com.kun.allgo.Utils.QRCodeDataParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalAccountFragment extends Fragment {

    private View view;
    private FloatingActionButton fab;
    FragmentPagerAdapter adapterViewPager;
    LockableViewPager vpPager;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar = null;

    public LocalAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_local_account, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fabAddAccount);
        coordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.coordinatorLayout);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("LocalAccount");

        addEvent();

        vpPager = (LockableViewPager) view.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setSwipeable(false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    if (GlobalVariable.IPCurrentPC.equals("")) {
                        snackbar = Snackbar
                                .make(coordinatorLayout, "Not connect PC", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Connect", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(getActivity(), ScanQRCodeActivity.class);
                                        startActivityForResult(i, 2);
                                    }
                                });
                        //snackbar.getView().setBackgroundColor(0xfff44336);
                        snackbar.show();
                    }
                } else {
                    if (snackbar != null) {
                        snackbar.dismiss();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.appaccountmenu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_connect) {
//            Intent i = new Intent(getActivity(), ScanQRCodeActivity.class);
//            startActivityForResult(i, 2);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {

            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                QRCodeDataParser.QRCodeConnectPCParser(result);

                //
                snackbar = Snackbar
                        .make(coordinatorLayout, "Connecting " + GlobalVariable.IPCurrentPC, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Change", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getActivity(), ScanQRCodeActivity.class);
                                startActivityForResult(i, 2);
                            }
                        });
                //snackbar.getView().setBackgroundColor(0xff2195f3);
                snackbar.show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
                    return WindowsAccountFragment.newInstance(0, "Windows Account", 0);
                case 1:
                    return ApplicationAccountFragment.newInstance(1, "Application Account", 0);
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