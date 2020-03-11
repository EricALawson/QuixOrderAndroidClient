package com.example.quixorder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quixorder.R;
import com.example.quixorder.adapter.TableAdapter;
import com.example.quixorder.item.TableItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AssignTableFragment extends Fragment {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();

    private View view;
    private RecyclerView tableView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assign_table, container, false);

        ArrayList<TableItem> tableList = new ArrayList<>();
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 1", "Line 2"));
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 3", "Line 4"));
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 5", "Line 6"));
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 7", "Line 8"));
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 9", "Line 10"));
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 11", "Line 12"));
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 13", "Line 14"));
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 15", "Line 16"));
        tableList.add(new TableItem(R.drawable.ic_running_order, "Line 17", "Line 18"));

        tableView = view.findViewById(R.id.tablesView);
        tableView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TableAdapter(tableList);

        tableView.setLayoutManager(layoutManager);
        tableView.setAdapter(adapter);

        return view;
    }
}
