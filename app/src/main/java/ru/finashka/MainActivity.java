package ru.finashka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.Serializable;
import java.util.List;

import lombok.SneakyThrows;
import ru.finashka.dao.CardDao;
import ru.finashka.entity.Card;
import ru.finashka.service.UserActivityService;

public class MainActivity extends AppCompatActivity {

    private UserActivityService userActivityService;
    private AppDatabase db;
    private CardViewModel mCardViewModel;
//    public static final int NEW_CARD_ACTIVITY_REQUEST_CODE = 1;

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userActivityService = new UserActivityService();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = AppDatabase.getDatabase(this);
        RecyclerView recyclerView = findViewById(R.id.activity_card_recycler_view);
        final CardListAdapter adapter = new CardListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        mCardViewModel.getAllCards().observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable final List<Card> cards) {
                adapter.setCards(cards);
            }
        });
    }

    @SneakyThrows
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Serializable sCard = data.getSerializableExtra("card");
            if (sCard != null) {
                mCardViewModel.insert((Card) sCard);
                Toast.makeText(
                        getApplicationContext(),
                        "Card was saved",
                        Toast.LENGTH_LONG).show();
            } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Card was not saved",
                    Toast.LENGTH_LONG).show();
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
