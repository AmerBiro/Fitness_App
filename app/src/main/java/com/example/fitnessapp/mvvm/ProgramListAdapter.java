package com.example.fitnessapp.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;

import java.util.List;

public class ProgramListAdapter extends RecyclerView.Adapter <ProgramListAdapter.StudentViewHolder> {

    private List<ProgramListModel> programListModels;

    public void setProgramListModels(List<ProgramListModel> programListModels) {
        this.programListModels = programListModels;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_list_single_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        String name, daysNumber, exercisesNumber, start_date, end_date;

        name = programListModels.get(position).getProgramName();
        daysNumber = programListModels.get(position).getDaysNumber();
        exercisesNumber = programListModels.get(position).getExercisesNumber();
        start_date = programListModels.get(position).getStart_date();
        end_date = programListModels.get(position).getEnd_date();

        if (name.length() > 25){
            name = name.substring(0, 25);
            name = name + "...";
        }

        holder.name.setText(name);
        holder.days_exercises.setText(daysNumber + " days, " + exercisesNumber + " exercise");
        holder.start_date.setText(start_date);
        holder.end_date.setText(end_date);

        Glide
                .with(holder.imageView)
                .load(programListModels.get(position).getImage_url())
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(programListModels == null){
            return 0;
        } else {
            return programListModels.size();
        }
    }


    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView name, days_exercises, start_date, end_date;
        private ImageView imageView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.program_list_single_item_name);
            days_exercises = itemView.findViewById(R.id.program_list_single_item_days_exercises);
            start_date = itemView.findViewById(R.id.program_list_single_item_start_date);
            end_date = itemView.findViewById(R.id.program_list_single_item_end_date);
            imageView = itemView.findViewById(R.id.program_list_single_item_image_placeholder);
        }
    }
}
