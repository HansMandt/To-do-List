package com.example.mytodolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements CreateDialog.ToDoItemListener{

    RecyclerView toDoList;
    TextView emptyTextMsg;
    ToDoTaskDBHelper dbHelper;
    ToDoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoList = findViewById(R.id.todo_list);
        toDoList.setLayoutManager(new LinearLayoutManager(this));
        emptyTextMsg = findViewById(R.id.empty_text);

        dbHelper = new ToDoTaskDBHelper(getApplicationContext(),
                ToDoTaskDBHelper.DB_NAME,
                null,
                ToDoTaskDBHelper.VERSION);

        adapter = new ToDoListAdapter(
                dbHelper.getAllTasks(),
                getSupportFragmentManager(),
                emptyTextMsg,
                dbHelper);

        if (adapter.toDoTasks.size() > 0) {
            emptyTextMsg.setVisibility(View.INVISIBLE);
        }

        toDoList.setAdapter(adapter);

        CreateDialog createDialog = new CreateDialog();

        FloatingActionButton fab = findViewById(R.id.create_task_btn);
        fab.setOnClickListener((v -> createDialog.show(getSupportFragmentManager(), "Create a Task")));
    }

    @Override
    public void setTask(String text, String date) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Text or Date wasn't filled in. Event not created.", Toast.LENGTH_SHORT).show();
        } else {
            int id = dbHelper.insertTask(text, date);
            adapter.toDoTasks.add(new ToDoTask(id, text, date, false));
            adapter.notifyItemInserted(adapter.toDoTasks.size());
            emptyTextMsg.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Task Added to To-Do List!", Toast.LENGTH_SHORT).show();
        }
    }
}