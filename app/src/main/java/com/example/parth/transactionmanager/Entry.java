package com.example.parth.transactionmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "entry_table")
public class Entry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    private int mTid;

    @NonNull
    @ColumnInfo(name = "from")
    private String mFrom;

    @NonNull
    @ColumnInfo(name = "to")
    private String mTo;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "amount")
    private double mAmount;

    @ColumnInfo(name = "wallet")
    private boolean mWallet;

    @ColumnInfo(name = "need")
    private boolean mNeed;

    @ColumnInfo(name = "date")
    private long mDate;


    public Entry(@NonNull String from, @NonNull String to, String description,
                 @NonNull double amount, @NonNull boolean wallet, @NonNull boolean need, @NonNull long date) {
        this.mFrom = from;
        this.mTo = to;
        this.mDescription = description;
        this.mAmount = amount;
        this.mWallet = wallet;
        this.mNeed = need;
        this.mDate = date;
    }

    public int getTid() {
        return mTid;
    }

    @NonNull
    public String getFrom() {
        return mFrom;
    }

    @NonNull
    public String getTo() {
        return mTo;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getAmount() {
        return mAmount;
    }

    public boolean isWallet() {
        return mWallet;
    }

    public boolean isNeed() {
        return mNeed;
    }

    public long getDate() {
        return mDate;
    }

    public void setTid(int tid) {
        this.mTid = tid;
    }
}
