package ru.finashka;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.text.ParseException;

import ru.finashka.entity.Card;

public class ActivityCardView extends LinearLayout {

    private Card card;
    private TimeView timeView;
    private ActivityCardContent cardContent;

    public ActivityCardView(Context context, Card card, @Nullable AttributeSet attrs) throws ParseException {
        super(context, attrs);
        this.card = card;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActivityCardView);
        try {
            timeView = new TimeView(context, card.getStartTime(), card.getEndTime(), attrs);
            setLayoutParams(getLayoutParams(context, attrs));
            cardContent = new ActivityCardContent(context, attrs, card.getTitle());
            addView(timeView);
            addView(cardContent);
        } finally {
            typedArray.recycle();
        }
    }

    private LayoutParams getLayoutParams(Context context, AttributeSet attrs) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 50);
        layoutParams.setMargins(15, 15, 5, 5);
        return layoutParams;
    }

}
