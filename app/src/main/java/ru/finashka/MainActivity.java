package ru.finashka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import lombok.SneakyThrows;
import ru.finashka.entity.Card;
import ru.finashka.service.UserActivityService;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserActivityService userActivityService;
    private AppDatabase db;

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

//    private void updateUserCards() throws ParseException {
//        LinearLayout myRoot = findViewById(R.id.activity_card_layout);
//        myRoot.removeAllViews();
//
//        List<Card> userCards = userActivityService.getUserCards();
//        for (Card userCard : userCards) {
//            ActivityCardView activityCardView = new ActivityCardView(this, userCard, null);
//            myRoot.addView(activityCardView);
//        }
//    }

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
}
