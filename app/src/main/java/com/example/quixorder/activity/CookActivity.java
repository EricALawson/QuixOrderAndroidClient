package com.example.quixorder.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

import com.example.quixorder.R;

public class CookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook);

        findViewById(R.id.lOut).setOnClickListener(logOut);

    }


    private OnClickListener logOut = new OnClickListener() {

        @Override
        public void onClick(View view)
        {
            //setContentView(R.layout.login);
            //Intent main = new Intent(CookActivity.this, LoginActivity.class);
            //getLogout();
            startActivity(new Intent(CookActivity.this, LoginActivity.class));
            //finish();


        }
    };

    /*private void getLogout()
    {

        String p = "1234567";
        AlertDialog.Builder sure = new AlertDialog.Builder(CookActivity.this);
        final EditText pass = new EditText(CookActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pass.setLayoutParams(lp);
        sure.setView(pass);

        sure.setTitle("Logout?");
        sure.setMessage("Enter Password");

        sure.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(pass.getText().toString().toUpperCase().equals(p.toLowerCase()))
                {
                    startActivity(new Intent(CookActivity.this, LoginActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        sure.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        sure.show();

    }*/

}