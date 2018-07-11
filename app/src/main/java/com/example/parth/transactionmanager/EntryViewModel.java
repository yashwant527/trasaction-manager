package com.example.parth.transactionmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository mRepository;

    private LiveData<List<Entry>> mAllEntries;

    public EntryViewModel(Application application) {
        super(application);
        mRepository = new EntryRepository(application);
        mAllEntries = mRepository.getAllEntries();
    }

    LiveData<List<Entry>> getAllWords() {
        return mAllEntries;
    }

    public void insert(Entry entry) {
        mRepository.insert(entry);
    }
}
