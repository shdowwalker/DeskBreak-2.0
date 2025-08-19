package com.s23010285.desk.ui.workout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.s23010285.desk.R;
import com.s23010285.desk.model.WorkoutSchedule;
import java.util.List;

public class WorkoutLibraryFragment extends Fragment {

    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;
    private List<WorkoutSchedule> workoutSchedules;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout_library, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerViewWorkouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Load workout schedules
        workoutSchedules = WorkoutSchedule.getAllWorkoutSchedules();
        
        // Set up adapter
        adapter = new WorkoutAdapter(workoutSchedules, workout -> {
            // Handle workout selection
            Intent intent = new Intent(getActivity(), WorkoutSessionActivity.class);
            intent.putExtra("workout_id", workout.getId());
            intent.putExtra("workout_name", workout.getName());
            intent.putExtra("workout_duration", workout.getDurationMinutes());
            startActivity(intent);
        });
        
        recyclerView.setAdapter(adapter);
        
        return view;
    }

    // Workout Adapter
    private static class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
        
        private List<WorkoutSchedule> workouts;
        private OnWorkoutClickListener listener;

        public WorkoutAdapter(List<WorkoutSchedule> workouts, OnWorkoutClickListener listener) {
            this.workouts = workouts;
            this.listener = listener;
        }

        @NonNull
        @Override
        public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_workout_schedule, parent, false);
            return new WorkoutViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
            WorkoutSchedule workout = workouts.get(position);
            holder.bind(workout);
        }

        @Override
        public int getItemCount() {
            return workouts.size();
        }

        class WorkoutViewHolder extends RecyclerView.ViewHolder {
            private MaterialCardView cardView;
            private TextView tvName, tvPurpose, tvDuration, tvSchedule, tvCategory;

            public WorkoutViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardWorkout);
                tvName = itemView.findViewById(R.id.tvWorkoutName);
                tvPurpose = itemView.findViewById(R.id.tvWorkoutPurpose);
                tvDuration = itemView.findViewById(R.id.tvWorkoutDuration);
                tvSchedule = itemView.findViewById(R.id.tvWorkoutSchedule);
                tvCategory = itemView.findViewById(R.id.tvWorkoutCategory);
            }

            public void bind(WorkoutSchedule workout) {
                tvName.setText(workout.getName());
                tvPurpose.setText(workout.getPurpose());
                tvDuration.setText(workout.getDurationMinutes() + " min");
                tvSchedule.setText(workout.getSchedule());
                tvCategory.setText(workout.getCategory());

                cardView.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onWorkoutClick(workout);
                    }
                });
            }
        }
    }

    interface OnWorkoutClickListener {
        void onWorkoutClick(WorkoutSchedule workout);
    }
}
