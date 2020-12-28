package com.example.fitnessapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProgramListAdapter extends RecyclerView.Adapter <ProgramListAdapter.ProgramViewHolder> {
    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
