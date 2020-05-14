package ru.finashka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import lombok.SneakyThrows;
import ru.finashka.entity.Card;
import ru.finashka.service.UserActivityService;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserActivityService userActivityService;
    private AppDatabase db;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.item_menu_list, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.edit_card_item:
                    // edit stuff here
                    return true;
                case R.id.delete_card_item:
                    userActivityService.removeCard(cardView.getCard());
                    updateUserCards();
                    // remove stuff here
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };


    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userActivityService = new UserActivityService();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // TODO: replace this with normal db creation (already in AppDatabase.java)
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "day-activity-planner").build();
//        updateUserCards();
        RecyclerView recyclerView = findViewById(R.id.activity_card_recycler_view);
        final CardListAdapter adapter = new CardListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateUserCards() {
        LinearLayout myRoot = findViewById(R.id.activity_card_layout);
        myRoot.removeAllViews();

        List<Card> userCards = userActivityService.getUserCards();
        for (Card userCard : userCards) {
            ActivityCardView activityCardView = new ActivityCardView(this, userCard, null);
            activityCardView.setOnLongClickListener(new View.OnLongClickListener() {
                // Called when the user long-clicks on someView
                public boolean onLongClick(View view) {
                    if (mActionMode != null) {
                        return false;
                    }

                    // Start the CAB using the ActionMode.Callback defined above
                    mActionMode = getActivity().startActionMode(mActionModeCallback);
                    view.setSelected(true);
                    return true;
                }
            });
            myRoot.addView(activityCardView);
        }
    }

    @SneakyThrows
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Serializable cardS = data.getSerializableExtra("card");
            if (cardS != null) {
                userActivityService.addUserCard((Card) cardS);
//                updateUserCards();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void openAddDialog(View view) {
        Intent intent = new Intent(this, AddCardActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v instanceof ActivityCardView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.item_menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ActivityCardView cardView = (ActivityCardView) info.targetView;
        switch (item.getItemId()) {
            case R.id.edit_card_item:
                // edit stuff here
                return true;
            case R.id.delete_card_item:
                userActivityService.removeCard(cardView.getCard());
                this.updateUserCards();
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
