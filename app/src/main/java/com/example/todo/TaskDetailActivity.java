package com.example.todo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class TaskDetailActivity extends AppCompatActivity {
    private EditText taskTitle;
    private EditText taskDescription;
    private DatePicker taskDueDate;
    private Button saveTaskButton;
    private TaskDatabaseHelper dbHelper;
    private Task currentTask;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        TextInputLayout titleInputLayout = findViewById(R.id.task_title_input_layout);
        TextInputLayout descriptionInputLayout = findViewById(R.id.task_description_input_layout);
        taskTitle = titleInputLayout.getEditText();
        taskDescription = descriptionInputLayout.getEditText();
        taskDueDate = findViewById(R.id.task_due_date);
        saveTaskButton = findViewById(R.id.save_task_button);

        dbHelper = new TaskDatabaseHelper(this);

        long taskId = getIntent().getLongExtra("task_id", -1);
        if (taskId != -1) {
            isEditing = true;
            currentTask = dbHelper.getTask(taskId);
            taskTitle.setText(currentTask.getTitle());
            taskDescription.setText(currentTask.getDescription());
            taskDueDate.updateDate(currentTask.getDueYear(), currentTask.getDueMonth(), currentTask.getDueDay());
        }

        saveTaskButton.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = taskTitle.getText().toString();
        String description = taskDescription.getText().toString();
        int day = taskDueDate.getDayOfMonth();
        int month = taskDueDate.getMonth();
        int year = taskDueDate.getYear();

        if (isEditing) {
            currentTask.setTitle(title);
            currentTask.setDescription(description);
            currentTask.setDueDate(year, month, day);
            dbHelper.updateTask(currentTask);
        } else {
            Task task = new Task(title, description, 0, year, month, day);
            dbHelper.addTask(task);
        }
        setResult(RESULT_OK);
        finish();
    }
}