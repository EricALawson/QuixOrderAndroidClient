package com.example.quixorder.adapter.server;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quixorder.R;
import com.example.quixorder.adapter.cook.OrderListAdapter;
import com.example.quixorder.model.Order;
import com.example.quixorder.model.TableCall;

import java.util.List;

public class ServerOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IServerTask> tasks;



    public ServerOrderListAdapter(List<IServerTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == IServerTask.ORDER) {
            ViewGroup v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.server_order, parent, false);
            return new ServerOrderViewHolder(v);
        } else { //if (viewType == TABLE_CALL)
            ViewGroup v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.server_call, parent, false);
            return new CallViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == IServerTask.ORDER) {
            ((ServerOrderViewHolder) holder).bindOrder((Order)tasks.get(position));
        } else if (getItemViewType(position) == IServerTask.TABLE_CALL) {
            ((CallViewHolder) holder).bindCall((TableCall) tasks.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return tasks.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}