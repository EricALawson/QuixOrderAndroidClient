package com.example.quixorder.fragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quixorder.OrderListAdapter;
import com.example.quixorder.OrderListViewModel;
import com.example.quixorder.R;
import com.example.quixorder.activity.LoginActivity;
import com.example.quixorder.activity.TableActivity;
import com.example.quixorder.adapter.CheckoutAdapter;
import com.example.quixorder.adapter.MenuAdapter;
import com.example.quixorder.model.MenuItem;
import com.example.quixorder.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CheckoutFragment extends Fragment //implements View.OnClickListener

    {//implements NavigationView.OnNavigationItemSelectedListener {

        private CheckoutViewModel mViewModel;
        private CheckoutInteractionListener listener;
        private RecyclerView order;
        private OrderListViewModel viewModel;
        private CheckoutAdapter ad;
        private Button add;
        private Button minus;
        private EditText qty;
        private FirebaseFirestore firebase = FirebaseFirestore.getInstance();
        double subTot = 20;
        //private Button pay;
        private int mColumnCount = 1;
        List<MenuItem> check = new ArrayList<MenuItem>();
        List<Integer> quantities = new ArrayList<Integer>();
        // private CollectionReference itemTypes = firebase.collection("item_types");
        // private CollectionReference menuItems = firebase.collection("menu_items");
        DocumentReference d = firebase.collection("orders").document("Ioy7CfhnnslBKGMgEVQG");
        private Order newOrder;
        //private DrawerLayout draw;
        public static CheckoutFragment newInstance () {
        return new CheckoutFragment();
    }

        @Override
        public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //if (getArguments() != null) {
        // mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        //}


    }

        @Override
        public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState){

        //order.setAdapter(CheckoutAdapter);
        View v = inflater.inflate(R.layout.checkout_fragment, container, false);
        setHasOptionsMenu(true);

        //pay = v.findViewById(R.id.payBtn);
        TextView sub = v.findViewById(R.id.sub);

        sub.setText(String.format("%.2f",subTot));
        //pay.setOnClickListener(new View.OnClickListener() {
            v.findViewById(R.id.payBtn).setOnClickListener(/*this);*/new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View view) {
                                                                   Log.d("Pay", "pay button pressed");
                                                                   payCheck();
                                                               }
                                                           });
            //Take the user to a new screen eor just have a pop-up window to enter info and it takes them back to the menu when done.
                  /*  String p = "1234567";
                    AlertDialog.Builder sure = new AlertDialog.Builder(getContext());
                    final EditText pass = new EditText(getContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    pass.setLayoutParams(lp);
                    sure.setView(pass);

                    sure.setTitle("Logout?");
                    sure.setMessage("Enter Password");
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    sure.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            if(pass.getText().toString().toUpperCase().equals(p.toUpperCase()))
                            {
                                startActivity(new Intent(getActivity(), TableActivity.class));
                                getActivity().finish();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
            }*/
        //});

       /* d.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Order x = documentSnapshot.toObject(Order.class);
                newOrder = x;
            }
        });

        firebase.collection("menu_items").document("9UjLSl36gN6qXEtOiEff").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                MenuItem a = documentSnapshot.toObject(MenuItem.class);
                newOrder.orderMenuItems.add(a);
                check = newOrder.getOrderItems();

            }
        });*/

        order = v.findViewById(R.id.checkoutList);
        check = ((TableActivity)getActivity()).order;
        quantities = ((TableActivity)getActivity()).quantities;

        ad = new CheckoutAdapter(check, quantities, listener);
        order.setAdapter(ad);
        order.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //initializeCheckoutList(v);

       /* Toolbar oToolbar = (Toolbar) v.findViewById(R.id.order_toolbar);
        setSupportActionBar(oToolbar);

        final ActionBar aBar = getSupportActionBar();
        //aBar.setDisplayHomeAsUpEnabled(true);

        draw = v.findViewById(R.id.order_layout);
        NavigationView navigationView = v.findViewById(R.id.order_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this.getActivity(), draw, oToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();

        oToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(draw.isDrawerOpen(Gravity.RIGHT))
                {
                    draw.closeDrawer(Gravity.RIGHT);
                }
                else
                {
                    draw.openDrawer(Gravity.RIGHT);
                }
            }
        });*/

       /* if (v instanceof RecyclerView) {
            Context context = v.getContext();
            order = (RecyclerView) v;
            if (mColumnCount <= 1) {
                order.setLayoutManager(new LinearLayoutManager(context));
            } else {
                order.setLayoutManager(new GridLayoutManager(context, mColumnCount, GridLayoutManager.VERTICAL, false));
            }
            //recyclerView.setAdapter(new MenuAdapter(db,  mListener));
        }*/


        /*firebase.collection("menu_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        order.add(document.toObject(MenuItem.class));
                        //order.add(document.toObject(MenuItem.class));
                        Log.d("Good", document.getId() + " => " + document.getData());
                        ad = new MenuAdapter(order, mListener);
                        //ad2 = new CheckoutAdapter(order, cListen);
                        //orderView.setAdapter(ad2);
                        order.setAdapter(ad);
                    }
                } else {
                    Log.d("Bad", "Error getting documents: ", task.getException());
                }
            }
        });*/

        return v;
    }


    public void payCheck()
    {
        Log.d("PayCheck", "function entered");
        AlertDialog.Builder pay = new AlertDialog.Builder(getContext());
        final EditText card = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        card.setLayoutParams(lp);
        pay.setView(card);

        pay.setTitle("Checkout");
        pay.setMessage("Enter valid payment information");

        pay.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(card.getText().length() == 16 && card.getText().toString().matches("-?\\d+(\\.\\d+)?"))
                {
                    Toast.makeText(getContext(), "Order "+newOrder.getDocumentId()+" is paid", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), TableActivity.class));
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getContext(), "Invalid CC number", Toast.LENGTH_SHORT).show();
                }
                pay.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                pay.show();
            }
        });
    }


        private void initializeCheckoutList (View v){
        order = v.findViewById(R.id.checkoutList);
        // order.setHasFixedSize(true);

        viewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);
        LiveData<List<Order>> liveData = viewModel.getOrderLiveData();
        liveData.observe(this, (List<Order> orders) -> {
            order.setLayoutManager(new LinearLayoutManager(getContext()));
            order.setAdapter(new OrderListAdapter(orders));

        });
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.order_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/
 /*  @Override
   private void View.OnClickListener()
        {

        }*/

    /*
    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.checkout:
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Order");
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.order_container,
                        new CheckoutFragment()).commit();

                //setContentView(R.layout.checkout_fragment);
                break;

            case R.id.call:
                Log.d("Call", "Call Server");
            case R.id.logout:
                getLogout();
                break;
            case R.id.menu:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.order_container,
                        new MenuFragment()).commit();
                //navigationView.setCheckedItem(R.id.recyclerView);
                break;
        }

        //draw.closeDrawer(GravityCompat.START);
        return true;
    }



    /*@Override
    public void onRemoveMenuItemClick(int position, String itemName) {
        Log.d("onRemoveMenuItemClick: ", itemName);

        Query menuItemQuery = order.whereEqualTo("name", itemName);

        // Remove menu item
        menuItemQuery.get()
                .addOnSuccessListener(task -> {
                    removeMenuItem(task);
                })
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed:", error.getMessage());
                });
    }*/

   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CheckoutViewModel.class);
        // TODO: Use the ViewModel
    }*/



        public interface CheckoutInteractionListener {
            // TODO: Update argument type and name
            //void onListFragmentInteraction(DummyItem item);
            void checkoutInteraction(MenuItem item);
        }

    }




