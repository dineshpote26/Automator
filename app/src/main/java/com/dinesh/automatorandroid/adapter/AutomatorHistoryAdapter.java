package com.dinesh.automatorandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinesh.automatorandroid.R;
import com.dinesh.automatorandroid.data.Automator;
import com.dinesh.automatorandroid.data.AutomatorHistory;

import java.util.ArrayList;
import java.util.List;

public class AutomatorHistoryAdapter extends RecyclerView.Adapter<AutomatorHistoryAdapter.ViewHolder> {

    List<AutomatorHistory> automatorHistories = new ArrayList<>();
    private onItemClickListner listner;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return automatorHistories.size();
    }

    public void setAutomatorHistory(List<AutomatorHistory> automatorHistories) {
        this.automatorHistories = automatorHistories;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView desc;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(automatorHistories.get(position));
                    }
                }
            });
        }

        public void bind(int position) {
            desc.setText(automatorHistories.get(position).getJsonData());
            date.setText(automatorHistories.get(position).getDate());
        }
    }

    public AutomatorHistory getAutmatorAt(int position) {
        return automatorHistories.get(position);
    }

    public interface onItemClickListner {
        void onItemClick(AutomatorHistory automator);
    }

    public void setOnItemClickListner(onItemClickListner listner) {
        this.listner = listner;
    }
}
