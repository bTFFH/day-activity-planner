package ru.finashka.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card implements Serializable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "details")
    public String details;

    @ColumnInfo(name = "start_time")
    public Date startTime;

    @ColumnInfo(name = "end_time")
    public Date endTime;
}
