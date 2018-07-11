package com.example.parth.transactionmanager;

import android.animation.Animator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private FloatingActionButton mDoneFab;
    private ConstraintLayout mLayoutMain;
    private ConstraintLayout mLayoutAdd;
    private ConstraintLayout mLayoutContent;
    private boolean isOpen = false;
    private boolean isDone = false;
    private Toolbar mToolbar;

    private CheckBox test;

    private EntryViewModel mEntryViewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.main_fab_add_transaction);
        mDoneFab = findViewById(R.id.main_fab_done_transaction);
        mToolbar = findViewById(R.id.main_toolbar);
        mLayoutMain = findViewById(R.id.layout_main);
        mLayoutAdd = findViewById(R.id.layout_2);
        mLayoutContent = findViewById(R.id.layout_1);

        test = findViewById(R.id.checkBox);


        setSupportActionBar(mToolbar);

        mEntryViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMenu();
            }
        });

        mDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFabIcon();
            }
        });

        test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeFabIcon();
            }
        });

    }


    private void viewMenu() {

        if (!isOpen) {

            int x = mFab.getLeft() + mFab.getWidth() / 2;
            int y = mFab.getTop() - mFab.getHeight() / 2;

            int startRadius = 0;
            int endRadius = Math.max(mLayoutContent.getWidth(), mLayoutContent.getHeight());

            mFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
            mFab.setImageResource(R.drawable.ic_close_grey);

            Animator anim = ViewAnimationUtils.createCircularReveal(mLayoutAdd, x, y, startRadius, endRadius);

            mLayoutAdd.setVisibility(View.VISIBLE);
            anim.start();

            isOpen = true;

        } else {
            int x = mFab.getLeft() + mFab.getWidth() / 2;
            int y = mFab.getTop() - mFab.getHeight() / 2;

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

    private void changeFabIcon() {

        if (!isDone) {

            int x = mDoneFab.getWidth() / 2;
            int y = mDoneFab.getHeight() / 2;

            int startRadius = 0;
            int endRadius = mDoneFab.getWidth();

            Animator anim = ViewAnimationUtils.createCircularReveal(mDoneFab, x, y, startRadius, endRadius);

            mDoneFab.setVisibility(View.VISIBLE);
            anim.start();
            isDone = true;

        } else {
            int x = mDoneFab.getWidth() / 2;
            int y = mDoneFab.getHeight() / 2;

            int startRadius = mDoneFab.getWidth();
            int endRadius = 0;

            Animator anim = ViewAnimationUtils.createCircularReveal(mDoneFab, x, y, startRadius, endRadius);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mDoneFab.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();

            isDone = false;
        }
    }
}
