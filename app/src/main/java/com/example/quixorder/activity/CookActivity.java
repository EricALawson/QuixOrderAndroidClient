package com.example.quixorder.activity;

import androidx.lifecycle.LiveData;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View.OnClickListener;
import android.content.Intent;
import androidx.lifecycle.ViewModelProviders;

import com.example.quixorder.OrderListViewModel;
import com.example.quixorder.adapter.cook.OrderListAdapter;
import com.example.quixorder.R;
import com.example.quixorder.model.Order;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CookActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private OrderListViewModel viewModel;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();


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
        viewModel.setQuery( firestore.collection("orders").whereEqualTo("status", "cooking") );
        LiveData<List<Order>> liveData = viewModel.getOrderLiveData();
        liveData.observe(this, (List<Order> orders)-> {
            orderList.setLayoutManager(new LinearLayoutManager(this));
            orderList.setAdapter(new OrderListAdapter(orders));

        });
    }


    private OnClickListener logOut = view -> startActivity(new Intent(CookActivity.this, LoginActivity.class));

}