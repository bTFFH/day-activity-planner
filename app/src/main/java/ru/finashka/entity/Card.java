package ru.finashka.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card implements Serializable {
    /* TODO: integrate data bindings after database complete integration according to https://www.androidauthority.com/data-binding-in-android-709747/ */

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
    private LocalDateTime startTime;

    @NonNull
    @ColumnInfo(name = "end_time")
    private LocalDateTime endTime;
}
