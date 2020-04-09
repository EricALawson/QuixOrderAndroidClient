package com.example.quixorder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.model.Order;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Order> taskList;

    public TaskAdapter(ArrayList<Order> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        ViewHolder evh = new ViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Order currentItem = taskList.get(i);

        // Set views
        viewHolder.taskName.setText("Task " + (i + 1));
        viewHolder.startTime.setText(currentItem.getStartTime().toString());
        viewHolder.endTime.setText(currentItem.getCookedTime().toString());
        viewHolder.totalTime.setText(toTime(currentItem.getCookedTime().getTime() - currentItem.getStartTime().getTime()));
    }

    public String toTime(long time) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1));
        return hms;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName;
        private TextView startTime;
        private TextView endTime;
        private TextView totalTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.taskName);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            totalTime = itemView.findViewById(R.id.totalTime);
        }
    }
}
