package ru.finashka.converter;

import androidx.room.TypeConverter;

import java.time.LocalDateTime;

public class TimeConverter {

    @TypeConverter
    public String fromTime(LocalDateTime time) {
        return time.toString();
    }

    @TypeConverter
    public LocalDateTime toTime(String stringTime) {
        return LocalDateTime.parse(stringTime);
    }
}
