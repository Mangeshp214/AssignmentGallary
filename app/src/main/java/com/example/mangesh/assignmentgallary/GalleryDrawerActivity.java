package com.example.mangesh.assignmentgallary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

public class GalleryDrawerActivity extends AppCompatActivity implements
    GdrawerAllAssignFragment.OnFragmentInteractionListener,
    GdrawerNotWrittenAssignFragment.OnFragmentInteractionListener,
    GdrawerNotCheckAssignFragment.OnFragmentInteractionListener,
    GdrawerSubmittedAssignFragment.OnFragmentInteractionListener,
    GdrawerWithFriendAssignFragment.OnFragmentInteractionListener {

    FloatingActionButton addAssignFAB;
    ImageView navBack, searchAssign;
    ViewPager viewPager;
    TabLayout tabLayout;
    SearchView searchView;
    CardView serachBarCardView;

    @Override
    protected void onResume() {
        super.onResume();

        int tab = getIntent().getIntExtra("tab",0);
        tabLayout = findViewById(R.id.GdrawerTabLayout);
        viewPager = findViewById(R.id.GdrawerViewPager);
        GdrawerAdapter pagerAdapter = new GdrawerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.setCurrentItem(tab);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_drawer);

        init();

        addAssignFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GalleryDrawerActivity.this, AddAssignActivity.class);
                startActivity(i);
            }
        });

        tabLayout = findViewById(R.id.GdrawerTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("ALL"));
        tabLayout.addTab(tabLayout.newTab().setText("NW"));
        tabLayout.addTab(tabLayout.newTab().setText("NC"));
        tabLayout.addTab(tabLayout.newTab().setText("DF"));
        tabLayout.addTab(tabLayout.newTab().setText("SA"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = findViewById(R.id.GdrawerViewPager);
        GdrawerAdapter pagerAdapter = new GdrawerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(2);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        searchAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serachBarCardView.setVisibility(View.VISIBLE);
                searchAssign.setSelected(true);
            }
        });


    }

    private void init() {

       addAssignFAB = findViewById(R.id.addAssignFAB);
       navBack = findViewById(R.id.navBack);
       searchAssign = findViewById(R.id.searchImageView);
       searchView = findViewById(R.id.searchView);
       serachBarCardView = findViewById(R.id.cardViewSearchBar);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
