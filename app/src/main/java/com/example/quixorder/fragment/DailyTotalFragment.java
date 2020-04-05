package com.example.quixorder.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.quixorder.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class DailyTotalFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();

    private View view;
    private Spinner frequencySpinner;
    private ArrayAdapter<CharSequence> frequencyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daily_total, container, false);

        // Set up views
        frequencySpinner = view.findViewById(R.id.frequencySpinner);

        // Populate views
        frequencyAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.frequencies, android.R.layout.simple_spinner_item);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencySpinner.setAdapter(frequencyAdapter);
        frequencySpinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
