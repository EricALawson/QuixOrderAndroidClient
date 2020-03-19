package com.example.quixorder.activity;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.quixorder.R;
import com.example.quixorder.fragment.AddEmployeeFragment;
import com.example.quixorder.fragment.AssignTableFragment;
import com.example.quixorder.fragment.DailyTotalFragment;
import com.example.quixorder.fragment.EditMenuFragment;
import com.example.quixorder.fragment.EmployeeActivityFragment;


public class OwnerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daily Total");

        drawer = findViewById(R.id.owner_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DailyTotalFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_daily_total);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_daily_total:
                getSupportActionBar().setTitle("Daily Total");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DailyTotalFragment()).commit();
                break;
            case R.id.nav_employee_activity:
                getSupportActionBar().setTitle("Employee Activity");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EmployeeActivityFragment()).commit();
                break;
            case R.id.nav_edit_menu:
                getSupportActionBar().setTitle("Edit Menu");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EditMenuFragment()).commit();
                break;
            case R.id.nav_add_employee:
                getSupportActionBar().setTitle("Add Employee");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddEmployeeFragment()).commit();
                break;
            case R.id.nav_assign_table:
                getSupportActionBar().setTitle("Assign Table");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AssignTableFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                startActivity(new Intent(OwnerActivity.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
