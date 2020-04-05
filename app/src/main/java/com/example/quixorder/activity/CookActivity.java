package com.example.quixorder.activity;

import androidx.lifecycle.LiveData;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import androidx.lifecycle.ViewModelProviders;

import com.example.quixorder.adapter.OrderListAdapter;
import com.example.quixorder.OrderListViewModel;
import com.example.quixorder.R;
import com.example.quixorder.model.Order;

import java.util.List;

public class CookActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private OrderListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook);

        findViewById(R.id.lOut).setOnClickListener(logOut);
        initializeOrderList();
    }

    private void initializeOrderList() {
        orderList = findViewById(R.id.orderItems);
        orderList.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);
        LiveData<List<Order>> liveData = viewModel.getOrderLiveData();
        liveData.observe(this, (List<Order> orders)-> {
            orderList.setLayoutManager(new LinearLayoutManager(this));
            orderList.setAdapter(new OrderListAdapter(orders));

        });
    }


    private OnClickListener logOut = new OnClickListener() {

        @Override
        public void onClick(View view)
        {
            startActivity(new Intent(CookActivity.this, LoginActivity.class));
        }
    };

}