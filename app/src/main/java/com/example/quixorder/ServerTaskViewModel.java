package com.example.quixorder;

import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quixorder.adapter.server.IServerTask;
import com.example.quixorder.model.Order;
import com.example.quixorder.model.TableCall;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ServerTaskViewModel extends ViewModel {
    private List<Order> orderData = new ArrayList<>();
    private List<TableCall> callData = new ArrayList<>();
    private MutableLiveData<List<IServerTask>> liveData;
    private Query orderQuery;
    private Query callQuery;

    public ServerTaskViewModel() {
        super();
    }

    private void loadOrders() {
        orderQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.d("OrderListViewModel", "loadOrders Success listener fired");
            loadOrders(queryDocumentSnapshots);
        });
    }

    private void loadCalls() {
        callQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.d("OrderListViewModel", "loadCalls Success listener fired");
            loadCalls(queryDocumentSnapshots);
        });
    }

    private void loadCalls(QuerySnapshot queryDocumentSnapshots) {
        ArrayList<TableCall> calls = new ArrayList<>();
        if (queryDocumentSnapshots != null && queryDocumentSnapshots.size() > 0) {
            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments() ) {
                TableCall call = snapshot.toObject(TableCall.class);
                call.setDocumentID(snapshot.getId());
                calls.add(call);
            }
            Log.d("ServerTaskViewModel", "Adding table calls to live data size = " + calls.size());
        } else {
            if (queryDocumentSnapshots == null) {
                Log.d("ServerTaskViewModel", "table call snapshot was null");
            } else {
                Log.d("ServerTaskViewModel", "table call snapshot size = " + queryDocumentSnapshots.size());
            }
        }

        callData = calls;
        List<IServerTask> taskList = new ArrayList<>();
        taskList.addAll(orderData);
        taskList.addAll(callData);
        liveData.postValue(taskList);
    }

    private void loadOrders(QuerySnapshot snapshots) {
        ArrayList<Order> orders = new ArrayList<>();
        if (snapshots != null && snapshots.size() > 0) {
            for (DocumentSnapshot snapshot : snapshots.getDocuments() ) {
                Order order = new Order(snapshot);
                orders.add(order);
            }
            Log.d("ServerTaskViewModel", "Adding orders to LiveData size = " + orders.size());
        } else {
            if (snapshots == null) {
                Log.d("ServerTaskViewModel", "order snapshot was null");
            } else {
                Log.d("ServerTaskViewModel", "order snapshot size = " + snapshots.size());
            }
        }

        orderData = orders;
        List<IServerTask> taskList = new ArrayList<>();
        taskList.addAll(orderData);
        taskList.addAll(callData);
        liveData.postValue(taskList);
    }

    public void setOrderQuery(Query query) {
        orderQuery = query;
        orderQuery.addSnapshotListener((queryDocumentSnapshots, e) -> loadOrders(queryDocumentSnapshots));
    }

    public void setCallQuery(Query query) {
        callQuery = query;
        callQuery.addSnapshotListener((queryDocumentSnapshots, e) -> loadCalls(queryDocumentSnapshots));
    }

    public LiveData<List<IServerTask>> getLiveData() {
        if (liveData == null) {
            List<IServerTask> taskList = new ArrayList<>();
            liveData = new MutableLiveData<>();
            liveData.setValue(taskList);
            loadOrders();
            loadCalls();
        }

        return liveData;
    }
}
