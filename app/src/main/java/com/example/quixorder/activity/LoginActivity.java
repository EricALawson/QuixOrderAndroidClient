package com.example.quixorder.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quixorder.R;
import com.example.quixorder.api.AccountService;
import com.example.quixorder.model.Account;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://quixorderserver.azurewebsites.net/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private EditText usernameInput;
    private EditText passwordInput;

    //Intent bManager = new Intent(LoginActivity.this, OwnerActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findViewById(R.id.btn_login).setOnClickListener(btn_loginOnClickListener);
        //findViewById(R.id.lOut).setOnClickListener(logOut);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);

    }

    private OnClickListener btn_loginOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            AccountService accountService = retrofit.create(AccountService.class);
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            accountService.getAccountByUsername(username).enqueue(new Callback<Account>() {

                @Override
                public void onResponse(Call<Account> call, Response<Account> response)
                {
                    if (response.isSuccessful()) {
                        Log.e("onResponseSuccess.", new Gson().toJson(response.body()));
                        String type = response.body().getType();

                        switch(type)
                        {
                            case "Owner":
                                //setContentView(R.layout.owner);
                                startActivity(new Intent(LoginActivity.this, OwnerActivity.class));
                                break;
                            case "Table":
                                //setContentView(R.layout.table);
                               startActivity(new Intent(LoginActivity.this, TableActivity.class));
                               break;
                            case "Server":
                                //setContentView(R.layout.server);
                                startActivity(new Intent(LoginActivity.this, ServerActivity.class));
                                break;
                                //finish();
                                //break;
                            case "Cook":
                                startActivity(new Intent(LoginActivity.this, CookActivity.class));
                                //startActivity(chef);
                        }
                    }
                    else {
                        Log.e("onResponseFail.", new Gson().toJson(response.errorBody()));
                        Toast.makeText(getApplicationContext(), "Invalid Account", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(LoginActivity.this, CookActivity.class));
                        //setContentView(R.layout.cook);
                        //findViewById(R.id.lOut);
                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    Log.e("onFailure", t.toString());
                }
            });
        }
    };


}
