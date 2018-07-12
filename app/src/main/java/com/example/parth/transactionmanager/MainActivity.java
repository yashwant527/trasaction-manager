package com.example.parth.transactionmanager;

import android.animation.Animator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String mypreference = "mypref";
    public static final String Account = "accountKey";
    public static final String Wallet = "WalletKey";
    public static final String TID = "TidKey";

    private FloatingActionButton mFab;
    SharedPreferences sharedpreferences;
    private FloatingActionButton mDoneFab;
    private ConstraintLayout mLayoutMain;
    private ConstraintLayout mLayoutAdd;
    private ConstraintLayout mLayoutContent;

    private boolean isOpen = false;
    private boolean isDone = false;

    private TextInputAutoCompleteTextView mTvFrom;
    private TextInputAutoCompleteTextView mTvTo;
    private TextInputAutoCompleteTextView mTvDesc;
    private TextInputAutoCompleteTextView mTvAmount;
    private RadioGroup mSource;
    private CheckBox mNeed;

    private int mCheckedId = -1;

    private EntryViewModel mEntryViewModel;
    private TextView mWalletTv;
    private TextView mAccountTv;
    private CardView mWalletCardView;
    private CardView mAccountCardView;
    private TextView mViewAllView;
    private List<User> mUsers;
    private Toast mToast;

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

        mWalletTv = findViewById(R.id.wallet_tv);
        mAccountTv = findViewById(R.id.account_tv);

        mViewAllView = findViewById(R.id.tv_view_all);

        mWalletCardView = findViewById(R.id.cardView_wallet);
        mAccountCardView = findViewById(R.id.cardView_Account);

        sharedpreferences = getSharedPreferences(mypreference,
                MODE_PRIVATE);

        mWalletCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDialog(0);
            }
        });
        mAccountCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDialog(1);
            }
        });

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
                mNeed.setChecked(true);
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

                Date date = Calendar.getInstance().getTime();
                long convertedDate = DateConverter.dateToTimestamp(date);

                Entry entry = new Entry(from, to, desc, Double.valueOf(amount), src, need, convertedDate);

                mEntryViewModel.insert(entry);

                int tid = sharedpreferences.getInt(TID, 0);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(TID, tid + 1);
                editor.apply();

                if (from.equals("Me")) {
                    if (need)
                        updateUsers(to, Double.valueOf(amount), String.valueOf(tid));
                    if (src)
                        updateBalance(0, -1 * Double.valueOf(amount));
                    else
                        updateBalance(1, -1 * Double.valueOf(amount));
                } else {
                    if (need)
                        updateUsers(from, -1 * Double.valueOf(amount), String.valueOf(tid));
                    if (src)
                        updateBalance(0, Double.valueOf(amount));
                    else
                        updateBalance(1, Double.valueOf(amount));
                }
                showToast("Added Transaction Successfully!");
            }
        });


        RecyclerView recyclerView = findViewById(R.id.pending_recycler_view);
        final UserListAdapter adapter = new UserListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEntryViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> entries) {
                adapter.setEntries(entries);
                mUsers = entries;
            }
        });

        if (sharedpreferences.contains(Wallet)) {
            mWalletTv.setText(String.format("%s%s", getText(R.string.rupee_symbol), sharedpreferences.getString(Wallet, "NA")));
        }
        if (sharedpreferences.contains(Account)) {
            mAccountTv.setText(String.format("%s%s", getText(R.string.rupee_symbol), sharedpreferences.getString(Account, "NA")));
        }
        if (!sharedpreferences.contains(TID)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(TID, 0);
            editor.apply();
        }

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

    private void updateUsers(String name, double amount, String tid) {
        User user = null;
        if (mUsers != null) {
            for (User current : mUsers) {
                if (current.getName().equals(name)) {
                    user = current;
                    break;
                }

            }

            if (user == null) {
                user = new User(name, amount, tid, tid);
                mEntryViewModel.insertUser(user);
            } else {
                user.setmDue(user.getDue() + amount);
                user.setmAllTransactions(user.getAllTransactions() + ", " + tid);
                if (user.getDue() == 0) {
                    user.setmPendingTransactions("");
                } else {
                    if (user.getPendingTransactions().equals("")) user.setmPendingTransactions(tid);
                    else user.setmPendingTransactions(user.getPendingTransactions() + ", " + tid);
                }
                mEntryViewModel.insertUser(user);
            }


        }

    }

    private void viewDialog(final int val) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.edit_text_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);

        final EditText userInputDialogEditText = mView.findViewById(R.id.userInputDialog);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        String text = userInputDialogEditText.getText().toString();
                        if (!text.isEmpty())
                            if (val == 0) {
                                mWalletTv.setText(text);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Wallet, text);
                                editor.apply();
                            } else {
                                mAccountTv.setText(text);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Account, text);
                                editor.apply();
                            }
                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    private void showToast(String s) {
        if (mToast == null) {
            mToast = new Toast(this);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        } else {
            mToast.setText(s);
            mToast.show();
        }
    }

    private void updateBalance(int source, double amount) {
        if (source == 0) {
            double amt = Double.valueOf(mWalletTv.getText().toString().substring(1)) + amount;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Wallet, String.valueOf(amt));
            editor.apply();
            mWalletTv.setText(String.format("%s %s", getText(R.string.rupee_symbol), String.valueOf(amt)));
        } else {
            double amt = Double.valueOf(mAccountTv.getText().toString().substring(1)) + amount;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Account, String.valueOf(amt));
            editor.apply();
            mAccountTv.setText(String.format("%s %s", getText(R.string.rupee_symbol), String.valueOf(amt)));

        }
    }
}
