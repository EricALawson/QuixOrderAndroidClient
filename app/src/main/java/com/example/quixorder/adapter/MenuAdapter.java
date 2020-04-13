package com.example.quixorder.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quixorder.R;
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
    //CollectionReference fdList = fb.collection("menu_items");
    //private final Context c;
    //private final ArrayList<String> menuNames;
    // private final ArrayList menuPics;
    private final OnListFragmentInteractionListener mListener;

    public MenuAdapter(List<MenuItem> items/*, Context here*/, OnListFragmentInteractionListener listener){/* ArrayList name,*/// ArrayList imgs) {
        mValues = items;
        //c = here;
        mListener = listener;
        //menuNames = name;
        //menuPics = imgs;
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
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
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

       /* public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
//                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }*/

    }
}

