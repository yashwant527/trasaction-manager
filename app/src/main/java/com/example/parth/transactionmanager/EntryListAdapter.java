package com.example.parth.transactionmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
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
    public void onBindViewHolder(@NonNull final EntryListAdapter.EntryViewHolder holder, int position) {
        if (!(mEntries == null) && !(mEntries.size() == 0)) {
            Entry current = mEntries.get(position);

            holder.dateTextView.setVisibility(View.GONE);
            holder.descTextView.setText(current.getDescription());
            if (current.getTo().equals("Me")) {
                holder.nameTextView.setText(current.getFrom());
                holder.amountTextView.setText(String.format("+%s", String.valueOf(current.getAmount())));
                holder.amountTextView.setTextColor(mContext.getResources().getColor(R.color.purple));
            } else {
                holder.nameTextView.setText(current.getTo());
                holder.amountTextView.setText(String.format("-%s", String.valueOf(current.getAmount())));
                holder.amountTextView.setTextColor(mContext.getResources().getColor(R.color.red));
            }

            if (current.isWallet()) holder.sourceTextView.setText("(Wallet)");
            else holder.sourceTextView.setText("(Account)");

            Date date = DateConverter.fromTimestamp(current.getDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            String day = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
            int din = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
            String month = getMonthofYear(cal.get(Calendar.MONTH) + 1);
            int hour = cal.get(Calendar.HOUR);
            int minutes = cal.get(Calendar.MINUTE);
            String am_pm = cal.get(Calendar.AM_PM) == 0 ? "AM" : "PM";

            holder.dateTextView.setText(String.format("On %s, %d %s at %02d:%02d %s", day, din, month, hour, minutes, am_pm));

            holder.mainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionManager.beginDelayedTransition((ViewGroup) v);
                    holder.dateTextView.setVisibility(View.VISIBLE);
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.nameTextView.setText("No Transaction Yet");
            holder.descTextView.setText("You have'nt done any transactions yet.");
        }
    }

    void setEntries(List<Entry> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (!(mEntries == null) && !(mEntries.size() == 0))
            return mEntries.size();
        else return 1;
    }

    private String getDayOfWeek(int value) {
        String day = "";
        switch (value) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
        }
        return day;
    }

    private String getMonthofYear(int value) {
        String month = "";
        switch (value) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
        }
        return month;
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {
        private final CardView mainView;
        private final TextView nameTextView;
        private final TextView amountTextView;
        private final TextView descTextView;
        private final TextView sourceTextView;
        private final TextView dateTextView;

        public EntryViewHolder(View itemView) {
            super(itemView);
            mainView = itemView.findViewById(R.id.main_cardview);
            nameTextView = itemView.findViewById(R.id.tv_name);
            amountTextView = itemView.findViewById(R.id.tv_amount);
            descTextView = itemView.findViewById(R.id.tv_description);
            sourceTextView = itemView.findViewById(R.id.tv_source);
            dateTextView = itemView.findViewById(R.id.tv_date);
        }
    }
}