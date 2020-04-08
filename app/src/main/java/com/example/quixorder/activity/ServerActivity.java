package com.example.quixorder.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.quixorder.OrderListViewModel;
import com.example.quixorder.R;
import com.example.quixorder.adapter.ServerOrderListAdapter;
import com.example.quixorder.model.Order;

import java.util.List;

public class ServerActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private OrderListViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);

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
            orderList.setAdapter(new ServerOrderListAdapter(orders));

        });
    }

    private View.OnClickListener logOut = view -> startActivity(new Intent(ServerActivity.this, LoginActivity.class));
}
