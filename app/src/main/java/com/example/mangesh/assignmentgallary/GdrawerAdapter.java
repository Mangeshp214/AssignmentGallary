package com.example.mangesh.assignmentgallary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class GdrawerAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public GdrawerAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                GdrawerAllAssignFragment gdrawerAllAssignFragment = new GdrawerAllAssignFragment();
                return gdrawerAllAssignFragment;
            case 1:
                GdrawerNotWrittenAssignFragment gdrawerNotWrittenAssignFragment = new GdrawerNotWrittenAssignFragment();
                return gdrawerNotWrittenAssignFragment;
            case 2:
                GdrawerNotCheckAssignFragment gdrawerNotCheckAssignFragment = new GdrawerNotCheckAssignFragment();
                return gdrawerNotCheckAssignFragment;
            case 3:
                GdrawerWithFriendAssignFragment gdrawerWithFriendAssignFragment = new GdrawerWithFriendAssignFragment();
                return gdrawerWithFriendAssignFragment;
            case 4:
                GdrawerSubmittedAssignFragment gdrawerSubmittedAssignFragment = new GdrawerSubmittedAssignFragment();
                return gdrawerSubmittedAssignFragment;
            default:
                    return null;

        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
