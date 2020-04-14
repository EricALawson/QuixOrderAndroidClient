package com.example.quixorder.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quixorder.fragment.MenuFragment;
import com.example.quixorder.R;
import com.example.quixorder.adapter.MenuAdapter;
//import com.example.quixorder.fragment.MenuFragment;
import com.example.quixorder.model.MenuItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class TableActivity extends AppCompatActivity {

    //MenuItemContent db = new MenuItemContent();
    MenuFragment.OnListFragmentInteractionListener listen;

    private FirebaseFirestore fb = FirebaseFirestore.getInstance();
    CollectionReference fdList;
    List<MenuItem> db = new ArrayList<MenuItem>();
    private String account_name;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        account_name = getIntent().getStringExtra("username");

        setContentView(R.layout.table);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        fb.collection("menu_items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        db.add(document.toObject(MenuItem.class));
                        Log.d("Good", document.getId() + " => " + document.getData());
                        MenuAdapter ad = new MenuAdapter( db,  listen);
                        recyclerView.setAdapter(ad);
                    }
                } else {
                    Log.d("Bad", "Error getting documents: ", task.getException());
                }
            }
        });
        //MenuAdapter ad = new MenuAdapter( db,  listen);
        //recyclerView.setAdapter(ad);
        //arrayList = new ArrayList();
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        //final String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
        //fdList = fb.collection("menu_items");

        findViewById(R.id.lOut).setOnClickListener(logOut);
    }

        private View.OnClickListener logOut = new View.OnClickListener() {

        @Override
        public void onClick(View view)
        {
            //setContentView(R.layout.login);
            //Intent main = new Intent(CookActivity.this, LoginActivity.class);
            getLogout();
            //startActivity(new Intent(TableActivity.this, LoginActivity.class));
            //finish();


        }
    };

    private void getLogout()
    {

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
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(pass.getText().toString().toUpperCase().equals(p.toUpperCase()))
                {
                    startActivity(new Intent(TableActivity.this, LoginActivity.class));
                    finish();
                }
                else
                {
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
}
