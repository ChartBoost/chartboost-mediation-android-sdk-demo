package com.example.chartboost.mediation.sdk.demo.java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private List<String> logs;

    public LogAdapter(final List<String> logs) {
        this.logs = new ArrayList<>(logs);
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        holder.logTextView.setText(logs.get(position));
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {
        public TextView logTextView;

        public LogViewHolder(View itemView) {
            super(itemView);
            logTextView = itemView.findViewById(R.id.logTextView);
        }
    }

    public void setLogs(final List<String> logs) {
        this.logs = new ArrayList<>(logs);
    }

    public void clearLogs() {
        this.logs = new ArrayList<>();
    }
}
