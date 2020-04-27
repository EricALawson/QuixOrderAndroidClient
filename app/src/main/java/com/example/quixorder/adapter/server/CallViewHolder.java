package com.example.quixorder.adapter.server;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quixorder.R;
import com.example.quixorder.model.TableCall;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;

class CallViewHolder extends RecyclerView.ViewHolder {
    private TableCall call;
    Button btnDone;
    TextView tableNum;

    public CallViewHolder(@NonNull View itemView) {
        super(itemView);
        btnDone = itemView.findViewById(R.id.btnDone);
        tableNum = itemView.findViewById(R.id.tableNumber);
        btnDone.setOnClickListener(view -> {
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("table_calls").document(call.getDocumentID());
            HashMap<String, Object> data = new HashMap<>();
            data.put("status", "responded");
            data.put("servedTime", new Date());
            docRef.update(data);
        });
    }

    public void bindCall(TableCall c) {
        this.call = c;
        tableNum.setText("Requested at: " + call.getTable());

    }
}
