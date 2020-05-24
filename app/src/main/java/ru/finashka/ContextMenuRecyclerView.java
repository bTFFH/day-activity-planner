package ru.finashka;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ContextMenuRecyclerView extends RecyclerView {

    private RecyclerContextMenuInfo mContextMenuInfo;

    public ContextMenuRecyclerView(Context context) {
        super(context);
    }

    public ContextMenuRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContextMenuRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        final int longPressPosition = getChildAdapterPosition(originalView);
        if (longPressPosition >= 0) {
            mContextMenuInfo = new RecyclerContextMenuInfo(longPressPosition);
            return super.showContextMenuForChild(originalView);
        }
        return false;
    }

    public static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {
        public int position;

        public RecyclerContextMenuInfo(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

    }

}