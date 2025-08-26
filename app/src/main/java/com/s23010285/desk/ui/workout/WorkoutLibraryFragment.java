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

/**
 * This fragment displays a library of available workout routines
 * Users can browse different workout programs and select one to start
 */
public class WorkoutLibraryFragment extends Fragment {

    // This variable holds the RecyclerView that displays the list of workouts
    private RecyclerView recyclerView;
    // This adapter helps display the workout data in the RecyclerView
    private WorkoutAdapter adapter;
    // This list holds all the available workout schedules
    private List<WorkoutSchedule> workoutSchedules;

    /**
     * This method is called when the workout library screen is created
     * It sets up the screen and loads all available workout programs
     * @param inflater Helps create the view from the layout file
     * @param container The parent view that will contain this fragment
     * @param savedInstanceState Any saved state from previous instances
     * @return The view that will be displayed to the user
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create the view from our layout file
        View view = inflater.inflate(R.layout.fragment_workout_library, container, false);
        
        // Find the RecyclerView that will display our workout list
        recyclerView = view.findViewById(R.id.recyclerViewWorkouts);
        // Set the layout manager to display workouts in a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        // Load all available workout schedules from our predefined list
        workoutSchedules = WorkoutSchedule.getAllWorkoutSchedules();
        
        // Create an adapter to display the workouts and handle user taps
        adapter = new WorkoutAdapter(workoutSchedules, workout -> {
            // Handle workout selection - when a user taps on a workout
            // Create an intent to open the workout session screen
            Intent intent = new Intent(getActivity(), WorkoutSessionActivity.class);
            // Pass the workout information to the session screen
            intent.putExtra("workout_id", workout.getId());
            intent.putExtra("workout_name", workout.getName());
            intent.putExtra("workout_duration", workout.getDurationMinutes());
            // Start the workout session
            startActivity(intent);
        });
        
        // Set the adapter to the RecyclerView so it can display the workouts
        recyclerView.setAdapter(adapter);
        
        // Return the view so it can be displayed
        return view;
    }

    /**
     * This adapter class helps display workout data in the RecyclerView
     * It creates workout cards and handles when users tap on them
     */
    private static class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
        
        // This list holds all the workouts we want to display
        private List<WorkoutSchedule> workouts;
        // This listener is called when a user taps on a workout
        private OnWorkoutClickListener listener;

        /**
         * Constructor for the adapter
         * @param workouts The list of workouts to display
         * @param listener The listener that handles workout selection
         */
        public WorkoutAdapter(List<WorkoutSchedule> workouts, OnWorkoutClickListener listener) {
            this.workouts = workouts;
            this.listener = listener;
        }

        /**
         * This method creates new workout card views
         * It's called when the RecyclerView needs to display a new workout
         * @param parent The parent view group
         * @param viewType The type of view to create
         * @return A new WorkoutViewHolder
         */
        @NonNull
        @Override
        public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Create a new view from our workout card layout
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_workout_schedule, parent, false);
            return new WorkoutViewHolder(view);
        }

        /**
         * This method fills a workout card with data
         * It's called when the RecyclerView wants to display a workout at a specific position
         * @param holder The view holder to fill with data
         * @param position The position of the workout in our list
         */
        @Override
        public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
            // Get the workout at this position and bind it to the view holder
            WorkoutSchedule workout = workouts.get(position);
            holder.bind(workout);
        }

        /**
         * This method tells the RecyclerView how many workouts we have
         * @return The total number of workouts to display
         */
        @Override
        public int getItemCount() {
            return workouts.size();
        }

        /**
         * This class holds references to all the UI elements in a workout card
         * It helps us efficiently update the card's content
         */
        class WorkoutViewHolder extends RecyclerView.ViewHolder {
            // This is the main card view that contains all the workout information
            private MaterialCardView cardView;
            // These text views display different details about the workout
            private TextView tvName, tvPurpose, tvDuration, tvSchedule, tvCategory;

            /**
             * Constructor for the view holder
             * @param itemView The view that represents one workout card
             */
            public WorkoutViewHolder(@NonNull View itemView) {
                super(itemView);
                // Find all the UI elements in our workout card layout
                cardView = itemView.findViewById(R.id.cardWorkout);
                tvName = itemView.findViewById(R.id.tvWorkoutName);
                tvPurpose = itemView.findViewById(R.id.tvWorkoutPurpose);
                tvDuration = itemView.findViewById(R.id.tvWorkoutDuration);
                tvSchedule = itemView.findViewById(R.id.tvWorkoutSchedule);
                tvCategory = itemView.findViewById(R.id.tvWorkoutCategory);
            }

            /**
             * This method fills the workout card with data
             * It sets all the text views to show the workout's information
             * @param workout The workout object containing all the data to display
             */
            public void bind(WorkoutSchedule workout) {
                // Set the workout name
                tvName.setText(workout.getName());
                // Set the workout purpose (what it's designed to do)
                tvPurpose.setText(workout.getPurpose());
                // Set the workout duration in minutes
                tvDuration.setText(workout.getDurationMinutes() + " min");
                // Set when this workout should be done
                tvSchedule.setText(workout.getSchedule());
                // Set the workout category (like cardio, strength, etc.)
                tvCategory.setText(workout.getCategory());

                // Set up what happens when someone taps on this workout card
                cardView.setOnClickListener(v -> {
                    // If we have a listener, tell it that this workout was tapped
                    if (listener != null) {
                        listener.onWorkoutClick(workout);
                    }
                });
            }
        }
    }

    /**
     * This interface defines what happens when a workout is selected
     * It's like a contract that says "when a workout is tapped, do this action"
     */
    interface OnWorkoutClickListener {
        /**
         * This method is called when a user taps on a workout
         * @param workout The workout that was selected
         */
        void onWorkoutClick(WorkoutSchedule workout);
    }
}
