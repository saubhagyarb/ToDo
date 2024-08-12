package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private TaskDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskRecyclerView = findViewById(R.id.task_recycler_view);
        Button addTaskButton = findViewById(R.id.add_task_button);

        dbHelper = new TaskDatabaseHelper(this);
        List<Task> tasks = dbHelper.getAllTasks();
        taskAdapter = new TaskAdapter(tasks, this::onTaskClick, this::onTaskLongClick, this::onDeleteClick);
        taskRecyclerView.setAdapter(taskAdapter);

        addTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    private void onTaskClick(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
        intent.putExtra("task_id", task.getId());
        startActivityForResult(intent, 2);
    }

    private void onTaskLongClick(Task task) {
        showDeleteConfirmationDialog(task);
    }

    private void onDeleteClick(Task task) {
        showDeleteConfirmationDialog(task);
    }

    private void showDeleteConfirmationDialog(Task task) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    dbHelper.deleteTask(task.getId());
                    refreshTaskList();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refreshTaskList();
        }
    }

    private void refreshTaskList() {
        List<Task> tasks = dbHelper.getAllTasks();
        taskAdapter.updateTasks(tasks);
    }
}
