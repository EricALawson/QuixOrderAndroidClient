package com.example.quixorder.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.quixorder.ServerTaskViewModel;
import com.example.quixorder.R;
import com.example.quixorder.adapter.server.IServerTask;
import com.example.quixorder.adapter.server.ServerOrderListAdapter;
import com.example.quixorder.model.Order;
import com.example.quixorder.model.TableCall;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ServerActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private ServerTaskViewModel viewModel;
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private String account_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);
        account_name = getIntent().getStringExtra("username");

        findViewById(R.id.lOut).setOnClickListener(logOut);
        initializeOrderList();
    }

    private void initializeOrderList() {
        orderList = findViewById(R.id.orderItems);
        orderList.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(ServerTaskViewModel.class);
        viewModel.setOrderQuery( firestore.collection("orders").whereEqualTo("server", account_name).whereEqualTo("status", "ready to serve") );
        viewModel.setCallQuery(firestore.collection("table_calls").whereEqualTo("server", account_name).whereEqualTo("status", "server called"));
        LiveData<List<IServerTask>> liveData = viewModel.getLiveData();
        liveData.observe(this, (taskList)-> {
            orderList.setLayoutManager(new LinearLayoutManager(this));
            orderList.setAdapter(new ServerOrderListAdapter(taskList));
        });
    }

    private View.OnClickListener logOut = view -> startActivity(new Intent(ServerActivity.this, LoginActivity.class));
}
