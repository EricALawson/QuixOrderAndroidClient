package com.example.quixorder.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import androidx.lifecycle.ViewModelProviders;

import com.example.quixorder.OrderListAdapter;
import com.example.quixorder.OrderListViewModel;
import com.example.quixorder.R;
import com.example.quixorder.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CookActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private RecyclerView.LayoutManager orderLayoutManger;
    private OrderListAdapter adapter;
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

        orderLayoutManger = new LinearLayoutManager(this);
        orderList.setLayoutManager(orderLayoutManger);
        adapter = new OrderListAdapter();
        viewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);
        LiveData<List<Order>> liveData = viewModel.getOrderLiveData();
        adapter.setOrderList(liveData.getValue());
        liveData.observe(this, (List<Order> orders)-> {
            adapter.setOrderList(orders);
        });
        orderList.setAdapter(adapter);
    }


    private OnClickListener logOut = new OnClickListener() {

        @Override
        public void onClick(View view)
        {
            startActivity(new Intent(CookActivity.this, LoginActivity.class));
        }
    };

}