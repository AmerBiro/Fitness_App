package com.example.fitnessapp.mvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;
import com.example.fitnessapp.mvvm.model.ProgramListModel;


import java.util.List;

public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.ProgramViewHolder> {

    private List<ProgramListModel> programListModels;

    public void setProgramListModels(List<ProgramListModel> programListModels) {
        this.programListModels = programListModels;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_programs, parent, false);
        return new ProgramViewHolder(view);
    }

    public void deleteProgram(int position){

    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        String program_name, days, exercises, start_date, end_date, image_url;

        program_name = programListModels.get(position).getProgram_name();
        days = programListModels.get(position).getDays();
        exercises = programListModels.get(position).getExercises();
        start_date = programListModels.get(position).getStart_date();
        end_date = programListModels.get(position).getEnd_date();
        image_url = programListModels.get(position).getProgram_Image_url();

        if (program_name.length() > 25){
            program_name = program_name.substring(0, 25);
            program_name = program_name + "...";
        }

        holder.program_name.setText(program_name);
        holder.days_exercises.setText(days + " days" + " with " + exercises + " exercise");
        holder.start_date.setText("From: " + start_date);
        holder.end_date.setText("To:     " + end_date);

        Glide
                .with(holder.itemView.getContext())
                .load(image_url)
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

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        private TextView program_name, days_exercises, start_date, end_date;
        private LinearLayout button;
        private ImageView imageView;


        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);

            program_name = itemView.findViewById(R.id.program_list_program_name);
            imageView = itemView.findViewById(R.id.program_list_image_placeholder);
            days_exercises = itemView.findViewById(R.id.program_list_days_exercises);
            start_date = itemView.findViewById(R.id.program_list_start_date);
            end_date = itemView.findViewById(R.id.program_list_end_date);
            button = itemView.findViewById(R.id.program_list_button);

        }
    }
}
