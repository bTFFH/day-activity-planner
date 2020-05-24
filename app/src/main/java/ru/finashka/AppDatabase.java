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
                            AppDatabase.class, "day_activity_planner"
                    ).addCallback(initDBCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback initDBCallback = new RoomDatabase.Callback() {
        /**
         * Basic examples for user, creating only first time app runs.
         */
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                LocalDateTime basicTime = LocalDateTime.now();
                Card card1 = new Card("Order marshmallows for cacao", "Search the cheapest variant for home usage", basicTime, basicTime.plusMinutes(30));
                Card card2 = new Card("Purchase food for cat", "Go to the shop near the station to find cat food", basicTime.plusHours(1), basicTime.plusHours(1).plusMinutes(15));
                CardDao dao = INSTANCE.cardDao();
                dao.insert(card1);
                dao.insert(card2);
            });
        }
    };
}
