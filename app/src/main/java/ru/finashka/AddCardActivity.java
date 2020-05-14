package ru.finashka;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;

import ru.finashka.entity.Card;

public class AddCardActivity extends AppCompatActivity {

    private SimpleDateFormat mFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());

    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();

    // установка обработчика выбора времени
    private TimePickerDialog.OnTimeSetListener startTimeListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startDate.set(Calendar.MINUTE, minute);
            Button btn = findViewById(R.id.start_date_btn);
            btn.setText(mFormatter.format(startDate.getTime()));
        }
    };

    // установка обработчика выбора даты
    private DatePickerDialog.OnDateSetListener startDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDate.set(Calendar.YEAR, year);
            startDate.set(Calendar.MONTH, monthOfYear);
            startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(AddCardActivity.this, R.style.DialogTheme, startTimeListener,
                    startDate.get(Calendar.HOUR), startDate.get(Calendar.MINUTE), true)
                    .show();
        }
    };

    // установка обработчика выбора времени
    private TimePickerDialog.OnTimeSetListener endTimeListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            startDate.set(Calendar.MINUTE, minute);
            Button btn = findViewById(R.id.end_date_btn);
            btn.setText(mFormatter.format(endDate.getTime()));
        }
    };

    // установка обработчика выбора даты
    private DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDate.set(Calendar.YEAR, year);
            endDate.set(Calendar.MONTH, monthOfYear);
            endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(AddCardActivity.this, R.style.DialogTheme, startTimeListener,
                    endDate.get(Calendar.HOUR), endDate.get(Calendar.MINUTE), true)
                    .show();
        }
    };


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
        LocalDateTime startDate = this.startDate.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDate = this.endDate.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Card card = new Card(null, title, details, startDate, endDate);
        Intent data = new Intent();
        data.putExtra("card", card);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    public void openStartTimeDialog(View view) {
        new DatePickerDialog(AddCardActivity.this, R.style.DialogTheme, startDateListener,
                startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }


    public void openEndTimeDialog(View view) {
        new DatePickerDialog(AddCardActivity.this, R.style.DialogTheme, endDateListener,
                endDate.get(Calendar.YEAR),
                endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DAY_OF_MONTH))
                .show();
    }
}

