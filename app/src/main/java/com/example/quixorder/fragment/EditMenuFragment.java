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

import com.example.quixorder.R;
import com.example.quixorder.adapter.ItemTypeAdapter;
import com.example.quixorder.adapter.MenuItemAdapter;
import com.example.quixorder.adapter.TableAdapter;
import com.example.quixorder.model.ItemType;
import com.example.quixorder.model.MenuItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EditMenuFragment extends Fragment implements ItemTypeAdapter.OnItemTypeListener {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
    private CollectionReference itemTypes = firebase.collection("item_types");
    private CollectionReference menuItems = firebase.collection("menu_items");

    private View view;
    private RecyclerView itemTypeView;
    private RecyclerView.Adapter itemTypeAdapter;
    private RecyclerView.LayoutManager itemTypeLayoutManager;
    private RecyclerView menuItemView;
    private RecyclerView.Adapter menuItemAdapter;
    private RecyclerView.LayoutManager menuItemLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_menu, container, false);

        // Get views
        itemTypeView = view.findViewById(R.id.itemTypeView);
        menuItemView = view.findViewById(R.id.menuItemView);

        // Set listeners
        loadCompleteListeners();


        return view;
    }

    public void loadCompleteListeners() {
        // Load Item Types
        itemTypes.get()
                .addOnSuccessListener(task -> {
                    loadItemTypes(task);
                })
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });
    }

    public void loadItemTypes(QuerySnapshot task) {
        // Get Item Types list
        ArrayList<ItemType> itemTypeList = new ArrayList<>();
        for (DocumentSnapshot itemType : task.getDocuments()) {
            Log.d("QuerySuccess", itemType.toString());
            itemTypeList.add(itemType.toObject(ItemType.class));
        }

        // Set up view of all item types
        itemTypeView.setHasFixedSize(true);
        itemTypeLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        itemTypeAdapter = new ItemTypeAdapter(itemTypeList, this);
        itemTypeView.setLayoutManager(itemTypeLayoutManager);
        itemTypeView.setAdapter(itemTypeAdapter);
    }

    public void loadMenuItems(QuerySnapshot task) {
        // Get Menu Items list
        ArrayList<MenuItem> menuItemList = new ArrayList<>();
        for (DocumentSnapshot menuItem : task.getDocuments()) {
            Log.d("QuerySuccess", menuItem.toString());
            menuItemList.add(menuItem.toObject(MenuItem.class));
        }

        // Set up view of all menu items based on selected item type
        menuItemView.setHasFixedSize(true);
        menuItemLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        menuItemAdapter = new MenuItemAdapter(menuItemList);
        menuItemView.setLayoutManager(menuItemLayoutManager);
        menuItemView.setAdapter(menuItemAdapter);
    }

    @Override
    public void onItemTypeClick(int position, String itemType) {
        Log.d(TAG, "onItemTypeClick: clicked." + position);
        Query menuItemQuery = menuItems.whereEqualTo("type", itemType);

        menuItemQuery.get()
                .addOnSuccessListener(task -> {
                    loadMenuItems(task);
                })
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });
    }
}