package ru.finashka.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import ru.finashka.converter.TimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card implements Serializable {
    /* TODO: integrate data bindings after database complete integration according to https://www.androidauthority.com/data-binding-in-android-709747/ */

    public Card(String title, String details, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.details = details;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "details")
    private String details;

    @NonNull
    @ColumnInfo(name = "start_time")
    @TypeConverters({TimeConverter.class})
    private LocalDateTime startTime;

    @NonNull
    @ColumnInfo(name = "end_time")
    @TypeConverters({TimeConverter.class})
    private LocalDateTime endTime;
}
