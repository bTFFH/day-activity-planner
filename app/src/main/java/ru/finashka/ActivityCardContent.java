package ru.finashka;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class ActivityCardContent extends AppCompatTextView {
    private String title;

    public ActivityCardContent(Context context, @Nullable AttributeSet attrs, String title) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActivityCardContent);
        try {
            setTitle(title);
        } finally {
            typedArray.recycle();
        }
        setText(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
