package com.example.quixorder.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.quixorder.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DailyTotalFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();

    private View view;
    private Spinner frequencySpinner;
    private ArrayAdapter<CharSequence> frequencyAdapter;
    private Button datePicker;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private Calendar calendar;
    int year, month, day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daily_total, container, false);

        // Set up views
        frequencySpinner = view.findViewById(R.id.frequencySpinner);
        datePicker = view.findViewById(R.id.datePickerButton);

        // Create frequency picker
        frequencyAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.frequencies, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);
        frequencySpinner.setOnItemSelectedListener(this);

        // Create date range picker
        calendar = Calendar.getInstance();
        datePicker.setOnClickListener(this);

        return view;
    }

    public void onDatePickerClick() {
        year = calendar.get(Calendar.YEAR);;
        month = calendar.get(Calendar.MONTH);;
        day = calendar.get(Calendar.DAY_OF_MONTH);;

        // Set date picker to currently selected date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                this,
                year, month, day
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        Log.d("onDateSet", currentDateString);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.datePickerButton:
                Log.d("onDatePickerClick", "click");
                onDatePickerClick();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
