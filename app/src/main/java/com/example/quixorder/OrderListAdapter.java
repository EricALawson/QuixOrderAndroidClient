package com.example.quixorder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quixorder.model.MenuItem;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {
    private MenuItem[] orderItems;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public OrderViewHolder(View v) {
            super(v);
            //TODO
        }

    }

    public OrderListAdapter(MenuItem[] orderItems) {
        this.orderItems = orderItems;
        //TODO: Maybe
    }
    // Create new views (invoked by the layout manager)
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.my_text_view, parent, false);
        View v = new TextView();
        OrderViewHolder vh = new OrderViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //TODO

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderItems.length;
    }
}
