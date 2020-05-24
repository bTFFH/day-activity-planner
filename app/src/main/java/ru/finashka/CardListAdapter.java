package ru.finashka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.finashka.entity.Card;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    class CardViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView startTimeView;
        private final TextView endTimeView;

        private CardViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.task_title);
            startTimeView = itemView.findViewById(R.id.task_start_time);
            endTimeView = itemView.findViewById(R.id.task_end_time);
        }
    }

    private final LayoutInflater mInflater;
    private List<Card> mCurrentCards;

    CardListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_card_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        if (mCurrentCards != null) {
            Card currentCard = mCurrentCards.get(position);
            holder.titleView.setText(currentCard.getTitle());

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd, HH:mm");
            holder.startTimeView.setText(dateFormat.format(currentCard.getStartTime()));
            holder.endTimeView.setText(dateFormat.format(currentCard.getEndTime()));
        }
    }

    void setCards(List<Card> cards) {
        mCurrentCards = cards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCurrentCards != null)
            return mCurrentCards.size();
        else return 0;
    }
}
