package com.example.quixorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);

        findViewById(R.id.lOut).setOnClickListener(logOut);
    }

    private View.OnClickListener logOut = new View.OnClickListener() {

        @Override
        public void onClick(View view)
        {
            //setContentView(R.layout.login);
            //Intent main = new Intent(CookActivity.this, LoginActivity.class);
            //getLogout();
            startActivity(new Intent(ServerActivity.this, LoginActivity.class));
            //finish();


        }
    };
}
