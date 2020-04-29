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
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.model.Payment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyTotalFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    // Declare firestore variables
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private CollectionReference payments = firebase.collection("Payment");

    // Declare views
    private View view;

    // Declare filter variables
    private Spinner frequencySpinner;
    private ArrayAdapter<CharSequence> frequencyAdapter;
    private Button datePicker;
    private ListenerRegistration paymentListener;

    // Declare graph variables
    private BarChart graphView;
    private TextView xAxisTitle;

    // Declare other views
    private TextView textView1;

    // Declare calendar variables
    private Calendar startCalendar;
    private int startYear, startMonth, startWeek, startDay;

    // Declare payments
    private List<Payment> paymentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daily_total, container, false);

        // Set up views
        frequencySpinner = view.findViewById(R.id.frequencySpinner);
        datePicker = view.findViewById(R.id.datePickerButton);
        graphView = view.findViewById(R.id.graphView);
        xAxisTitle = view.findViewById(R.id.xAxisTitle);
        textView1 = view.findViewById(R.id.textView1);

        // Create frequency picker
        frequencyAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.frequencies, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);
        frequencySpinner.setOnItemSelectedListener(this);

        // Create date range picker
        startCalendar = Calendar.getInstance();
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH);
        startWeek = startCalendar.get(Calendar.WEEK_OF_YEAR);
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        datePicker.setOnClickListener(this);

        // Set graph view settings
        graphView.setExtraBottomOffset(10);

        // xAxis settings
        XAxis xAxis = graphView.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(20);

        // yAxis settings
        YAxis yAxisLeft = graphView.getAxisLeft();
        yAxisLeft.setStartAtZero(true);
        yAxisLeft.setTextSize(20);

        // Disable settings
        YAxis yAxisRight = graphView.getAxisRight();
        yAxisRight.setEnabled(false);
        Legend legend = graphView.getLegend();
        legend.setEnabled(false);
        Description description = graphView.getDescription();
        description.setEnabled(false);

        return view;
    }

    public void loadPayments(QuerySnapshot task) {
        // Get Payments list
        paymentList = new ArrayList<>();
        for (DocumentSnapshot payment: task.getDocuments()) {
            Log.d("QuerySuccess", payment.toString());
            paymentList.add(payment.toObject(Payment.class));
        }

        // Set up view of all payments
        double total = sumTotal();
        Log.d("loadTotal", "" + total);
        textView1.setText(String.format("%.2f", total));
    }

    public void loadGraph() {
        String frequency = frequencySpinner.getSelectedItem().toString();
        Log.d("loadPaymentsGraph", "selectedFrequency:" + frequency);

        // Check if payment list created
        if (paymentList == null) {
            Log.d("loadPaymentsGraph", "no data loaded");
            return;
        }
        Log.d("loadPaymentsGraph", "paymentListSize:" + paymentList.size());

        // Load data
        graphView.setData(getData(frequency));
        graphView.invalidate();
    }

    public BarData getData(String frequency) {

        List<BarEntry> totals = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(this.startCalendar.getTime());

        float x = 0;
        float total = 0;

        switch(frequency) {
            case "Daily":
                for (Payment payment: paymentList) {
                    Calendar currentCalendar = payment.getCalendar();
                    while (startCalendar.get(Calendar.DAY_OF_YEAR) != currentCalendar.get(Calendar.DAY_OF_YEAR)) {
                        // Update day label and its total payment
                        totals.add(new BarEntry(x, total));
                        labels.add(DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(startCalendar.getTime()));

                        // Refresh counters for remaining days
                        x += 1;
                        total = 0;
                        startCalendar.add(Calendar.DAY_OF_YEAR, 1);
                    }
                    // Add to total when matching day
                    total += payment.getTotal();
                }

                // Update last entry
                totals.add(new BarEntry(x, total));
                labels.add(DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(startCalendar.getTime()));
                xAxisTitle.setText("Date (M/D/YY)");
                break;

            case "Weekly":
                startCalendar.add(Calendar.DAY_OF_MONTH, Calendar.MONDAY - startCalendar.get(Calendar.DAY_OF_WEEK));
                for (Payment payment: paymentList) {
                    Calendar currentCalendar = payment.getCalendar();
                    while (startCalendar.get(Calendar.WEEK_OF_YEAR) != currentCalendar.get(Calendar.WEEK_OF_YEAR)) {
                        // Update day label and its total payment
                        totals.add(new BarEntry(x, total));
                        labels.add(DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(startCalendar.getTime()));

                        // Refresh counters for remaining days
                        x += 1;
                        total = 0;
                        startCalendar.add(Calendar.WEEK_OF_YEAR, 1);
                    }
                    // Add to total when matching day
                    total += payment.getTotal();
                }

                // Update last entry
                totals.add(new BarEntry(x, total));
                labels.add(DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(startCalendar.getTime()));
                xAxisTitle.setText("Date (M/D/YY)");
                break;

            case "Monthly":
                for (Payment payment: paymentList) {
                    Calendar currentCalendar = payment.getCalendar();
                    while (startCalendar.get(Calendar.MONTH) != currentCalendar.get(Calendar.MONTH)) {
                        // Update day label and its total payment
                        totals.add(new BarEntry(x, total));
                        labels.add("" + (startCalendar.get(Calendar.MONTH) + 1) + "/" + startCalendar.get(Calendar.YEAR));

                        // Refresh counters for remaining days
                        x += 1;
                        total = 0;
                        startCalendar.add(Calendar.MONTH, 1);
                    }
                    // Add to total when matching day
                    total += payment.getTotal();
                }

                // Update last entry
                totals.add(new BarEntry(x, total));
                labels.add("" + (startCalendar.get(Calendar.MONTH) + 1) + "/" + startCalendar.get(Calendar.YEAR));
                xAxisTitle.setText("Date (M/YYYY)");
                break;

            case "Yearly":
                for (Payment payment: paymentList) {
                    Calendar currentCalendar = payment.getCalendar();
                    while (startCalendar.get(Calendar.YEAR) != currentCalendar.get(Calendar.YEAR)) {
                        // Update day label and its total payment
                        totals.add(new BarEntry(x, total));
                        labels.add("" + startCalendar.get(Calendar.YEAR));

                        // Refresh counters for remaining days
                        x += 1;
                        total = 0;
                        startCalendar.add(Calendar.YEAR, 1);
                    }
                    // Add to total when matching day
                    total += payment.getTotal();
                }

                // Update last entry
                totals.add(new BarEntry(x, total));
                labels.add("" + startCalendar.get(Calendar.YEAR));
                xAxisTitle.setText("Date (YYYY)");
                break;
        }

        // Formatter
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value > labels.size() - 1) {
                    return "";
                }
                return labels.get((int) value);
            }

            @Override
            public String getFormattedValue(float value) {
                DecimalFormat format = new DecimalFormat("$#.00");
                if (value == 0) {
                    return "";
                }
                return format.format(value);
            }
        };

        graphView.getXAxis().setValueFormatter(formatter);

        // Return data
        BarDataSet dataset = new BarDataSet(totals, "BarDataSet");
        dataset.setColor(R.color.purple);
        BarData data = new BarData(dataset);
        data.setValueTextSize(20);
        data.setValueFormatter(formatter);

        return data;
    }

    public double sumTotal() {
        double total = 0;
        for (Payment payment: paymentList) {
            total += payment.getTotal();
        }

        return total;
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
        startYear = startCalendar.get(Calendar.YEAR);
        startMonth = startCalendar.get(Calendar.MONTH);
        startWeek = startCalendar.get(Calendar.WEEK_OF_YEAR);
        startDay = startCalendar.get(Calendar.DAY_OF_MONTH);
        String startDateString = DateFormat.getDateInstance(DateFormat.FULL).format(startCalendar.getTime());
        Log.d("onDateSet", "startDate:" + startDateString);

        // Get current date
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.HOUR_OF_DAY, 23);
        currentCalendar.set(Calendar.MINUTE, 59);
        currentCalendar.set(Calendar.SECOND, 59);
        currentCalendar.set(Calendar.MILLISECOND, 999);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(currentCalendar.getTime());
        Log.d("onDateSet", "currentDate:" + currentDateString);

        // Remove payment listener
        if (paymentListener != null) {
            paymentListener.remove();
        }

        // Update payment snapshot listener
        Query paymentsQuery = payments
                .whereGreaterThanOrEqualTo("receivedTime", startCalendar.getTime())
                .whereLessThanOrEqualTo("receivedTime", currentCalendar.getTime())
                .orderBy("receivedTime", Query.Direction.ASCENDING);
        paymentListener = paymentsQuery.addSnapshotListener(getActivity(), (query, error) -> {
           if (error != null) {
               Log.e("QueryFailed", error.getMessage());
               return;
           }

           loadPayments(query);
           loadGraph();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemSelected", frequencySpinner.getSelectedItem().toString());
        loadGraph();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
