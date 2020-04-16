package ru.finashka;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ru.finashka.common.DateTimePickerFragment;
import ru.finashka.entity.Card;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddCardActivity extends AppCompatActivity {

    private static SimpleDateFormat formatterFromString = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
    private SimpleDateFormat mFormatter = new SimpleDateFormat("hh:mm dd/MM/yyyy", Locale.getDefault());

    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        Button btn = findViewById(R.id.start_date_btn);
        btn.setText(mFormatter.format(startDate.getTime()));
        btn = findViewById(R.id.end_date_btn);
        btn.setText(mFormatter.format(endDate.getTime()));
    }

    public void addCard(View view) {
        TextView titleView = findViewById(R.id.title);
        String title = titleView.getText().toString();
        TextView detailsView = findViewById(R.id.details);
        String details = detailsView.getText().toString();
        Card card = new Card(title, details, startDate.getTime(), endDate.getTime());
        Intent data = new Intent();
        data.putExtra("card", card);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    public void openStartTimeDialog(View view) {
        final DateTimePickerFragment dateTimePicker = new DateTimePickerFragment(getSupportFragmentManager());
        dateTimePicker.show(getSupportFragmentManager(), "dateTimePicker");
        dateTimePicker.onDismiss(new DialogInterface() {
            @Override
            public void cancel() {
            }

            @Override
            public void dismiss() {
                startDate = dateTimePicker.getSelectedDate();
                Button btn = findViewById(R.id.start_date_btn);
                btn.setText(mFormatter.format(startDate.getTime()));
            }
        });
    }


    public void openEndTimeDialog(View view) {

    }
}

