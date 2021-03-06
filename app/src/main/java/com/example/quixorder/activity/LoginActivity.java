package com.example.quixorder.activity;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quixorder.R;
import com.example.quixorder.model.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private FirebaseFirestore firebase = FirebaseFirestore.getInstance();


    public EditText usernameInput;
    public EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //some fix for firestore changes to timestamps.
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firebase.setFirestoreSettings(settings);

        findViewById(R.id.btn_login).setOnClickListener(btn_loginOnClickListener);
        //findViewById(R.id.lOut).setOnClickListener(logOut);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);

    }

    private OnClickListener btn_loginOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();

            firebase.collection("accounts").whereEqualTo("username", username).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("Login Task Failed", task.getException().getMessage());
                            } else if (task.getResult().size() != 1) {
                                Log.e("Login Failed", "Accounts with username: " + task.getResult().size());
                                Toast.makeText(getApplicationContext(), "Accounts with username: " + task.getResult().size(), Toast.LENGTH_SHORT).show();
                            } else {
                                Account act = task.getResult().getDocuments().get(0).toObject(Account.class);
                                if ( ! password.equals(act.getPassword())) {
                                    Log.e("Login Failed", "Password does not match");
                                    Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                                } else {
                                    switch(act.getType()) {
                                        case "Owner":
                                            startActivity(new Intent(LoginActivity.this, OwnerActivity.class).putExtra("username", act.getUsername()) );
                                            break;
                                        case "Customer":
                                            DocumentSnapshot customer = task.getResult().getDocuments().get(0);
                                            if (customer.get("server") == null) {
                                                Toast.makeText(getApplicationContext(), "This table is not assigned to a server yet", Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                            startActivity(new Intent(LoginActivity.this, TableActivity.class).putExtra("username", act.getUsername()) );
                                            break;
                                        case "Server":
                                            startActivity(new Intent(LoginActivity.this, ServerActivity.class).putExtra("username", act.getUsername()) );
                                            break;
                                        case "Cook":
                                            startActivity(new Intent(LoginActivity.this, CookActivity.class).putExtra("username", act.getUsername()) );
                                            break;
                                        default:
                                            Log.e("Login Failed", "Account type not recognized: " + act.getType());
                                            Toast.makeText(getApplicationContext(), "Invalid Account", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
        }
    };


}
