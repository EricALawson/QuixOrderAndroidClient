package com.example.quixorder.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.quixorder.dummy.MenuItemContent;
import com.example.quixorder.R;
import com.example.quixorder.activity.LoginActivity;
import com.example.quixorder.activity.TableActivity;
import com.example.quixorder.adapter.CheckoutAdapter;
import com.example.quixorder.adapter.MenuAdapter;
import com.example.quixorder.model.MenuItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MenuFragment extends Fragment implements MenuAdapter.OnAddMenuItemListener {

   // private FirebaseFirestore firebase = FirebaseFirestore.getInstance();

   // private CheckoutFragment.CheckoutInteractionListener cListen;
    private DrawerLayout draw;

    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    //CollectionReference fdList;
    List<MenuItem> db = new ArrayList<MenuItem>();
    List<MenuItem> order = new ArrayList<MenuItem>();

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    //private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MenuAdapter ad;
    //private CheckoutAdapter ad2;
   // ArrayList items = new ArrayList<MenuItem>();

    //CollectionReference menuPics = firebase.collection("menu_items");
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MenuFragment newInstance(int columnCount) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (getArguments() != null) {
           // mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        //}
    }

    public void setOnListListener(OnListFragmentInteractionListener callback) {
        this.mListener = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.table_menu_item_list, container, false);

       /* Toolbar oToolbar = (Toolbar) view.findViewById(R.id.order_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(oToolbar);

        ActionBar aBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        aBar.setDisplayHomeAsUpEnabled(true);



        draw = view.findViewById(R.id.order_layout);
        NavigationView navigationView = view.findViewById(R.id.order_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

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
//-----------------------------------------------------------------------------------------------------
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        //RecyclerView orderView = (RecyclerView) view.findViewById(R.id.checkoutList);
        //recyclerView.setHasFixedSize(true);
        fb.collection("menu_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        db.add(document.toObject(MenuItem.class));
                        //order.add(document.toObject(MenuItem.class));
                        Log.d("Good", document.getId() + " => " + document.getData());
                        ad = new MenuAdapter(db, mListener, MenuFragment.this::onAddMenuItemClick);
                        //ad2 = new CheckoutAdapter(order, cListen);
                        //orderView.setAdapter(ad2);
                        recyclerView.setAdapter(ad);
                    }
                } else {
                    Log.d("Bad", "Error getting documents: ", task.getException());
                }
            }
        });
//-----------------------------------------------------------------------------------------------------
        //Log.d("MenuItem at 0:", db.get(0).getName() +" "+db.get(0).getPrice());
        //CheckoutAdapter cOut = new CheckoutAdapter(order, cListen);
        //orderView.setAdapter(cOut);

        //MenuAdapter ad = new MenuAdapter( db,  listen);
        //recyclerView.setAdapter(ad);
        //arrayList = new ArrayList();
        //GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        GridLayoutManager manager = new GridLayoutManager(this.getContext(), 2, GridLayoutManager.VERTICAL, false);
        //LinearLayoutManager manager2 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        //orderView.setLayoutManager(manager2);

        //final String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
        //fdList = fb.collection("menu_items");

        //findViewById(R.id.lOut).setOnClickListener(logOut);

       /* firebase.collection("menu_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        items.add(document.toObject(MenuItem.class));
                        Log.d("Good", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("Bad", "Error getting documents: ", task.getException());
                }
            }
        });*/



        // Set the adapter
       /* if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount, GridLayoutManager.VERTICAL, false));
            }
            //recyclerView.setAdapter(new MenuAdapter(db,  mListener));
        }*/
        return view;
    }

   /* public boolean onMenuItemSelected(int featureId, android.view.MenuItem item) {

        Intent myIntent = new Intent((AppCompatActivity)getActivity().getApplicationContext(), TableActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }*/

   /* public boolean onNavigationItemSelected(@NonNull android.view.MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.checkout:
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Order");
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.order_container,
                        new CheckoutFragment()).commit();

                //setContentView(R.layout.checkout_fragment);
                break;
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
    }*/

   /* public void getLogout()
    {

        String p = "1234567";
        AlertDialog.Builder sure = new AlertDialog.Builder(this.getContext());
        final EditText pass = new EditText(this.getContext());
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
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(getContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        sure.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        sure.show();
    }*/


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAddMenuItemClick(int position, MenuItem item) {
        Toast.makeText(getContext(), "Item added", Toast.LENGTH_SHORT).show();
        List<MenuItem> order = ((TableActivity)getActivity()).order;
        List<Integer> quantities = ((TableActivity) getActivity()).quantities;

        // Check for duplicate and update
        int i;
        for (i = 0; i < order.size(); i++) {
            if (order.get(i).getName().equals(item.getName())) {
                quantities.set(i, quantities.get(i) + 1);
                break;
            }
        }
        if (order.size() == 0 || i == order.size()) {
            order.add(item);
            quantities.add(1);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onListFragmentInteraction(DummyItem item);
        void OnListFragmentInteraction(MenuItem item);
    }
}
