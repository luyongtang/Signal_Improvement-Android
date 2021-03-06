package org.thoughtcrime.securesms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    public static int DATE_YEAR;
    public static int DATE_MONTH;
    public static int DATE_DAY;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        final Calendar c = Calendar.getInstance();

        Calendar chosenDate = Calendar.getInstance();
        chosenDate.set(year, month, day);
        if (c.after(chosenDate)){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Date must be in the future");
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            TextView textview = getActivity().findViewById(R.id.dateDisplay);
            textview.setText(day + "-" + month + "-" + year);
            DATE_DAY= day;
            DATE_MONTH= month;
            DATE_YEAR= year;
        }
    }
}
