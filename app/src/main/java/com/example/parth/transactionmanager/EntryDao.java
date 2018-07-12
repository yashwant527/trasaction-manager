package com.example.parth.transactionmanager;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert
    void insert(Entry entry);

    @Query("SELECT * from entry_table ORDER BY tid DESC")
    LiveData<List<Entry>> getAllEntries();

    @Query("DELETE FROM entry_table")
    void deleteAll();

}
