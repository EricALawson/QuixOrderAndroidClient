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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AssignTableFragment extends Fragment {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private CollectionReference accounts = firebase.collection("accounts");
    private Query serverAccounts = accounts.whereEqualTo("type", "Server");
    private Query tableAccounts = accounts.whereEqualTo("type", "Table");

    private View view;
    private RecyclerView serverView;
    private RecyclerView.Adapter serverAdapter;
    private RecyclerView.LayoutManager serverLayoutManager;

    private RecyclerView tableView;
    private RecyclerView.Adapter tableAdapter;
    private RecyclerView.LayoutManager tableLayoutManager;

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
        serverAccounts.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot query,
                                @javax.annotation.Nullable FirebaseFirestoreException error) {

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
            }
        });

        // Live update tableAccounts
        tableAccounts.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot query,
                                @javax.annotation.Nullable FirebaseFirestoreException error) {

                // Check for errors
                if (error != null) {
                    Log.e("QueryFailed", error.getMessage());
                    return;
                }

                // Load tables
                ArrayList<Account> tableList = new ArrayList<>();
                for (DocumentSnapshot table : query.getDocuments()) {
                    Log.d("QuerySuccess", table.toString());
                    tableList.add(table.toObject(Account.class));
                }

                // Set up view of all tables
                tableView = view.findViewById(R.id.tablesView);
                tableView.setHasFixedSize(true);
                tableLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                tableAdapter = new TableAdapter(tableList);
                tableView.setLayoutManager(tableLayoutManager);
                tableView.setAdapter(tableAdapter);
            }
        });
    }

    public void loadServers() {
        serverAccounts.get()
                // Load Servers
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
        tableAccounts.get()
                // Load Tables
                .addOnSuccessListener(task -> {
                    ArrayList<Account> tableList = new ArrayList<>();
                    for (DocumentSnapshot table : task.getDocuments()) {
                        Log.d("QuerySuccess", table.toString());
                        tableList.add(table.toObject(Account.class));
                    }

                    // Set up view of all tables
                    tableView = view.findViewById(R.id.tablesView);
                    tableView.setHasFixedSize(true);
                    tableLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
                    tableAdapter = new TableAdapter(tableList);
                    tableView.setLayoutManager(tableLayoutManager);
                    tableView.setAdapter(tableAdapter);
                })

                // Handle errors
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });
    }
}