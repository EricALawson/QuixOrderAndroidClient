package com.example.quixorder.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.fragment.CheckoutFragment.CheckoutInteractionListener;
//import com.example.quixorder.R;
//import com.example.quixorder.dummy.DummyContent.DummyItem;
import com.example.quixorder.model.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MenuItem} and makes a call to the
 * specified {@link CheckoutInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>{

    private List<MenuItem> mValues = new ArrayList<MenuItem>();
    private List<Integer> mQuantities = new ArrayList<>();
    //OnRemoveMenuItemListener mListener;
    private final CheckoutInteractionListener mListener;
    private double subtotal = 20.00;

    // Click listeners
    private OnAddQuantityListener mOnAddQuantityListener;
    private OnRemoveQuantityListener mOnRemoveQuantityListener;


    public CheckoutAdapter(List<MenuItem> items, List<Integer> quantities/*, Context here*/, CheckoutInteractionListener listener, OnAddQuantityListener onAddQuantityListener, OnRemoveQuantityListener onRemoveQuantityListener){/* ArrayList name,*/// ArrayList imgs) {
        mValues = items;
        mQuantities = quantities;
        mListener = listener;
        mOnAddQuantityListener = onAddQuantityListener;
        mOnRemoveQuantityListener = onRemoveQuantityListener;
        for(int i = 0; i < mValues.size()-1; i++) {
            subtotal += this.mValues.get(i).getPrice();
        }
    }

    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new CheckoutAdapter.ViewHolder(view, mOnAddQuantityListener, mOnRemoveQuantityListener);
    }

    @Override
    public void onBindViewHolder(final CheckoutAdapter.ViewHolder holder, int position) {
        //holder.setData();
        //holder.mItem = mValues.get(position);
        //holder.itemName.setText(mValues.get(position).getName());
        // holder.priceView.setText(String.valueOf(mValues.get(position).getPrice()));
        holder.bindMenuItem(mValues.get(position), mQuantities.get(position));
        holder.removeIcon.setImageResource(R.drawable.ic_remove_circle_dk);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                   // mListener.checkoutInteraction(holder.removeIcon);

                    switch(v.getId()) {
                        case R.id.remove:
                            mValues.remove(mValues.get(position));
                        case R.id.add:
                            mValues.add(mValues.get(position));
                    }
                }
            }
        });
    }

    //@Override
    // public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    //    super.onAttachedToRecyclerView(recyclerView);
    // }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView itemName;
        public final ImageView itemPic;
        public final ImageView removeIcon;
        public final ImageView addIcon;
        public final TextView priceView;
        public final TextView qtyView;
        public double total = 0.00;
        public TextView totalView;

        // Listeners
        public OnAddQuantityListener onAddQuantityListener;
        public OnRemoveQuantityListener onRemoveQuantityListener;

        public ViewHolder(View view, OnAddQuantityListener onAddQuantityListener, OnRemoveQuantityListener onRemoveQuantityListener) {
            super(view);
            mView = view;
            itemName = (TextView) view.findViewById(R.id.item_name);
            itemPic = (ImageView) view.findViewById(R.id.icon);
            priceView = (TextView) view.findViewById(R.id.price);
            totalView = (TextView) view.findViewById(R.id.sub);
            addIcon = (ImageView) view.findViewById(R.id.add);
            qtyView = (TextView) view.findViewById(R.id.qty);
            removeIcon = (ImageView) view.findViewById(R.id.remove);
            //totalView.setText("$"+total);

            // Load click listeners
            this.onAddQuantityListener = onAddQuantityListener;
            addIcon.setOnClickListener(this);
            this.onRemoveQuantityListener = onRemoveQuantityListener;
            removeIcon.setOnClickListener(this);
        }

        public void bindMenuItem(MenuItem item, int qty) {
            //String pic = item.getImage();
            itemName.setText(item.getName());
            qtyView.setText(Integer.toString(qty));
            Picasso.get().load(item.getImage()).into(itemPic);
            priceView.setText("$" + String.valueOf(item.getPrice()));
        }

      @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.add:
                    int newQuantity = Integer.parseInt(qtyView.getText().toString()) + 1;
                    onAddQuantityListener.onAddQuantityClick(getAdapterPosition(), newQuantity);
                    break;
                case R.id.remove:
                    newQuantity = Integer.parseInt(qtyView.getText().toString()) - 1;
                    onRemoveQuantityListener.onRemoveQuantityClick(getAdapterPosition(), newQuantity);
                    break;
            }
        }
    }


    public interface OnAddQuantityListener {
        void onAddQuantityClick(int position, int quantity);
    }

    public interface OnRemoveQuantityListener {
        void onRemoveQuantityClick(int position, int quantity);
    }

}

