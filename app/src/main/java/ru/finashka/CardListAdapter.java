package ru.finashka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.finashka.entity.Card;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {
    class CardViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardItemView;

        private CardViewHolder(View itemView) {
            super(itemView);
            cardItemView = itemView.findViewById(R.id.title);
        }
    }

    private final LayoutInflater mInflater;
    private List<Card> mCards; // Cached copy of words

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
        if (mCards != null) {
            Card current = mCards.get(position);
            holder.cardItemView.setText(current.getCard().getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.cardItemView.setText("No Title");
        }
    }

    void setCards(List<Card> cards) {
        mCards = cards;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCards != null)
            return mCards.size();
        else return 0;
    }
}
