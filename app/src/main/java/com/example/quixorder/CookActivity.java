package com.example.quixorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CookActivity extends AppCompatActivity {
    public Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://quixorderserver.azurewebsites.net/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private RecyclerView orderList;
    private RecyclerView.LayoutManager orderLayoutManger;
    private OrderListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook);

        findViewById(R.id.lOut).setOnClickListener(logOut);
        initializeOrderList();
    }

    private void initializeOrderList() {
        orderList = (RecyclerView) findViewById(R.id.orderList);
        orderList.setHasFixedSize(true);

        orderLayoutManger = new LinearLayoutManager(this);
        orderList.setLayoutManager(orderLayoutManger);


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