package com.example.mangesh.assignmentgallary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class cardStackAdapter extends FragmentStatePagerAdapter {

    CardStackModel cardStackModel;

    public cardStackAdapter(FragmentManager fm) {
        super(fm);
    }

    public cardStackAdapter(FragmentManager fm, CardStackModel cardStackModel) {
        super(fm);
        this.cardStackModel = cardStackModel;
    }


    @Override
    public Fragment getItem(int position) {
        return new CardStackFragment(position, cardStackModel);
    }

    @Override
    public int getCount() {
        return cardStackModel.getRemainingDays().length;
    }

}

