package com.example.quixorder.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quixorder.fragment.MenuFragment;
import com.example.quixorder.R;
import com.example.quixorder.adapter.MenuAdapter;
//import com.example.quixorder.fragment.MenuFragment;
import com.example.quixorder.model.MenuItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    //MenuItemContent db = new MenuItemContent();
    MenuFragment.OnListFragmentInteractionListener listen;

    private FirebaseFirestore fb;
    List<MenuItem> db = new ArrayList<MenuItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        for (int i = 1; i <= 10; i++) {
            MenuItem x = new MenuItem("", "ItemPic", "Item " + (i+1), Double.valueOf(i), "drink");
            db.add(x);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //arrayList = new ArrayList();
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        MenuAdapter ad = new MenuAdapter( db, TableActivity.this, listen);
        recyclerView.setAdapter(ad);

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

        sure.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(pass.getText().toString().toUpperCase().equals(p.toLowerCase()))
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
