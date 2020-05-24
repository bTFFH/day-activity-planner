package ru.finashka.converter;

import androidx.room.TypeConverter;

import java.time.LocalDate;
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

    @TypeConverter
    public String fromDate(LocalDate date) {
        return date.toString();
    }

    @TypeConverter
    public LocalDate toDate(String date) {
        return LocalDate.parse(date);
    }
}
