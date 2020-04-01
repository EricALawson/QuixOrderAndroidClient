package com.example.quixorder.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quixorder.R;
import com.example.quixorder.adapter.ItemTypeAdapter;
import com.example.quixorder.adapter.MenuItemAdapter;
import com.example.quixorder.model.ItemType;
import com.example.quixorder.model.MenuItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class EditMenuFragment
        extends Fragment
        implements ItemTypeAdapter.OnItemTypeListener, ItemTypeAdapter.OnRemoveItemTypeListener, MenuItemAdapter.OnRemoveMenuItemListener {
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

    private ListenerRegistration menuItemsListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_menu, container, false);

        // Get views
        itemTypeView = view.findViewById(R.id.itemTypeView);
        menuItemView = view.findViewById(R.id.menuItemView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Set snapshot listeners
        loadSnapshotListeners();
    }

    public void loadSnapshotListeners() {
        itemTypes.addSnapshotListener(getActivity(), (query, error) -> {
            if (error != null) {
                Log.e("QueryFailed:", error.getMessage());
                return;
            }
            loadItemTypes(query);
        });
    }

    public void loadItemTypes(QuerySnapshot task) {
        // Get Item Types list
        ArrayList<ItemType> itemTypeList = new ArrayList<>();
        for (DocumentSnapshot itemType : task.getDocuments()) {
            Log.d("QuerySuccess:", itemType.toString());
            itemTypeList.add(itemType.toObject(ItemType.class));
        }

        // Set up view of all item types
        itemTypeView.setHasFixedSize(true);
        itemTypeLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        itemTypeAdapter = new ItemTypeAdapter(itemTypeList, this, this);
        itemTypeView.setLayoutManager(itemTypeLayoutManager);
        itemTypeView.setAdapter(itemTypeAdapter);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (!itemTypeView.isComputingLayout()) {
                    itemTypeView.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
            }
        });
    }

    public void removeItemType(QuerySnapshot task) {
        String itemType = task.getDocuments().get(0).getId();

        // Load delete listener
        itemTypes.document(itemType)
                .delete()
                .addOnSuccessListener(removeTask -> {
                    Log.d("RemoveSuccess", itemType);
                })
                .addOnFailureListener(error -> {
                   Log.e("RemoveFailed", error.getMessage());
                });
    }

    public void loadMenuItems(QuerySnapshot task) {
        // Get Menu Items list
        ArrayList<MenuItem> menuItemList = new ArrayList<>();
        for (DocumentSnapshot menuItem : task.getDocuments()) {
            Log.d("QuerySuccess:", menuItem.toString());
            menuItemList.add(menuItem.toObject(MenuItem.class));
        }

        // Set up view of all menu items based on selected item type
        menuItemView.setHasFixedSize(true);
        menuItemLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        menuItemAdapter = new MenuItemAdapter(menuItemList, this);
        menuItemView.setLayoutManager(menuItemLayoutManager);
        menuItemView.setAdapter(menuItemAdapter);
    }

    public void removeMenuItem(QuerySnapshot task) {
        String menuItem = task.getDocuments().get(0).getId();

        // Load delete listener
        menuItems.document(menuItem)
                .delete()
                .addOnSuccessListener(removeTask -> {
                    Log.d("RemoveSuccess", menuItem);
                })
                .addOnFailureListener(error -> {
                    Log.e("RemoveFailed", error.getMessage());
                });
    }

    @Override
    public void onItemTypeClick(int position, String itemType) {
        Log.d("onItemTypeClick: ", "" + itemType);
        Query menuItemQuery = menuItems.whereEqualTo("type", itemType);

        // Load complete listener
        menuItemQuery.get()
                .addOnSuccessListener(task -> {
                    loadMenuItems(task);
                })
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed:", error.getMessage());
                });

        // Remove menu item listener
        if (menuItemsListener != null) {
            menuItemsListener.remove();
        }

        // Load snapshot listener
        menuItemsListener = menuItemQuery.addSnapshotListener(getActivity(), (query, error) -> {
            if (error != null) {
                Log.e("QueryFailed:", error.getMessage());
                return;
            }

            loadMenuItems(query);
        });
    }

    @Override
    public void onRemoveItemTypeListener(int position, String itemType) {
        Log.d("onRemoveItemTypeClick: ", itemType);

        Query itemTypeQuery = itemTypes.whereEqualTo("type", itemType);

        // Remove item type
        itemTypeQuery.get()
                .addOnSuccessListener(task -> {
                    removeItemType(task);
                })
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed:", error.getMessage());
                });
    }


    @Override
    public void onRemoveMenuItemListener(int position, String itemName) {
        Log.d("onRemoveMenuItemClick: ", itemName);

        Query menuItemQuery = menuItems.whereEqualTo("name", itemName);

        // Remove menu item
        menuItemQuery.get()
                .addOnSuccessListener(task -> {
                    removeMenuItem(task);
                })
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed:", error.getMessage());
                });
    }
}