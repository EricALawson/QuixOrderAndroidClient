package com.example.quixorder.adapter.server;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.adapter.TableAdapter;
import com.example.quixorder.model.Account;
import com.example.quixorder.model.Table;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> implements View.OnDragListener {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private CollectionReference accounts = firebase.collection("accounts");

    private ArrayList<Account> serverList;

    public ServerAdapter(ArrayList<Account> serverList) {
        this.serverList = serverList;
    }

    @Override
    public int getItemCount() {
        return serverList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.server_item, viewGroup, false);
        ViewHolder evh = new ViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Account currentItem = serverList.get(i);
        viewHolder.tableAccounts = accounts.whereEqualTo("server", currentItem.getUsername());

        // Set views
        viewHolder.textView1.setText(currentItem.getUsername());

        // Set listeners
        viewHolder.cardView.setOnDragListener(this);
        viewHolder.loadSnapshotListeners();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private RecyclerView tableView;
        private TextView textView1;

        private RecyclerView.Adapter tableAdapter;
        private RecyclerView.LayoutManager tableLayoutManager;
        private Query tableAccounts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tableView = itemView.findViewById(R.id.tableView);
            textView1 = itemView.findViewById(R.id.textView1);
        }

        public void loadSnapshotListeners() {
            // Live update tableAccounts
            tableAccounts.addSnapshotListener((Activity) cardView.getContext(), (query, error) -> {
                if (error != null) {
                    Log.e("QueryFailed", error.getMessage());
                    return;
                }
                loadTables(query);
            });
        }

        public void loadTables(QuerySnapshot task) {
            // Get tables list
            ArrayList<Table> tableList = new ArrayList<>();
            for (DocumentSnapshot table : task.getDocuments()) {
                Log.d("QuerySuccess", table.toString());
                tableList.add(table.toObject(Table.class));
            }

            // Set up view of all tables
            tableView.setHasFixedSize(true);
            tableLayoutManager = new GridLayoutManager(cardView.getContext(), 3);
            tableAdapter = new TableAdapter(tableList);
            tableView.setLayoutManager(tableLayoutManager);
            tableView.setAdapter(tableAdapter);
        }
    }
}
