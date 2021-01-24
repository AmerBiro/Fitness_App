package com.example.fitnessapp.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fitnessapp.R;
import com.example.fitnessapp.mvvm.model.ExerciseListModel;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder> {

    private List<ExerciseListModel> exerciseListModels;
    private OnExerciseListItemClicked onExerciseListItemClicked;

    public ExerciseListAdapter(OnExerciseListItemClicked onExerciseListItemClicked) {
        this.onExerciseListItemClicked = onExerciseListItemClicked;
    }

    public void setExerciseListModels(List<ExerciseListModel> exerciseListModels) {
        this.exerciseListModels = exerciseListModels;
    }

    @NonNull
    @Override
    public ExerciseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_exercise, parent, false);
        return new ExerciseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseListViewHolder holder, int position) {
        String image_url, exercise_name;
        long exercise_number, reps, rest, sets, start_weight;

        exercise_number = exerciseListModels.get(position).getNumber();
        exercise_name = exerciseListModels.get(position).getName();
        sets = exerciseListModels.get(position).getSets();
        reps = exerciseListModels.get(position).getReps();
        rest = exerciseListModels.get(position).getRest();
        start_weight = exerciseListModels.get(position).getStart_weight();
        image_url = exerciseListModels.get(position).getImage_url();


        holder.exercise_number_and_name.setText(exercise_number + ", " + exercise_name);
        holder.reps.setText(reps + "");
        holder.rest.setText(rest + "");
        holder.sets.setText(sets + "");
        holder.start_weight.setText(start_weight + "");

        Glide
                .with(holder.image)
                .load(image_url)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(holder.image);


    }


    @Override
    public int getItemCount() {
        if (exerciseListModels == null) {
            return 0;
        } else {
            return exerciseListModels.size();
        }
    }


    public class ExerciseListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView exercise_number_and_name, reps, rest, sets, start_weight;

        public ExerciseListViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.exercise_image);
            exercise_number_and_name = itemView.findViewById(R.id.exercise_number_and_name);
            reps = itemView.findViewById(R.id.reps);
            rest = itemView.findViewById(R.id.rest);
            sets = itemView.findViewById(R.id.sets);
            start_weight = itemView.findViewById(R.id.start_weight);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onExerciseListItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnExerciseListItemClicked {
        public void onItemClicked(int position);
    }

}
