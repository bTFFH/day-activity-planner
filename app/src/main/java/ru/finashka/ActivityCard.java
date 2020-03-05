package ru.finashka;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ActivityCard extends LinearLayout {
    private TextView title;
    private TimeView timeView;

    public ActivityCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
