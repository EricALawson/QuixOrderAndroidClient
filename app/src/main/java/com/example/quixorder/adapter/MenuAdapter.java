package com.example.quixorder.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quixorder.R;
import com.example.quixorder.activity.TableActivity;
import com.example.quixorder.fragment.MenuFragment.OnListFragmentInteractionListener;
//import com.example.quixorder.R;
//import com.example.quixorder.dummy.DummyContent.DummyItem;
import com.example.quixorder.model.MenuItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MenuItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private FirebaseFirestore fb;
    private final List<MenuItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MenuAdapter(List<MenuItem> items/*, Context here*/, OnListFragmentInteractionListener listener){
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.setData();
        //holder.mItem = mValues.get(position);
        //holder.itemName.setText(mValues.get(position).getName());
       // holder.priceView.setText(String.valueOf(mValues.get(position).getPrice()));
        holder.bindMenuItem(mValues.get(position));

        holder.itemPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                // Add menu item to order

                    Toast.makeText(v.getContext(), "Clicked at "+position, Toast.LENGTH_SHORT).show();
               // }
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
        public final TextView priceView;
        //public MenuItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            itemName = (TextView) view.findViewById(R.id.item_name);
            itemPic= (ImageView) view.findViewById(R.id.icon);
            priceView = (TextView) view.findViewById(R.id.price);
        }

        public void bindMenuItem(MenuItem item)
        {
            //String pic = item.getImage();
            itemName.setText(item.getName());
            Picasso.get().load(item.getImage()).into(itemPic);
            priceView.setText("$"+String.valueOf(item.getPrice()));
        }
    }
}

