package ru.finashka;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import ru.finashka.entity.Card;

public class CardViewModel extends AndroidViewModel {
    private CardRepository mRepository;

    public CardViewModel(Application app) {
        super(app);
        mRepository = new CardRepository(app);
    }

    LiveData<List<Card>> getCardsByDate(Calendar date) {
        return mRepository.getCardsByDate(
                // просто преобразование Calendar -> LocalDate
                LocalDateTime.ofInstant(date.toInstant(), date.getTimeZone().toZoneId()).toLocalDate()
        );
    }

    public void insert(Card card) {
        mRepository.insert(card);
    }
}
