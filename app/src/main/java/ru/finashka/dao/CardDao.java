package ru.finashka.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.finashka.entity.Card;

@Dao
public interface CardDao {
    @Query("SELECT * FROM card")
    List<Card> getAll();

    @Query("SELECT * FROM card WHERE title LIKE :title")
    Card getByTitle(String title);

    @Insert
    void insert(Card card);

    @Delete
    void delete(Card card);
}
