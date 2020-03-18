package com.example.quixorder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quixorder.R;
import com.example.quixorder.adapter.ServerAdapter;
import com.example.quixorder.adapter.TableAdapter;
import com.example.quixorder.model.Account;
import com.example.quixorder.model.Table;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class AssignTableFragment extends Fragment {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private CollectionReference accounts = firebase.collection("accounts");
    private Query serverAccounts = accounts.whereEqualTo("type", "Server");
    private Query tableAccountsNull = accounts.whereEqualTo("server", null);

    private View view;
    private RecyclerView serverView;
    private RecyclerView.Adapter serverAdapter;
    private RecyclerView.LayoutManager serverLayoutManager;

    private RecyclerView tableViewNull;
    private RecyclerView.Adapter tableAdapterNull;
    private RecyclerView.LayoutManager tableLayoutManagerNull;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assign_table, container, false);

        loadServers();
        loadTables();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Live update serverAccounts
        serverAccounts.addSnapshotListener(getActivity(), (query, error) -> {

            // Check for errors
            if (error != null) {
                Log.e("QueryFailed", error.getMessage());
                return;
            }

            // Load servers
            ArrayList<Account> serverList = new ArrayList<>();
            for (DocumentSnapshot server : query.getDocuments()) {
                Log.d("QuerySuccess", server.toString());
                serverList.add(server.toObject(Account.class));
            }

            // Set up view of all servers
            serverView = view.findViewById(R.id.serversView);
            serverView.setHasFixedSize(true);
            serverLayoutManager = new GridLayoutManager(getContext(), 3);
            serverAdapter = new ServerAdapter(serverList);
            serverView.setLayoutManager(serverLayoutManager);
            serverView.setAdapter(serverAdapter);

        });

        // Live update tableAccounts
        tableAccountsNull.addSnapshotListener(getActivity(), (query, error) -> {

            // Check for errors
            if (error != null) {
                Log.e("QueryFailed", error.getMessage());
                return;
            }

            // Load tables
            ArrayList<Table> tableList = new ArrayList<>();
            for (DocumentSnapshot table : query.getDocuments()) {
                Log.d("QuerySuccess", table.toString());
                tableList.add(table.toObject(Table.class));
            }

            // Set up view of all tables
            tableViewNull = view.findViewById(R.id.tablesView);
            tableViewNull.setHasFixedSize(true);
            tableLayoutManagerNull = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
            tableAdapterNull = new TableAdapter(tableList);
            tableViewNull.setLayoutManager(tableLayoutManagerNull);
            tableViewNull.setAdapter(tableAdapterNull);
        });
    }

    public void loadServers() {
        // Load Servers
        serverAccounts.get()
                // Handle success
                .addOnSuccessListener(task -> {
                    ArrayList<Account> serverList = new ArrayList<>();
                    for (DocumentSnapshot server : task.getDocuments()) {
                        Log.d("QuerySuccess", server.toString());
                        serverList.add(server.toObject(Account.class));
                    }

                    // Set up view of all servers
                    serverView = view.findViewById(R.id.serversView);
                    serverView.setHasFixedSize(true);
                    serverLayoutManager = new GridLayoutManager(getContext(), 3);
                    serverAdapter = new ServerAdapter(serverList);
                    serverView.setLayoutManager(serverLayoutManager);
                    serverView.setAdapter(serverAdapter);
                })

                // Handle errors
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });
    }

    public void loadTables() {
        // Load Tables
        tableAccountsNull.get()
                // Handle success
                .addOnSuccessListener(task -> {
                    ArrayList<Table> tableList = new ArrayList<>();
                    for (DocumentSnapshot table : task.getDocuments()) {
                        Log.d("QuerySuccess", table.toString());
                        tableList.add(table.toObject(Table.class));
                    }

                    // Set up view of all tables
                    tableViewNull = view.findViewById(R.id.tablesView);
                    tableViewNull.setHasFixedSize(true);
                    tableLayoutManagerNull = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                    tableAdapterNull = new TableAdapter(tableList);
                    tableViewNull.setLayoutManager(tableLayoutManagerNull);
                    tableViewNull.setAdapter(tableAdapterNull);
                })

                // Handle errors
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });
    }
}