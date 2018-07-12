package com.example.parth.transactionmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.EntryViewHolder> {

    private final LayoutInflater mInflater;
    private Context mContext;
    private List<User> mEntries; // Cached copy of words

    UserListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public UserListAdapter.EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.EntryViewHolder holder, int position) {
        if (!(mEntries == null) && !(mEntries.size() == 0)) {
            User current = mEntries.get(position);

            if (current.getDue() != 0) {
                holder.descTextView.setText(String.format("Tid's : %s", current.getPendingTransactions()));
                holder.nameTextView.setText(current.getName());
                if (current.getDue() > 0) {
                    holder.amountTextView.setText(String.format("+%s", String.valueOf(Math.abs(current.getDue()))));
                    holder.amountTextView.setTextColor(mContext.getResources().getColor(R.color.purple));
                    holder.sourceTextView.setText("(Take)");
                } else {
                    holder.amountTextView.setText(String.format("-%s", String.valueOf(Math.abs(current.getDue()))));
                    holder.amountTextView.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.sourceTextView.setText("(Give)");

                }
            }


        } else {
            // Covers the case of data not being ready yet.
            holder.nameTextView.setText("No Pending Transactions");
            holder.descTextView.setText("Click the plus icon to add a new transaction.");
        }
    }

    void setEntries(List<User> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (!(mEntries == null) && !(mEntries.size() == 0))
            return mEntries.size();
        else return 1;
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
