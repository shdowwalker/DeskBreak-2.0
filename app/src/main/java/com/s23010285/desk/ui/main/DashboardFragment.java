package com.s23010285.desk.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.s23010285.desk.R;
import com.s23010285.desk.ui.workout.WorkoutSessionActivity;

/**
 * Dashboard fragment displaying user's fitness stats and quick actions
 */
public class DashboardFragment extends Fragment {

    private TextView tvSteps, tvActiveMinutes, tvTodayProgress;
    private Button btnStartWorkout, btnStartBreak;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        
        setupViews(view);
        setupClickListeners();
        
        return view;
    }

    private void setupViews(View view) {
        tvSteps = view.findViewById(R.id.tvSteps);
        tvActiveMinutes = view.findViewById(R.id.tvActiveMinutes);
        tvTodayProgress = view.findViewById(R.id.tvTodayProgress);
        btnStartWorkout = view.findViewById(R.id.btnStartWorkout);
        btnStartBreak = view.findViewById(R.id.btnStartBreak);
    }

    private void setupClickListeners() {
        // Start Workout button
        btnStartWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WorkoutSessionActivity.class);
            startActivity(intent);
        });

        // Start Break button
        btnStartBreak.setOnClickListener(v -> {
            // TODO: Implement break functionality
            tvTodayProgress.setText("Break started! Take a walk.");
        });
    }
}
