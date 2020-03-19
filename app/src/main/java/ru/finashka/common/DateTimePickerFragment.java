package ru.finashka.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DateTimePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private final FragmentManager fragmentManager;
    @Getter
    private Calendar selectedDate = Calendar.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        selectedDate.set(year, month, dayOfMonth);
        final TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(fragmentManager, "timePicker");
        timePickerFragment.onDismiss(new DialogInterface() {
            @Override
            public void cancel() {
            }

            @Override
            public void dismiss() {
                Calendar time = timePickerFragment.getSelectedTime();
                selectedDate.set(Calendar.HOUR, time.get(Calendar.HOUR));
                selectedDate.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
            }
        });
    }
}
