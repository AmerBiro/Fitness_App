package com.example.fitnessapp.program;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ProgramProgramListViewBinding;
import com.example.fitnessapp.functions.AlertDialogShower;
import com.example.fitnessapp.mvvm.adapter.ProgramListAdapter;
import com.example.fitnessapp.mvvm.model.ProgramListModel;
import com.example.fitnessapp.mvvm.viewmodel.ProgramListViewModel;
import com.example.fitnessapp.program.functions.DeleteProgram;
import com.example.fitnessapp.program.functions.GetProgramData;

import java.util.List;

public class ProgramListView extends Fragment implements ProgramListAdapter.OnProgramListItemClicked, View.OnClickListener {

    private @NonNull
    ProgramProgramListViewBinding binding;

    private NavController controller;
    private ProgramListViewModel programListViewModel;
    private List<ProgramListModel> programListModelss;
    private DeleteProgram deleteProgram;
    private int position;
    private GetProgramData getProgramData;
    private RecyclerView recyclerView;
    private ProgramListAdapter adapter;
    private AlertDialogShower shower;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProgramProgramListViewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        shower = new AlertDialogShower();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        recyclerViewSetup();
        onSwiped();
    }



    @Override
    public void onStart() {
        super.onStart();
        binding.addProgram.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        programListViewModel = new ViewModelProvider(getActivity()).get(ProgramListViewModel.class);
        programListViewModel.getProgramListModelData().observe(getViewLifecycleOwner(), new Observer<List<ProgramListModel>>() {
            @Override
            public void onChanged(List<ProgramListModel> programListModels) {
                programListModelss = programListModels;
                adapter.setProgramListModels(programListModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
//        Log.d(TAG, "onChanged: " + position + ", " + programListId);
        ProgramListViewDirections.ActionHome2ToDayListViwer action =
                ProgramListViewDirections.actionHome2ToDayListViwer();
        action.setPosition(position);
        action.setProgramListId(programListModelss.get(position).getProgramListId());
        controller.navigate(action);
    }

    private void recyclerViewSetup() {
        recyclerView = binding.recyclerview;
        adapter = new ProgramListAdapter(this);
        deleteProgram = new DeleteProgram(getActivity(), adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void onSwiped(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == 4){
                    getProgramData = new GetProgramData(programListModelss, viewHolder.getAdapterPosition());

                    deleteProgram.deleteProgram(
                            getProgramData.getProgramListId(),
                            getProgramData.getNumber() + ", " + getProgramData.getProgramName(),
                            getProgramData.getStart_date(),
                            getProgramData.getEnd_date());
                }else{
                    ProgramListViewDirections.ActionHome2ToCurrentProgramViwer action =
                            ProgramListViewDirections.actionHome2ToCurrentProgramViwer();
                    action.setPosition(viewHolder.getAdapterPosition());
                    controller.navigate(action);
                }

            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_program:
                controller.navigate(R.id.action_home2_to_createProgram);
                break;
            default:
        }
    }
}