package ru.finashka.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.time.LocalDateTime;
import java.util.List;

import ru.finashka.converter.TimeConverter;
import ru.finashka.entity.Card;

@Dao
public interface CardDao {
    @Query("SELECT * FROM card")
    LiveData<List<Card>> getAll();

    @Query("SELECT * FROM card WHERE title LIKE :title")
    Card getByTitle(String title);

    @Query("SELECT * FROM card WHERE start_time > :start and start_time < :end ORDER BY start_time")
    @TypeConverters({TimeConverter.class})
    LiveData<List<Card>> getAllByStartTimeBounds(LocalDateTime start, LocalDateTime end);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Card card);

    @Delete
    void delete(Card card);

    @Update
    void update(Card card);

    @Delete
    void deleteCards(Card... cards);
}
