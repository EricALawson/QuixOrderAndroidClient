package com.example.quixorder.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MenuItem} and makes a call to the
 * specified {@link CheckoutInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>{

    private List<MenuItem> mValues = new ArrayList<MenuItem>();
    //OnRemoveMenuItemListener mListener;
    private final CheckoutInteractionListener mListener;
    private double subtotal = 20.00;



    public CheckoutAdapter(List<MenuItem> items/*, Context here*/, CheckoutInteractionListener listener){/* ArrayList name,*/// ArrayList imgs) {
        mValues = items;
        mListener = listener;
        for(int i = 0; i < mValues.size()-1; i++) {
            subtotal += this.mValues.get(i).getPrice();
        }
    }

    @Override
    public CheckoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        return new CheckoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CheckoutAdapter.ViewHolder holder, int position) {
        //holder.setData();
        //holder.mItem = mValues.get(position);
        //holder.itemName.setText(mValues.get(position).getName());
        // holder.priceView.setText(String.valueOf(mValues.get(position).getPrice()));
        holder.bindMenuItem(mValues.get(position));
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView itemName;
        public final ImageView itemPic;
        public final ImageView removeIcon;
        public final ImageView addIcon;
        public final TextView priceView;
        public double total = 0.00;
        public TextView totalView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            itemName = (TextView) view.findViewById(R.id.item_name);
            itemPic = (ImageView) view.findViewById(R.id.icon);
            priceView = (TextView) view.findViewById(R.id.price);
            totalView = (TextView) view.findViewById(R.id.sub);
            addIcon = (ImageView) view.findViewById(R.id.add);
            removeIcon = (ImageView) view.findViewById(R.id.remove);
            //totalView.setText("$"+total);
        }

        public void bindMenuItem(MenuItem item) {
            //String pic = item.getImage();
            itemName.setText(item.getName());
            Picasso.get().load(item.getImage()).into(itemPic);
            priceView.setText("$" + String.valueOf(item.getPrice()));
        }

      /*  @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.imageView2:
                    CheckoutInteractionListener.onRemoveMenuItemClick(getAdapterPosition(), mValues.get(getAdapterPosition()).getName());
            }
        }*/

    }


    public interface OnRemoveMenuItemListener {
        void onRemoveMenuItemClick(int position, String itemName);
    }
}

