package com.example.parth.transactionmanager;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User... user);

    @Query("SELECT * from user_table ORDER BY name ASC")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE FROM user_table")
    void deleteAll();
}
