package ru.finashka;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeView extends AppCompatTextView {

    private static SimpleDateFormat formatterToString = new SimpleDateFormat("hh:mm", Locale.getDefault());

    private Date timeStart;
    private Date timeEnd;

    public TimeView(Context context, Date start, Date end, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, start, end);
    }

    private void init(Context context, AttributeSet attrs, Date start, Date end) {
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

    private void setTimeStart(Date date) {
        timeStart = date;
    }

    private void setTimeEnd(Date date) {
        timeEnd = date;
    }
}
