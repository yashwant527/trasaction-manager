package com.example.parth.transactionmanager;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private ConstraintLayout mLayoutMain;
    private ConstraintLayout mLayoutAdd;
    private ConstraintLayout mLayoutContent;
    private boolean isOpen = false;
    private Toolbar mToolbar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.main_fab_add_transaction);
        mToolbar = findViewById(R.id.main_toolbar);
        mLayoutMain = findViewById(R.id.layout_main);
        mLayoutAdd = findViewById(R.id.layout_2);
        mLayoutContent = findViewById(R.id.layout_1);

        setSupportActionBar(mToolbar);


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMenu();
            }
        });
    }


    private void viewMenu() {

        if (!isOpen) {

            int x = mFab.getLeft() + (mFab.getRight()-mFab.getLeft())/2;
            int y = mFab.getTop() + (mFab.getBottom()-mFab.getTop())/2-150;

            int startRadius = 0;
            int endRadius = (int) Math.hypot(mLayoutMain.getWidth(), mLayoutMain.getHeight());

            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
            mFab.setImageResource(R.drawable.ic_close_grey);

            Animator anim = ViewAnimationUtils.createCircularReveal(mLayoutAdd, x, y, startRadius, endRadius);

            mLayoutAdd.setVisibility(View.VISIBLE);
            anim.start();

            isOpen = true;

        } else {

            int x = mFab.getLeft() + (mFab.getRight()-mFab.getLeft())/2;
            int y = mFab.getTop() + (mFab.getBottom()-mFab.getTop())/2 - 150;

            int startRadius = Math.max(mLayoutContent.getWidth(), mLayoutContent.getHeight());
            int endRadius = 0;

            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            mFab.setImageResource(R.drawable.baseline_add_24);

            Animator anim = ViewAnimationUtils.createCircularReveal(mLayoutAdd, x, y, startRadius, endRadius);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mLayoutAdd.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();

            isOpen = false;
        }
    }
}
