package com.example.quixorder.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

import com.example.quixorder.OrderListAdapter;
import com.example.quixorder.R;

public class CookActivity extends AppCompatActivity {
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
        orderList = (RecyclerView) findViewById(R.id.orderItems);
        orderList.setHasFixedSize(true);

        orderLayoutManger = new LinearLayoutManager(this);
        orderList.setLayoutManager(orderLayoutManger);

    }


    private OnClickListener logOut = new OnClickListener() {

        @Override
        public void onClick(View view)
        {
            startActivity(new Intent(CookActivity.this, LoginActivity.class));
        }
    };

}