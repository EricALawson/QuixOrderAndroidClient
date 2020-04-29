package com.example.quixorder.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quixorder.fragment.CheckoutFragment;
import com.example.quixorder.fragment.MenuFragment;
import com.example.quixorder.R;
//import com.example.quixorder.fragment.MenuFragment;
import com.example.quixorder.model.MenuItem;
import com.example.quixorder.model.Order;
import com.example.quixorder.model.Table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MenuFragment.OnListFragmentInteractionListener {

    //MenuItemContent db = new MenuItemContent();
    //private MenuFragment.OnListFragmentInteractionListener listen;
    //private CheckoutFragment.CheckoutInteractionListener cListen;
    private DrawerLayout draw;
    //private TableCall current;
    private Order newOrder;
    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    private CollectionReference callList;
    //List<MenuItem> db = new ArrayList<MenuItem>();
    public String curr;
    public String server;
    private String tableId;
    private DocumentReference t;
    public List<MenuItem> order = new ArrayList<MenuItem>();
    public List<Integer> quantities = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        //Intent i = getIntent();
        callList = fb.collection("table_calls");

        curr = getIntent().getStringExtra("username");
        // Log.d("Table", curr);

        // Query x = fb.collection("accounts").whereEqualTo("username", curr);

        //QuerySnapshot q = x.get().getResult();

        // tableId = q.getDocuments().get(0).getId();

        fb.collection("accounts")
                .whereEqualTo("username", curr)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Table", document.getId() + " => " + document.getData());
                                tableId = document.getId();
                                t = fb.collection("accounts").document(tableId);
                                t.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        Table current = documentSnapshot.toObject(Table.class);
                                        server = current.getServer();
                                    }
                                });
                            }
                        } else {
                            Log.d("Table", "Error getting documents: ", task.getException());
                        }
                    }
                });










        /*Log.d("Table 1", curr);
        if(curr.equals("table 1"))
            Log.d("Table 1", "Table 1 noticed");*/



        Toolbar oToolbar = (Toolbar) findViewById(R.id.order_toolbar);
        setSupportActionBar(oToolbar);

        ActionBar aBar = getSupportActionBar();
        aBar.setDisplayHomeAsUpEnabled(true);

        draw = findViewById(R.id.order_layout);
        NavigationView navigationView = findViewById(R.id.order_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, draw, oToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();

        oToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (draw.isDrawerOpen(Gravity.RIGHT)) {
                    draw.closeDrawer(Gravity.RIGHT);
                } else {
                    draw.openDrawer(Gravity.RIGHT);
                }
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.order_container,
                    new MenuFragment()).commit();
            navigationView.setCheckedItem(R.id.list);
        }
        //RecyclerView menu = (RecyclerView) findViewById(R.id.list);



        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // getSupportActionBar().setDisplayShowHomeEnabled(false);


    }

      /*  private View.OnClickListener logOut = new View.OnClickListener() {

        @Override
        public void onClick(View view)
        {
            //setContentView(R.layout.login);
            //Intent main = new Intent(CookActivity.this, LoginActivity.class);
            getLogout();
            //startActivity(new Intent(TableActivity.this, LoginActivity.class));
            //finish();


        }
    };*/

    //public MenuFragment.OnListFragmentInteractionListener getListen() {
    //  return listen;
    //}


    @Override
    public void OnListFragmentInteraction(MenuItem m)
    {
        //db.add(m);
        Log.d("Menu item Added", "Added.");
    }


   /* {
        @Override
        public void OnListFragmentInteractionListener(MenuItem item)
        {
            order.add(item);
        }
    };*/


    @Override
    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.checkout:
                getSupportActionBar().setTitle("My Order");
                getSupportFragmentManager().beginTransaction().replace(R.id.order_container,
                        new CheckoutFragment()).commit();

                //setContentView(R.layout.checkout_fragment);
                break;
            case R.id.logout:
                getLogout();
                break;
            case R.id.menu:
                getSupportActionBar().setTitle("Quix Order");
                getSupportFragmentManager().beginTransaction().replace(R.id.order_container,
                        new MenuFragment()).commit();
                break;
        }

        draw.closeDrawer(GravityCompat.END);
        return true;
    }

    // switched to public
    public void getLogout() {
        String p = "1234567";
        AlertDialog.Builder sure = new AlertDialog.Builder(TableActivity.this);
        final EditText pass = new EditText(TableActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        pass.setLayoutParams(lp);
        sure.setView(pass);

        sure.setTitle("Logout?");
        sure.setMessage("Enter Password");
        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        sure.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (pass.getText().toString().toUpperCase().equals(p.toUpperCase())) {
                    startActivity(new Intent(TableActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void onBackPressed() {
        if (draw.isDrawerOpen(GravityCompat.END)) {
            draw.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.call_server_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                callServer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void callServer()
    {
        Log.d("Call", "Call Server");
        Map<String, Object> call = new HashMap<>();
        call.put("table", curr);
        call.put("server", server);
        call.put("startTime", new Date());
        call.put("status", "server called");
        call.put("servedTime", null);

        callList.whereEqualTo("table", curr).whereEqualTo("servedTime", null)
                .get()
                .addOnSuccessListener(task -> {
                    if (task.getDocuments().size() != 0) {
                        Toast.makeText(this, "Server will help soon", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        fb.collection("table_calls").add(call);
                        Toast.makeText(this, "Server has been called", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });
    }
}
