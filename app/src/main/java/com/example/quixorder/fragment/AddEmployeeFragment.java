package com.example.quixorder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.gson.Gson;

public class AddEmployeeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
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
            /**
            AccountService accountService = retrofit.create(AccountService.class);

            Account account = new Account(
                    spinner.getSelectedItem().toString(),
                    usernameInput.getText().toString(),
                    passwordInput.getText().toString());

            accountService.createAccount(account).enqueue(new Callback<Account>() {

                @Override
                public void onResponse(Call<Account> call, Response<Account> response)
                {
                    if (response.isSuccessful()) {
                        Account accountResponse = response.body();
                        String content = "";
                        content += "Code: " + response.code() + "\n";
                        content += "Type: " + accountResponse.getType() + "\n";
                        content += "Username: " + accountResponse.getId() + "\n";
                        content += "Password: " + accountResponse.getPassword() + "\n";

                        Log.e("onResponseSuccess.", content);
                        Toast.makeText(getActivity().getApplicationContext(), "Created Account", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("onResponseFail.", new Gson().toJson(response.errorBody()));
                        Toast.makeText(getActivity().getApplicationContext(), "Invalid Account", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    Log.e("onFailure", t.toString());
                }
            */
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
