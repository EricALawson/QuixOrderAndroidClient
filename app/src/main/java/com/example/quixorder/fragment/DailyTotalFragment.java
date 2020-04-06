package com.example.quixorder.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.quixorder.model.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DailyTotalFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    // Declare firestore variables
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private CollectionReference orders = firebase.collection("orders");

    // Declare views
    private View view;

    // Declare filter views
    private Spinner frequencySpinner;
    private ArrayAdapter<CharSequence> frequencyAdapter;
    private Button datePicker;
    private ListenerRegistration ordersListener;

    // Declare calendar variables
    private Calendar startCalendar;
    int startYear, startMonth, startDay;

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
        startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        datePicker.setOnClickListener(this);

        return view;
    }

    public void loadOrders(QuerySnapshot task) {
        // Get Orders list
        ArrayList<Order> orderList = new ArrayList<>();
        for (DocumentSnapshot order: task.getDocuments()) {
            Log.d("QuerySuccess", order.toString());
        }
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

    public void onDatePickerClick() {
        startYear = startCalendar.get(Calendar.YEAR);;
        startMonth = startCalendar.get(Calendar.MONTH);;
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);;

        // Set date picker to currently selected date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                this,
                startYear, startMonth, startDay
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Set calendar to selected date
        startCalendar.set(Calendar.YEAR, year);
        startCalendar.set(Calendar.MONTH, month);
        startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String startDateString = DateFormat.getDateInstance(DateFormat.FULL).format(startCalendar.getTime());
        Log.d("test", "" + startCalendar.getTimeInMillis() / 1000);
        Log.d("onDateSet", "startDate:" + startDateString);

        // Get current date
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.HOUR_OF_DAY, 23);
        currentCalendar.set(Calendar.MINUTE, 59);
        currentCalendar.set(Calendar.SECOND, 59);
        currentCalendar.set(Calendar.MILLISECOND, 999);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(currentCalendar.getTime());
        Log.d("onDateSet", "currentDate:" + currentDateString);

        // Remove orders listener
        if (ordersListener != null) {
            ordersListener.remove();
        }

        // Update orders snapshot listener
        Query ordersQuery = orders
                .whereGreaterThanOrEqualTo("servedTime", startCalendar.getTime())
                .whereLessThanOrEqualTo("servedTime", currentCalendar.getTime());
        ordersListener = ordersQuery.addSnapshotListener(getActivity(), (query, error) -> {
           if (error != null) {
               Log.e("QueryFailed", error.getMessage());
               return;
           }

           loadOrders(query);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
