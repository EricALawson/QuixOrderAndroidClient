package com.example.quixorder;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quixorder.MenuFragment.OnListFragmentInteractionListener;
import com.example.quixorder.dummy.DummyContent.DummyItem;
import com.example.quixorder.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMenuAdapter extends RecyclerView.Adapter<MyMenuAdapter.ViewHolder> {

    private final List<MenuItem> mValues;
    private final Context c;
    //private final ArrayList<String> menuNames;
    private final ArrayList menuPics;
    private final OnListFragmentInteractionListener mListener;

    public MyMenuAdapter(List<MenuItem> items, Context here, OnListFragmentInteractionListener listener,/* ArrayList name,*/ ArrayList imgs) {
        mValues = items;
        c = here;
        mListener = listener;
        //menuNames = name;
        menuPics = imgs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      //holder.setData();
        //holder.mItem = mValues.get(position);
        holder.itemName.setText(mValues.get(position).getName());
        holder.priceView.setText(String.valueOf(mValues.get(position).price));
        holder.itemPic.setImageResource((int) menuPics.get(position));
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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView itemName;
        public final ImageView itemPic;
        public final TextView priceView;
        public MenuItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            itemName = (TextView) view.findViewById(R.id.item_name);
            itemPic= (ImageView) view.findViewById(R.id.icon);
            priceView = (TextView) view.findViewById(R.id.price);
        }

    }
}
