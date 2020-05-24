package ru.finashka;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ru.finashka.dao.CardDao;
import ru.finashka.entity.Card;

class CardRepository {
    private CardDao mCardDao;

    CardRepository(Application app) {
        AppDatabase db = AppDatabase.getDatabase(app);
        mCardDao = db.cardDao();
    }

    LiveData<List<Card>> getCardsByDate(LocalDate date) {
        LocalDateTime dateStart = date.atStartOfDay();
        LocalDateTime dateEnd = LocalTime.MAX.atDate(date);

        return mCardDao.getAllByIncludeDate(date);
    }

    void insert(final Card card) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mCardDao.insert(card);
            }
        });
    }

    void delete(final Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> { mCardDao.delete(card); });
    }

    void update(final Card card) {
        AppDatabase.databaseWriteExecutor.execute(() -> { mCardDao.update(card); });
    }

    void deleteCards(final Card... cards) {
        AppDatabase.databaseWriteExecutor.execute(() -> { mCardDao.deleteCards(cards); });
    }
}
