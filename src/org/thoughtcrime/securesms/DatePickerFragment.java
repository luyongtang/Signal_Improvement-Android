package org.thoughtcrime.securesms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.zip.Inflater;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public DatePickerFragment(){

    }

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
        }
    }
}
