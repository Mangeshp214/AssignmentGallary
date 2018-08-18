package com.example.mangesh.assignmentgallary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private cardStackAdapter cardStackAdapter;
    private FloatingActionButton fab;
    CardView galleryDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        createCardStack();

        assignFAB();

        assignGalleryDrawer();

    }

    private void assignGalleryDrawer() {

        galleryDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GalleryDrawerActivity.class);
                startActivity(intent);
            }
        });

    }

    private void assignFAB() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddAssignActivity.class);
                startActivity(i);
            }
        });

    }

    private void createCardStack() {

        cardStackAdapter = new cardStackAdapter(getSupportFragmentManager());

        viewPager.setPageTransformer(true, new CardStackTransformer());

        viewPager.setOffscreenPageLimit(20);

        viewPager.setAdapter(cardStackAdapter);

    }

    private void init() {

        viewPager = (ViewPager)findViewById(R.id.viewPagerCardNote);
        fab = findViewById(R.id.addAssignFAB);
        galleryDrawer = findViewById(R.id.galleryDrawer);

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

}
