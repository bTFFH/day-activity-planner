package ru.finashka;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.finashka.dao.CardDao;
import ru.finashka.entity.Card;

@Database(entities = {Card.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardDao cardDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "word_database"
                    ).addCallback(initDBCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback initDBCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                LocalDateTime basicTime = LocalDateTime.now();
                Card card1 = new Card("Title 1", "Description 1", basicTime, basicTime.plusHours(1));
                Card card2 = new Card("Title 2", "Go to the shop to find cat food", basicTime.plusHours(2), basicTime.plusHours(4));
                CardDao dao = INSTANCE.cardDao();
                dao.insert(card1);
                dao.insert(card2);
            });
        }
    };
}
