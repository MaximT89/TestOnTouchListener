package com.example.testontouchlictener.presentation;

import static com.example.testontouchlictener.utils.GeneratorAnimation.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.testontouchlictener.R;
import com.example.testontouchlictener.databinding.FragmentStartBinding;
import com.example.testontouchlictener.utils.GeneratorAnimation;

import java.util.Objects;


public class StartFragment extends Fragment {

    private FragmentStartBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartBinding.inflate(LayoutInflater.from(requireParentFragment().getContext()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        makeShakeAnimation(requireActivity(), binding.letter1, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter2, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter3, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter4, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter5, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter6, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter7, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter8, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter9, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter10, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter11, getRandomDuration());
        makeShakeAnimation(requireActivity(), binding.letter12, getRandomDuration());

        binding.btnGoGame.setOnClickListener(view -> {
            navController.navigate(R.id.action_startFragment_to_gameFragment, null, new NavOptions.Builder()
                    .setEnterAnim(R.anim.anim_show)
                    .setExitAnim(R.anim.anim_hide)
                    .setPopEnterAnim(R.anim.anim_show)
                    .setPopExitAnim(R.anim.anim_hide)
                    .build());
        });

        binding.btnGoSelectLevel.setOnClickListener(view -> {
            navController
                    .navigate(R.id.action_startFragment_to_choiseLevelFragment, null, new NavOptions.Builder()
                    .setEnterAnim(R.anim.anim_show)
                    .setExitAnim(R.anim.anim_hide)
                    .setPopEnterAnim(R.anim.anim_show)
                    .setPopExitAnim(R.anim.anim_hide)
                    .build());
        });


    }
}