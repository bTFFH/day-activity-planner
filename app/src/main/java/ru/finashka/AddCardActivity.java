package ru.finashka;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.finashka.entity.Card;

public class AddCardActivity extends AppCompatActivity {

    private static SimpleDateFormat formatterFromString = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
    }

    public void addCard(View view) throws ParseException {
        TextView titleView = findViewById(R.id.title);
        String title = titleView.getText().toString();
        TextView detailsView = findViewById(R.id.details);
        String details = detailsView.getText().toString();
        TextView startDateView = findViewById(R.id.start_date);
        String startTime = startDateView.getText().toString();
        TextView endDateView = findViewById(R.id.end_date);
        String endTime = endDateView.getText().toString();
        Card card = new Card(title, details, formatterFromString.parse(startTime), formatterFromString.parse(endTime));
        Intent data = new Intent();
        data.putExtra("card", card);
        setResult(Activity.RESULT_OK, data);
        finish();

    }
}
