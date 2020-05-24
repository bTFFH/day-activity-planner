package ru.finashka;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import lombok.SneakyThrows;
import ru.finashka.entity.Card;

public class MainActivity extends AppCompatActivity {

    private CardViewModel mCardViewModel;
    private CardListAdapter mCardListAdapter;

    private SimpleDateFormat mFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    private Calendar currentDate = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener currentDateListener = (view, year, monthOfYear, dayOfMonth) -> {
        currentDate.set(Calendar.YEAR, year);
        currentDate.set(Calendar.MONTH, monthOfYear);
        currentDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Button currentDateButton = findViewById(R.id.current_date_button);
        currentDateButton.setText(mFormatter.format(currentDate.getTime()));

        mCardViewModel.getCardsByDate(currentDate).observe(this, cards -> mCardListAdapter.setCards(cards));
    };

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button currentDateButton = findViewById(R.id.current_date_button);
        currentDateButton.setText(mFormatter.format(currentDate.getTime()));

        RecyclerView activityCardRV = findViewById(R.id.activity_card_rv);
        activityCardRV.setLayoutManager(new LinearLayoutManager(this));

        mCardListAdapter = new CardListAdapter(this);
        activityCardRV.setAdapter(mCardListAdapter);

        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        mCardViewModel.getCardsByDate(currentDate).observe(this, cards -> mCardListAdapter.setCards(cards));
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
                        R.string.card_saved,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        R.string.card_saved_error,
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

    public void openCurrentDateDialog(View view) {
        new DatePickerDialog(MainActivity.this, R.style.DialogTheme, currentDateListener,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH))
                .show();
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
