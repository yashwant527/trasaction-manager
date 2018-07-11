package com.example.parth.transactionmanager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class EntryRepository {
    private EntryDao mEntryDao;
    private LiveData<List<Entry>> mAllEntries;

    EntryRepository(Application application) {
        EntryRoomDatabase db = EntryRoomDatabase.getDatabase(application);
        mEntryDao = db.entryDao();
        mAllEntries = mEntryDao.getAllEntries();
    }

    LiveData<List<Entry>> getAllEntries() {
        return mAllEntries;
    }


    public void insert(Entry entry) {
        new insertAsyncTask(mEntryDao).execute(entry);
    }

    private static class insertAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao mAsyncTaskDao;

        insertAsyncTask(EntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
