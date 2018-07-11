package com.example.parth.transactionmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.EntryViewHolder> {

    private final LayoutInflater mInflater;
    private Context mContext;
    private List<Entry> mEntries; // Cached copy of words

    EntryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public EntryListAdapter.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryListAdapter.EntryViewHolder holder, int position) {
        if (mEntries != null) {
            Entry current = mEntries.get(position);

            holder.descTextView.setText(current.getDescription());
            if (current.getTo().equals("Me")) {
                holder.nameTextView.setText(current.getFrom());
                holder.amountTextView.setText(String.format("+%s", String.valueOf(current.getAmount())));
                holder.amountTextView.setTextColor(mContext.getResources().getColor(R.color.green));
            } else {
                holder.nameTextView.setText(current.getTo());
                holder.amountTextView.setText(String.format("-%s", String.valueOf(current.getAmount())));
                holder.amountTextView.setTextColor(mContext.getResources().getColor(R.color.red));
            }

            if (current.isWallet()) holder.sourceTextView.setText("(Wallet)");
            else holder.sourceTextView.setText("(Account)");

        } else {
            // Covers the case of data not being ready yet.
            holder.nameTextView.setText("No Transaction");
        }
    }

    void setEntries(List<Entry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mEntries != null)
            return mEntries.size();
        else return 0;
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView amountTextView;
        private final TextView descTextView;
        private final TextView sourceTextView;

        public EntryViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_name);
            amountTextView = itemView.findViewById(R.id.tv_amount);
            descTextView = itemView.findViewById(R.id.tv_description);
            sourceTextView = itemView.findViewById(R.id.tv_source);
        }
    }
}
