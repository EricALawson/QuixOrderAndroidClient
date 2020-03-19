package com.example.quixorder.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quixorder.R;
import com.example.quixorder.model.Account;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddEmployeeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();

    private View view;
    private EditText usernameInput;
    private EditText passwordInput;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_employee, container, false);

        view.findViewById(R.id.btn_sign_up).setOnClickListener(btn_sign_upOnClickListener);
        usernameInput = view.findViewById(R.id.username);
        passwordInput = view.findViewById(R.id.password);
        spinner = view.findViewById(R.id.spinner_type);

        adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    private OnClickListener btn_sign_upOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CollectionReference accounts = firebase.collection("accounts");

            // Create Account object with text field values
            Account account = new Account(
                    spinner.getSelectedItem().toString(),
                    usernameInput.getText().toString(),
                    passwordInput.getText().toString()
            );

            // Query accounts collection for a username
            Log.e("QueryAccounts", account.getUsername());
            accounts.whereEqualTo("username", account.getUsername())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.e("QueryFailed", task.getException().getMessage());
                        } else if (task.getResult().size() != 0) {
                            Toast.makeText(getActivity(), "username already exists", Toast.LENGTH_SHORT).show();
                        } else {

                            // Add account to accounts collection
                            Log.e("AddAccount", account.getUsername());
                            accounts.add(account)
                                    .addOnFailureListener(output -> {
                                        Log.e("AddFailed", output.getMessage());
                                    })
                                    .addOnSuccessListener(output -> {
                                        Toast.makeText(getActivity(), "account created", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    });
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
