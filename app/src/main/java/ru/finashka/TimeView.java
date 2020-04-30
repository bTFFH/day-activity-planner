package ru.finashka;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeView extends AppCompatTextView {

    private static DateTimeFormatter formatterToString = DateTimeFormatter.ofPattern("hh:mm");

    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    public TimeView(Context context, LocalDateTime start, LocalDateTime end, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, start, end);
    }

    private void init(Context context, AttributeSet attrs, LocalDateTime start, LocalDateTime end) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
        try {
            setTimeStart(start);
            setTimeEnd(end);
        } finally {
            typedArray.recycle();
        }
        setText(String.format("%s-%s",
                formatterToString.format(timeStart),
                formatterToString.format(timeEnd)));
    }

    private void setTimeStart(LocalDateTime date) {
        timeStart = date;
    }

    private void setTimeEnd(LocalDateTime date) {
        timeEnd = date;
    }
}
