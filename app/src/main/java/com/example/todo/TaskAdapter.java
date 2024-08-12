package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;
    private OnTaskClickListener onTaskClickListener;
    private OnTaskLongClickListener onTaskLongClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public TaskAdapter(List<Task> tasks, OnTaskClickListener onTaskClickListener, OnTaskLongClickListener onTaskLongClickListener, OnDeleteClickListener onDeleteClickListener) {
        this.tasks = tasks;
        this.onTaskClickListener = onTaskClickListener;
        this.onTaskLongClickListener = onTaskLongClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void updateTasks(List<Task> newTasks) {
        tasks.clear();
        tasks.addAll(newTasks);
        notifyDataSetChanged();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView titleView;
        private CheckBox completedCheckBox;
        private Button deleteButton;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.task_title);
            completedCheckBox = itemView.findViewById(R.id.task_completed);
            deleteButton = itemView.findViewById(R.id.delete_task_button);
        }

        void bind(Task task) {
            titleView.setText(task.getTitle());
            completedCheckBox.setChecked(task.isCompleted());

            itemView.setOnClickListener(v -> onTaskClickListener.onTaskClick(task));
            itemView.setOnLongClickListener(v -> {
                onTaskLongClickListener.onTaskLongClick(task);
                return true;
            });

            completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.setCompleted(isChecked);
                TaskDatabaseHelper dbHelper = new TaskDatabaseHelper(itemView.getContext());
                dbHelper.updateTask(task);
            });

            deleteButton.setOnClickListener(v -> onDeleteClickListener.onDeleteClick(task));
        }
    }

    interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    interface OnTaskLongClickListener {
        void onTaskLongClick(Task task);
    }

    interface OnDeleteClickListener {
        void onDeleteClick(Task task);
    }
}
