package com.example.quixorder.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.model.Account;
import com.example.quixorder.model.Table;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ExampleViewHolder> implements View.OnDragListener {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private CollectionReference accounts = firebase.collection("accounts");

    private ArrayList<Account> serverList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public RecyclerView tableView;
        public ImageView imageView;
        public TextView textView1;
        public RecyclerView.Adapter tableAdapter;
        public RecyclerView.LayoutManager tableLayoutManager;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tableView = itemView.findViewById(R.id.tableView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
        }
    }

    public ServerAdapter(ArrayList<Account> serverList) {
        this.serverList = serverList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.server_item, viewGroup, false);
        ExampleViewHolder evh = new ExampleViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        Account currentItem = serverList.get(i);

        exampleViewHolder.imageView.setImageResource(R.drawable.ic_assign_table_blk);
        exampleViewHolder.textView1.setText(currentItem.getUsername());
        exampleViewHolder.cardView.setOnDragListener(this);

        // Retrieve this Server's Table Accounts assigned to them
        Query tableAccounts = accounts.whereEqualTo("server", currentItem.getUsername());

        // Live update tableAccounts
        tableAccounts.addSnapshotListener((Activity) exampleViewHolder.tableView.getContext(), (task, error) -> {

            // Check for errors
            if (error != null) {
                Log.e("QueryFailed", error.getMessage());
                return;
            }

            // Load tables
            ArrayList<Table> tableList = new ArrayList<>();
            for (DocumentSnapshot table : task.getDocuments()) {
                Log.d("QuerySuccess", table.toString());
                tableList.add(table.toObject(Table.class));
            }

            // Set up view of all tables
            exampleViewHolder.tableView.setHasFixedSize(true);
            exampleViewHolder.tableLayoutManager = new GridLayoutManager(exampleViewHolder.tableView.getContext(), 3);
            exampleViewHolder.tableAdapter = new TableAdapter(tableList);
            exampleViewHolder.tableView.setLayoutManager(exampleViewHolder.tableLayoutManager);
            exampleViewHolder.tableView.setAdapter(exampleViewHolder.tableAdapter);

        });

        // Load Tables
        tableAccounts.get()
                .addOnSuccessListener(task -> {
                    ArrayList<Table> tableList = new ArrayList<>();
                    for (DocumentSnapshot table : task.getDocuments()) {
                        Log.d("QuerySuccess", table.toString());
                        tableList.add(table.toObject(Table.class));
                    }

                    // Set up view of all tables
                    exampleViewHolder.tableView.setHasFixedSize(true);
                    exampleViewHolder.tableLayoutManager = new GridLayoutManager(exampleViewHolder.tableView.getContext(), 3);
                    exampleViewHolder.tableAdapter = new TableAdapter(tableList);
                    exampleViewHolder.tableView.setLayoutManager(exampleViewHolder.tableLayoutManager);
                    exampleViewHolder.tableView.setAdapter(exampleViewHolder.tableAdapter);
                })

                // Handle errors
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });
    }

    @Override
    public int getItemCount() {
        return serverList.size();
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

                // Get server's username
                String serverUsername = ((TextView)view.findViewById(R.id.textView1)).getText().toString();
                Log.d("onDragServer", serverUsername);

                // Assign table's server to new server
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
                                                    "server", serverUsername
                                            );
                                }

                            }
                        });
                return true;
        }
        return false;
    }
}
