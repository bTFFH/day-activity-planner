package ru.finashka;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
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
        this.updateCards();
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
        registerForContextMenu(activityCardRV);

        mCardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        this.updateCards();
    }

    @SneakyThrows
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Serializable sCard = data.getSerializableExtra("card");
            if (sCard != null) {
                Card card = (Card) sCard;
                if (card.getId() == null) {
                    mCardViewModel.insert(card);
                } else {
                    mCardViewModel.update(card);
                }
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
        Intent intent = new Intent(this, AddEditCardActivity.class);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContextMenuRecyclerView.RecyclerContextMenuInfo info = (ContextMenuRecyclerView.RecyclerContextMenuInfo) item.getMenuInfo();
        Card tappedCard = mCardListAdapter.getCards().get(info.getPosition());
        switch (item.getItemId()) {
            case R.id.edit_card_item:
                // edit stuff here
                Intent intent = new Intent(this, AddEditCardActivity.class);
                intent.putExtra("card", tappedCard);
                startActivityForResult(intent, 0);
                return true;
            case R.id.delete_card_item:
                // remove stuff here
                mCardViewModel.remove(tappedCard);
                this.updateCards();
                Toast.makeText(
                        getApplicationContext(),
                        R.string.card_removed,
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateCards() {
        mCardViewModel.getCardsByDate(currentDate).observe(this, cards -> mCardListAdapter.setCards(cards));
    }
}
