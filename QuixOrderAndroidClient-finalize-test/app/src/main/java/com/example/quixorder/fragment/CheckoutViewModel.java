package com.example.quixorder.fragment;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quixorder.model.MenuItem;
import com.example.quixorder.model.Order;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CheckoutViewModel extends ViewModel
{   // TODO: Implement the ViewModel
  //  private MutableLiveData<List<MenuItem>> orderLiveData;
    //private Query orderQuery;
    //private DocumentReference order = FirebaseFirestore.getInstance().collection("orders").document("Ioy7CfhnnslBKGMgEVQG");
    //List<MenuItem> x = order;
    //Figure out how to add adapter for checkout recycling view
    //public CheckoutViewModel() {
      //  super();
       // orderQuery = FirebaseFirestore.getInstance().collection("orders").document("orderItems");

        /* orderQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                loadOrder(queryDocumentSnapshots);
            }
        });*/
   /* }

    public LiveData<List<MenuItem>> getOrderLiveData() {
        if (orderLiveData == null) {
            orderLiveData = new MutableLiveData<>();
            orderLiveData.setValue(new ArrayList<>());
            Log.d("OrderListViewModel", "getting orders from Firebase");
            loadOrder();
        }
        return orderLiveData;
    }

        private void loadOrder() {
            orderQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {
                Log.d("CheckoutViewModel", "loadOrders Success listener fired");
                loadOrder(queryDocumentSnapshots);
            });
        }

    private void loadOrder(QuerySnapshot snapshots)
    {
        ArrayList<MenuItem> items = new ArrayList<>();
        if (snapshots != null && snapshots.size() > 0)
        {
            for (DocumentSnapshot snapshot : snapshots.getDocuments() )
            {
                MenuItem item = snapshot.toObject(MenuItem.class);
                items.add(item);
            }
            Log.d("CheckoutViewModel", "Adding orders to LiveData size = " + items.size());
        } else {
            if (snapshots == null) {
                Log.d("CheckoutViewModel", "loaded snapshot was null");
            } else {
                Log.d("CheckoutViewModel", "loaded snapshot size = " + snapshots.size());
            }
        }
        orderLiveData.postValue(items);
    }*/
}

