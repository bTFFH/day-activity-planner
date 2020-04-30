package ru.finashka;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.finashka.dao.CardDao;
import ru.finashka.entity.Card;

class CardRepository {
    private CardDao mCardDao;
    private LiveData<List<Card>> mAllCards;

    CardRepository(Application app) {
        AppDatabase db = AppDatabase.getDatabase(app);
        mCardDao = db.cardDao();
        mAllCards = mCardDao.getAll();
    }

    LiveData<List<Card>> getAllCards() {
        return mAllCards;
    }

    void insert(final Card card) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mCardDao.insert(card);
            }
        });
    }
}
