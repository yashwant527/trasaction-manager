package com.example.parth.transactionmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "due")
    private double mDue;

    @ColumnInfo(name = "pending_transactions")
    private String mPendingTransactions;

    @ColumnInfo(name = "all_transactions")
    private String mAllTransactions;

    public User(String name, double due, String pendingTransactions, String allTransactions) {
        this.mName = name;
        this.mDue = due;
        this.mPendingTransactions = pendingTransactions;
        this.mAllTransactions = allTransactions;
    }

    public void setmDue(double mDue) {
        this.mDue = mDue;
    }

    public void setmPendingTransactions(String mPendingTransactions) {
        this.mPendingTransactions = mPendingTransactions;
    }

    public void setmAllTransactions(String mAllTransactions) {
        this.mAllTransactions = mAllTransactions;
    }

    public String getName() {
        return mName;
    }

    public Double getDue() {
        return mDue;
    }

    public String getPendingTransactions() {
        return mPendingTransactions;
    }

    public String getAllTransactions() {
        return mAllTransactions;
    }
}
