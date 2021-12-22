package com.example.testontouchlictener.presentation.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testontouchlictener.R;
import com.example.testontouchlictener.data.Worlds;
import com.example.testontouchlictener.databinding.FragmentChoiseLevelBinding;
import com.example.testontouchlictener.presentation.adapters.AdapterChoiceWorld;

public class ChoiceLevelFragment extends Fragment {

    private FragmentChoiseLevelBinding binding;
    private RecyclerView recyclerView;
    private AdapterChoiceWorld adapter;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChoiseLevelBinding.inflate(LayoutInflater.from(requireParentFragment().getContext()));
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        adapter = new AdapterChoiceWorld(Worlds.getWorlds(), requireActivity());
        layoutManager = new LinearLayoutManager(requireActivity());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
}