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
import com.example.fitnessapp.mvvm.model.ProgramModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class ProgramAdapter extends FirestoreRecyclerAdapter <ProgramModel, ProgramAdapter.ProgramViewHolder> {

    public ProgramAdapter(@NonNull FirestoreRecyclerOptions<ProgramModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProgramViewHolder holder, int position, @NonNull ProgramModel model) {
        String program_name, days, exercises, start_date, end_date, image_url;

        program_name = model.getProgram_name();
        days = model.getDays();
        exercises = model.getExercises();
        start_date = model.getStart_date();
        end_date = model.getEnd_date();
        image_url = model.getProgram_Image_url();

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

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_programs, parent, false);
        return new ProgramViewHolder(view);
    }

    class ProgramViewHolder extends RecyclerView.ViewHolder{
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

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public String getProgramId(){
        getSnapshots().getSnapshot(0).getReference();
        String programId = getSnapshots().getSnapshot(0).getReference().getId();
        return programId;
    }


}
