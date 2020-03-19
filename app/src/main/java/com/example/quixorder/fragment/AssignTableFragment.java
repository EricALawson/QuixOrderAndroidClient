package com.example.quixorder.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AssignTableFragment extends Fragment implements View.OnDragListener {
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

        serverView = view.findViewById(R.id.serversView);
        tableViewNull = view.findViewById(R.id.tablesView);
        tableViewNull.setOnDragListener(this);

        loadServers();
        loadTables();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadServersListener();
        loadTablesListener();
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
                    tableViewNull.setHasFixedSize(true);
                    tableLayoutManagerNull = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    tableAdapterNull = new TableAdapter(tableList);
                    tableViewNull.setLayoutManager(tableLayoutManagerNull);
                    tableViewNull.setAdapter(tableAdapterNull);
                })

                // Handle errors
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });
    }

    public void loadServersListener() {
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
            serverView.setHasFixedSize(true);
            serverLayoutManager = new GridLayoutManager(getContext(), 3);
            serverAdapter = new ServerAdapter(serverList);
            serverView.setLayoutManager(serverLayoutManager);
            serverView.setAdapter(serverAdapter);

        });
    }

    public void loadTablesListener() {
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
            tableViewNull.setHasFixedSize(true);
            tableLayoutManagerNull = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            tableAdapterNull = new TableAdapter(tableList);
            tableViewNull.setLayoutManager(tableLayoutManagerNull);
            tableViewNull.setAdapter(tableAdapterNull);
        });
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        switch(event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
            case DragEvent.ACTION_DRAG_ENDED:
                return true;
            case DragEvent.ACTION_DROP:
                Log.d("onDrag", "dropped");

                // Get table's username
                String tableUsername = event.getClipData().getItemAt(0).getText().toString();
                Log.d("onDragTable", tableUsername);

                // Assign table's server to null
                accounts.whereEqualTo("username", tableUsername)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Log.e("QueryFailed", task.getException().getMessage());
                            } else {
                                // Update account
                                Log.d("QuerySuccess", tableUsername);
                                for (DocumentSnapshot table : task.getResult().getDocuments()) {
                                    accounts.document(table.getId())
                                            .update(
                                                    "server", null
                                            );
                                }

                            }
                        });

                return true;
        }
        return false;
    }
}