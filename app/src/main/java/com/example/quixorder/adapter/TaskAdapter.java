package com.example.quixorder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quixorder.R;
import com.example.quixorder.model.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Order> taskList;
    private String type;

    public TaskAdapter(ArrayList<Order> taskList, String type) {
        this.taskList = taskList;
        this.type = type;
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

        long startTime = 0, endTime = 0, totalTime = 0;

        // Set views
        viewHolder.taskName.setText("Task " + (i + 1));

        if (type.equals("Server")) {
            if (currentItem.getCookedTime() != null && currentItem.getServedTime() != null) {
                startTime = currentItem.getCookedTime().getTime();
                endTime = currentItem.getServedTime().getTime();
            }
        } else if (type.equals("Cook")){
            if (currentItem.getStartTime() != null && currentItem.getCookedTime() != null) {
                startTime = currentItem.getStartTime().getTime();
                endTime = currentItem.getCookedTime().getTime();
            }
        }

        totalTime = endTime - startTime;

        if (totalTime != 0) {
            viewHolder.startTime.setText(toDate(startTime));
            viewHolder.endTime.setText(toDate(endTime));
            viewHolder.totalTime.setText(toTime(totalTime));
        } else {
            viewHolder.startTime.setText(toDate(startTime));
            viewHolder.endTime.setText("incomplete");
            viewHolder.totalTime.setText("incomplete");
        }
    }

    public String toTime(long time) {
        String hms = String.format("%02dH %02dM %02dS", TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1)
        );
        return hms;
    }

    public String toDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String date = String.format("%02d/%02d/%02d %02d:%02d:%02d", calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND)
        );
        return date;
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
