/*
package ru.finashka;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class ActivityCard extends AppCompatTextView {
    private TimeView timeView;
    private ActivityCardContent cardContent;

    public ActivityCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActivityCard);
        try {
            ActivityCardContent content = typedArray.get(R.styleable.ActivityCard_content);

            setCardContent(content);
        } finally {
            typedArray.recycle();
        }
        setText(title);
    }

    public void setCardContent(ActivityCardContent cardContent) {
        this.cardContent = cardContent;
    }

    public ActivityCardContent getCardContent() {
        return cardContent;
    }
}
*/
