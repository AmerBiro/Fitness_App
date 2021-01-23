package com.example.fitnessapp.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.mvvm.model.DayListModel;

import java.util.List;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.DayListViewHolder> {

    private List<DayListModel> dayListModels;
    private OnDayListItemClicked onDayListItemClicked;

    public DayListAdapter(OnDayListItemClicked onDayListItemClicked) {
        this.onDayListItemClicked = onDayListItemClicked;
    }

    public void setDayListModels(List<DayListModel> dayListModels) {
        this.dayListModels = dayListModels;
    }

    @NonNull
    @Override
    public DayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_day, parent, false);
        return new DayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayListViewHolder holder, int position) {
        String dayName, dayNumber, exerciseNumber;

        dayName = dayListModels.get(position).getDay_name();
        dayNumber = dayListModels.get(position).getDay_number();
        exerciseNumber = dayListModels.get(position).getDay_exercise_number();

        holder.dayNumberExercisse.setText("Day " + dayNumber + ", " + exerciseNumber + " exercise");
        holder.dayName.setText(dayName);

    }

    @Override
    public int getItemCount() {
        if (dayListModels == null) {
            return 0;
        } else {
            return dayListModels.size();
        }
    }


    public class DayListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView dayNumberExercisse, dayName;

        public DayListViewHolder(@NonNull View itemView) {
            super(itemView);

            dayNumberExercisse = itemView.findViewById(R.id.day_list_single_item_day_exercises);
            dayName = itemView.findViewById(R.id.day_list_single_item_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDayListItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnDayListItemClicked {
        public void onItemClicked(int position);
    }

}
