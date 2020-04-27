package com.example.quixorder.adapter;

import android.content.ClipData;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.model.Account;
import com.example.quixorder.model.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> implements View.OnTouchListener {
    private ArrayList<Table> tableList;

    public TableAdapter(ArrayList<Table> tableList) {
        this.tableList = tableList;
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.table_item, viewGroup, false);
        ViewHolder evh = new ViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Account currentItem = tableList.get(i);

        // Set views
        viewHolder.textView1.setText(currentItem.getUsername());
        viewHolder.cardView.setOnTouchListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("onTouch", "click");
                ClipData data = ClipData.newPlainText("username", ((TextView)view.findViewById(R.id.textView1)).getText().toString());
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
        }
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView textView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            textView1 = itemView.findViewById(R.id.textView1);
        }
    }
}
