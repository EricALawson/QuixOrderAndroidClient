package com.example.quixorder;

import android.media.SoundPool;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quixorder.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.local.QueryData;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class OrderListViewModel extends ViewModel {
    MutableLiveData<List<Order>> orderLiveData;
    FirebaseFirestore firebase;

    public OrderListViewModel() {
        super();
        orderLiveData = new MutableLiveData<>();
        orderLiveData.setValue(new ArrayList<Order>());
    }

    public LiveData<List<Order>> getOrderLiveData() {
        Log.d("OrderListViewModel", "getting orders from Firebase");
        if(orderLiveData.getValue() == null) {
            Query orderQuery = FirebaseFirestore.getInstance().collection("orders").whereEqualTo("cookedTime", null);

            orderQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    setLiveData(queryDocumentSnapshots);
                }
            });
        }

        return orderLiveData;
    }

    private void setLiveData(QuerySnapshot snapshots) {
        ArrayList<Order> orders = new ArrayList<>();
        if (snapshots != null && snapshots.size() > 0) {
            for (DocumentSnapshot snapshot : snapshots.getDocuments() ) {
                Order order = new Order(snapshot);
                orders.add(order);
            }
            Log.d("OrderListViewModel", "Adding orders to LiveData");
        }
        orderLiveData.postValue(orders);
    }
}
