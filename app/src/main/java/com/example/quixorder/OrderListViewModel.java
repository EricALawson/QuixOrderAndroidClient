package com.example.quixorder;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quixorder.model.Order;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class OrderListViewModel extends ViewModel {
    private MutableLiveData<List<Order>> orderLiveData;
    private Query orderQuery = FirebaseFirestore.getInstance().collection("orders").whereEqualTo("cookedTime", null);

    public OrderListViewModel() {
        super();
        //orderLiveData = new MutableLiveData<>();
        //orderLiveData.setValue(new ArrayList<Order>());
        orderQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                loadOrders(queryDocumentSnapshots);
            }
        });
    }

    public LiveData<List<Order>> getOrderLiveData() {
        if(orderLiveData == null) {
            orderLiveData = new MutableLiveData<>();
            orderLiveData.setValue(new ArrayList<>());
            Log.d("OrderListViewModel", "getting orders from Firebase");
            loadOrders();
        }

        return orderLiveData;
    }

    private void loadOrders() {
        orderQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.d("OrderListViewModel", "loadOrders Success listener fired");
            loadOrders(queryDocumentSnapshots);
        });
    }

    private void loadOrders(QuerySnapshot snapshots) {
        ArrayList<Order> orders = new ArrayList<>();
        if (snapshots != null && snapshots.size() > 0) {
            for (DocumentSnapshot snapshot : snapshots.getDocuments() ) {
                Order order = new Order(snapshot);
                orders.add(order);
            }
            Log.d("OrderListViewModel", "Adding orders to LiveData size = " + orders.size());
        } else {
            if (snapshots == null) {
                Log.d("OrderListViewModel", "loaded snapshot was null");
            } else {
                Log.d("OrderListViewModel", "loaded snapshot size = " + snapshots.size());
            }
        }
        orderLiveData.postValue(orders);
    }


}
