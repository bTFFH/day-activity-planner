package ru.finashka.converters;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class TimeConverter {

    @TypeConverter
    public String fromTime(LocalDateTime time) {
        return time.toString();
    }

    @TypeConverter
    public LocalDateTime toTime(String stringTime) {
        LocalDateTime time = LocalDateTime.parse(stringTime);
        return time;
    }
}
