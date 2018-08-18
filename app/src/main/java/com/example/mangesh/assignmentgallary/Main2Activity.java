package com.example.mangesh.assignmentgallary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

    public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private cardStackAdapter cardStackAdapter;
    private FloatingActionButton fab;
    CardView galleryDrawer;
    TextView totalCount, pendingCount;
    DeadLine deadLineObj;
    DataBaseHandler DataBaseHandler;
    CardStackModel cardStackModel;
    private String[] num = {"one","two","three","four","five","six","seven","eight","nine","ten"};

    @Override
    protected void onResume() {
        super.onResume();

        try {
            prepareCardStack();
        }catch (Exception e){}

        createCardStack();

        int totalCountInt = DataBaseHandler.getAssignCount();
        int pendingCountInt = DataBaseHandler.getPendingAssignCount();

        totalCount.setText(""+totalCountInt+"");
        pendingCount.setText(""+pendingCountInt+"");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            prepareCardStack();
        }catch (Exception e){}

        createCardStack();

        int totalCountInt = DataBaseHandler.getAssignCount();
        int pendingCountInt = DataBaseHandler.getPendingAssignCount();

        totalCount.setText(""+totalCountInt+"");
        pendingCount.setText(""+pendingCountInt+"");

        assignFAB();

        assignGalleryDrawer();

    }

    private void prepareCardStack() throws Exception {

        String[] deadLines = DataBaseHandler.getColumn("A_DeadLine");
        cardStackModel = new CardStackModel();

        String[] sortedNames = new String[DataBaseHandler.getAssignCount()], sortedSubject = new String[DataBaseHandler.getAssignCount()], sortedStatus = new String[DataBaseHandler.getAssignCount()];

        String[] sortedDeadline = deadLineObj.sortDates(deadLines);

        long[] dateDiff = deadLineObj.calcDateDiff(sortedDeadline);
        cardStackModel.setRemainingDays(dateDiff);

        int n = DataBaseHandler.getAssignCount();
        int i=0;
        while(n>0){

            ArrayList item;
            item = DataBaseHandler.getValueOfAt("A_Name", sortedDeadline[i]);
            int v = item.size();
            for(int p=0; p<v; p++){

                sortedNames[i] = item.get(p).toString();
                i++;
                n--;

            }

        }

        int y = DataBaseHandler.getAssignCount();
        int j=0;
        while(y>0){

            ArrayList item;
            item = DataBaseHandler.getValueOfAt("A_Sub", sortedDeadline[j]);
            int v = item.size();
            for(int p=0; p<v; p++){

                sortedSubject[j] = item.get(p).toString();
                j++;
                y--;

            }

        }

        int z = DataBaseHandler.getAssignCount();
        int k=0;
        while(z>0){

            ArrayList item;
            item = DataBaseHandler.getValueOfAt("A_Status", sortedDeadline[k]);
            int v = item.size();
            for(int p=0; p<v; p++){

                sortedStatus[k] = item.get(p).toString();
                k++;
                z--;

            }

        }

        cardStackModel.setNameOfAssign(sortedNames);
        cardStackModel.setStatusOfAssign(sortedStatus);
        cardStackModel.setSubjectOfAssign(sortedSubject);

    }

    private void assignGalleryDrawer() {

        galleryDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, GalleryDrawerActivity.class);
                startActivity(intent);
            }
        });

    }

    private void assignFAB() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this, AddAssignActivity.class);
                startActivity(i);
            }
        });

    }

    private void createCardStack() {

        cardStackAdapter = new cardStackAdapter(getSupportFragmentManager(), cardStackModel);

        viewPager.setPageTransformer(true, new Main2Activity.CardStackTransformer());

        viewPager.setOffscreenPageLimit(40);

        viewPager.setAdapter(cardStackAdapter);


    }

    private void init() {

        viewPager = (ViewPager)findViewById(R.id.viewPagerCardNote);
        fab = findViewById(R.id.addAssignFAB);
        deadLineObj = new DeadLine();
        galleryDrawer = findViewById(R.id.galleryDrawer);
        DataBaseHandler = new DataBaseHandler(Main2Activity.this);
        totalCount = findViewById(R.id.totalCountTextView);
        pendingCount = findViewById(R.id.pendingCountTextView);

    }

    private class CardStackTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {

            if(position >= 0){

                page.setScaleX(0.95f - 0.009f * position);

                page.setScaleY(0.95f - 0.02f * position);

                page.setTranslationX(- page.getWidth() * position);

                page.setTranslationY(15 * position);

            }

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_assign) {
            Intent intent = new Intent(Main2Activity.this, AddAssignActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(Main2Activity.this, GalleryDrawerActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_over_due) {
            Intent intent = new Intent(Main2Activity.this, AssignOverDue.class);
            startActivity(intent);
        }else if (id == R.id.nav_share) {
            Toast.makeText(Main2Activity.this, "Please share the app with your friends, Thanks!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(Main2Activity.this, "Please send the app to your friends, Thanks!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
