package com.example.quixorder.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.example.quixorder.R;
import com.example.quixorder.adapter.TaskAdapter;
import com.example.quixorder.model.Account;
import com.example.quixorder.model.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployeeActivityFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // Declare firestore variables
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private Query serverAccounts = firebase.collection("accounts").whereEqualTo("type", "Server");
    private CollectionReference orders = firebase.collection("orders");
    private Query tasksQuery;

    // Declare views
    private View view;

    // Declare spinner variables
    private Spinner employeeTypeSpinner;
    private ArrayAdapter<CharSequence> employeeTypeAdapter;
    private FrameLayout employeeListFrameLayout;
    private Spinner employeeListSpinner;
    private ArrayAdapter<CharSequence> employeeListAdapter;

    // Declare recycler view variables
    private RecyclerView taskView;
    private TaskAdapter taskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;
    private ListenerRegistration tasksListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_employee_activity, container, false);

        // Set up views
        employeeTypeSpinner = view.findViewById(R.id.employeeTypeSpinner);
        employeeListFrameLayout = view.findViewById(R.id.employeeListFrameLayout);
        employeeListSpinner = view.findViewById(R.id.employeeListSpinner);
        taskView = view.findViewById(R.id.taskList);

        // Create employee type picker
        employeeTypeAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.employeeTypes, android.R.layout.simple_spinner_item);
        employeeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeTypeSpinner.setAdapter(employeeTypeAdapter);
        employeeTypeSpinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Set snapshot listeners
        loadSnapshotListeners();
    }

    public void loadSnapshotListeners() {
        serverAccounts.addSnapshotListener(getActivity(), (query, error) -> {
            if (error != null) {
                Log.e("QueryFailed", error.getMessage());
                return;
            }

            loadServers(query);
        });
    }

    public void loadTasks(QuerySnapshot task) {
        ArrayList<Order> orderList = new ArrayList<>();
        for (DocumentSnapshot order : task.getDocuments()) {
            Log.d("QuerySuccess", order.toString());
            orderList.add(order.toObject(Order.class));
        }

        // Set up view of all task list
        taskView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        taskAdapter = new TaskAdapter(orderList);
        taskView.setLayoutManager(taskLayoutManager);
        taskView.setAdapter(taskAdapter);
    }

    public void loadServers(QuerySnapshot task) {
        // Get servers list
        List<String> serverList = new ArrayList<>();
        for (DocumentSnapshot server : task.getDocuments()) {
            Log.d("QuerySuccess", server.toString());
            serverList.add(server.toObject(Account.class).getUsername());
        }

        // Convert server list to string array
        String[] serverArray = new String[serverList.size()];
        serverArray = serverList.toArray(serverArray);

        // Create employee list picker
        employeeListAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, serverArray);
        employeeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeListSpinner.setAdapter(employeeListAdapter);
        employeeListSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String type = employeeTypeSpinner.getSelectedItem().toString();
        String name = "";
        if (employeeListSpinner.getSelectedItem() != null) {
            name = employeeListSpinner.getSelectedItem().toString();
        }

        if (tasksListener != null) {
            tasksListener.remove();
        }

        tasksQuery = orders.orderBy("startTime");

        switch(parent.getId()) {
            // on employee type spinner selected
            case R.id.employeeTypeSpinner:
                Log.d("onItemSelected", "employeeType:" + type);

                if (type.equals("Server")) {
                    tasksQuery = orders.whereEqualTo("server", name);
                    employeeListFrameLayout.setVisibility(View.VISIBLE);
                } else if (type.equals("Cook")){
                    tasksQuery = orders.orderBy("startTime");
                    employeeListFrameLayout.setVisibility(View.GONE);
                }
                break;

            // on employee list spinner selected
            case R.id.employeeListSpinner:
                tasksQuery = orders.whereEqualTo("server", name);
                Log.d("onItemSelected", "employeeName:" + name);

                break;
        }

        // Load task snapshot listener
        tasksListener = tasksQuery.addSnapshotListener(getActivity(), (query, error) -> {
            if (error != null) {
                Log.d("QueryFailed", error.getMessage());
                return;
            }

            loadTasks(query);
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
