package ru.finashka;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.finashka.entity.Card;

public class CardViewModel extends AndroidViewModel {
    private CardRepository mRepository;
    private LiveData<List<Card>> mAllCards;

    public CardViewModel(Application app) {
        super(app);
        mRepository = new CardRepository(app);
        mAllCards = mRepository.getAllCards();
    }

    LiveData<List<Card>> getAllCards() {
        return mAllCards;
    }

    public void insert(Card card) {
        mRepository.insert(card);
    }
}
