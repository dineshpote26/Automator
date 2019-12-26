package com.dinesh.automatorandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinesh.automatorandroid.R;
import com.dinesh.automatorandroid.data.Automator;

import java.util.ArrayList;
import java.util.List;

public class AutomatorAdapter extends RecyclerView.Adapter<AutomatorAdapter.ViewHolder> {

    List<Automator> automatorList = new ArrayList<>();
    private onItemClickListner listner;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return automatorList.size();
    }

    public void setAutomator(List<Automator> automators) {
        this.automatorList = automators;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView url;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            url = itemView.findViewById(R.id.automator_url);
            time = itemView.findViewById(R.id.automator_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(automatorList.get(position));
                    }
                }
            });
        }

        public void bind(int position) {
            url.setText(automatorList.get(position).getUrl());
            time.setText(automatorList.get(position).getScheduleTime());
        }
    }

    public Automator getAutmatorAt(int position) {
        return automatorList.get(position);
    }

    public interface onItemClickListner {
        void onItemClick(Automator automator);
    }

    public void setOnItemClickListner(onItemClickListner listner) {
        this.listner = listner;
    }
}
