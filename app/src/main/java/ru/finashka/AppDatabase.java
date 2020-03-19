package ru.finashka;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.finashka.dao.CardDao;
import ru.finashka.entity.Card;

@Database(entities = {Card.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardDao cardDao();
}
