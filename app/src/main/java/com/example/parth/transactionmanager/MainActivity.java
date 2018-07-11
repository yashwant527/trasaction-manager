package com.example.parth.transactionmanager;

import android.animation.Animator;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private FloatingActionButton mDoneFab;
    private ConstraintLayout mLayoutMain;
    private ConstraintLayout mLayoutAdd;
    private ConstraintLayout mLayoutContent;

    private boolean isOpen = false;
    private boolean isDone = false;
    private TextView mViewAllView;

    private TextInputAutoCompleteTextView mTvFrom;
    private TextInputAutoCompleteTextView mTvTo;
    private TextInputAutoCompleteTextView mTvDesc;
    private TextInputAutoCompleteTextView mTvAmount;
    private RadioGroup mSource;
    private CheckBox mNeed;

    private int mCheckedId = -1;

    private EntryViewModel mEntryViewModel;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFab = findViewById(R.id.main_fab_add_transaction);
        mDoneFab = findViewById(R.id.main_fab_done_transaction);
        mLayoutMain = findViewById(R.id.layout_main);
        mLayoutAdd = findViewById(R.id.layout_2);
        mLayoutContent = findViewById(R.id.layout_1);

        mTvFrom = findViewById(R.id.new_transaction_edittext_from_child);
        mTvTo = findViewById(R.id.new_transaction_edittext_to_child);
        mTvDesc = findViewById(R.id.new_transaction_edittext_description_child);
        mTvAmount = findViewById(R.id.new_transaction_edittext_amount_child);
        mSource = findViewById(R.id.radioGroup);
        mNeed = findViewById(R.id.checkBox);

        mViewAllView = findViewById(R.id.tv_view_all);

        mViewAllView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllEntriesActivity.class);
                intent.putExtra("LAUNCH_TYPE", 1);
                startActivity(intent);
            }
        });

        mEntryViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);


        mTvFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mTvFrom.getText().toString().equals("Me") && !mTvFrom.getText().toString().isEmpty()) {
                    mTvTo.setText("Me");
                    checkFields(mCheckedId);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mTvTo.getText().toString().equals("Me") && !mTvTo.getText().toString().isEmpty()) {
                    mTvFrom.setText("Me");
                    checkFields(mCheckedId);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields(mCheckedId);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mCheckedId = checkedId;
                checkFields(mCheckedId);
            }
        });

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
                viewMenu();
                String from = mTvFrom.getText().toString();
                String to = mTvTo.getText().toString();
                String desc = mTvDesc.getText().toString();
                String amount = mTvAmount.getText().toString();
                int source = mCheckedId;
                boolean need = mNeed.isChecked();

                boolean src = source == R.id.new_transaction_radio_wallet;

                Entry entry = new Entry(from, to, desc, Double.valueOf(amount), src, need);

                mEntryViewModel.insert(entry);

                Intent intent = new Intent(MainActivity.this, AllEntriesActivity.class);
                startActivity(intent);
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

            mTvFrom.setText("");
            mTvTo.setText("");
            mTvDesc.setText("");
            mTvAmount.setText("");
            mSource.clearCheck();
            if (mNeed.isChecked()) mNeed.toggle();
            mCheckedId = -1;

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

    private void checkFields(int source) {
        String from = mTvFrom.getText().toString();
        String to = mTvTo.getText().toString();
        String amount = mTvAmount.getText().toString();

        if (!from.isEmpty() && !to.isEmpty() && !amount.isEmpty() && !(source == -1)) {
            if (!isDone)
                changeFabIcon();
        } else if (isDone) changeFabIcon();
    }
}
