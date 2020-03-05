package ru.finashka;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeView extends AppCompatTextView {

    private static SimpleDateFormat formatterFromString = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss", Locale.getDefault());
    private static SimpleDateFormat formatterToString = new SimpleDateFormat("hh:mm", Locale.getDefault());

    private Date timeStart;
    private Date timeEnd;

    public TimeView(Context context, @Nullable AttributeSet attrs) throws ParseException {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) throws ParseException {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeView);
        try {
            String start = typedArray.getString(R.styleable.TimeView_timeStart);
            String end = typedArray.getString(R.styleable.TimeView_timeEnd);

            setTimeStart(start);
            setTimeEnd(end);
        } finally {
            typedArray.recycle();
        }
        setText(String.format("%s-%s",
                formatterToString.format(timeStart),
                formatterToString.format(timeEnd)));
    }

    private void setTimeStart(String string) throws ParseException {
        timeStart = formatterFromString.parse(string);
    }

    private void setTimeEnd(String string) throws ParseException {
        timeEnd = formatterFromString.parse(string);
    }

}
