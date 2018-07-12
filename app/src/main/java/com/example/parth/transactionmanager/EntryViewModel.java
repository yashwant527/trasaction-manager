package com.example.parth.transactionmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository mRepository;
    private UserRepository mUserRepository;

    private LiveData<List<Entry>> mAllEntries;
    private LiveData<List<User>> mAllUsers;

    public EntryViewModel(Application application) {
        super(application);
        mRepository = new EntryRepository(application);
        mUserRepository = new UserRepository(application);
        mAllEntries = mRepository.getAllEntries();
        mAllUsers = mUserRepository.getAllUsers();
    }

    LiveData<List<Entry>> getAllEntries() {
        return mAllEntries;
    }

    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(Entry entry) {
        mRepository.insert(entry);
    }

    public void insertUser(User user) {
        mUserRepository.insert(user);
    }

    public void updateUser(User user) {
        mUserRepository.update(user);
    }
}
