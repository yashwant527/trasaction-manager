package com.example.parth.transactionmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

public class AllEntriesActivity extends AppCompatActivity {

    private EntryViewModel mEntryViewModel;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_entries);

        RecyclerView recyclerView = findViewById(R.id.all_recycler_view);
        final EntryListAdapter adapter = new EntryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);

        mEntryViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter.setEntries(null);

        mEntryViewModel.getAllEntries().observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                adapter.setEntries(entries);
            }
        });
    }
}
