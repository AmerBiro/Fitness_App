package com.example.fitnessapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.ProgramListModel;


import java.util.List;

public class ProgramListAdapter extends RecyclerView.Adapter <ProgramListAdapter.ProgramViewHolder> {

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

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        holder.program_name.setText(programListModels.get(position).getProgram_name());
        holder.start_date.setText(programListModels.get(position).getStart_date());
    }

    @Override
    public int getItemCount() {
        if (programListModels == null){
            return 0;
        }else {
            return programListModels.size();
        }
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_placeholder;
        private TextView program_name, days, exercises, start_date, end_date;
        private LinearLayout button;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);

            image_placeholder = itemView.findViewById(R.id.program_list_image_placeholder);
            program_name = itemView.findViewById(R.id.program_list_program_name);
            days = itemView.findViewById(R.id.program_list_days);
            exercises = itemView.findViewById(R.id.program_list_exercises);
            start_date = itemView.findViewById(R.id.program_list_start_date);
            end_date = itemView.findViewById(R.id.program_list_end_date);
            button = itemView.findViewById(R.id.program_list_button);
        }
    }
}
