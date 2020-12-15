package com.example.mytodolistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {

    public ArrayList<ToDoTask> toDoTasks;
    private ToDoTaskDBHelper dbHelper;
    private TextView emptyTextView;

    public ToDoListAdapter(ArrayList<ToDoTask> toDoTasks, FragmentManager supportFragmentManager, TextView emptyTextView, ToDoTaskDBHelper dbHelper) {
        this.toDoTasks = toDoTasks;
        this.dbHelper = dbHelper;
        this.emptyTextView = emptyTextView;
    }

    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View taskView = inflater.inflate(R.layout.task_layout, null);
        return new ToDoListViewHolder(taskView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListAdapter.ToDoListViewHolder holder, int position) {
        ToDoTask task = toDoTasks.get(position);
        holder.completeCheckBox.setChecked(task.isComplete());
        holder.taskTextView.setText(task.getText());
        holder.dateTextView.setText(task.getDate());

        holder.itemView.setOnLongClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("item_text", toDoTasks.get(position).getText());
            return position == 1;
        });

        holder.deleteBtn.setOnClickListener(v -> {
            ToDoTask taskToDelete = toDoTasks.get(holder.getAdapterPosition());
            if (dbHelper.deleteTask(taskToDelete.getId())) {
               toDoTasks.remove(holder.getAdapterPosition());
               notifyItemRemoved(position);
            }
            if (toDoTasks.size() == 0) {
               emptyTextView.setVisibility(View.VISIBLE);
            }
        });

        holder.completeCheckBox.setOnCheckedChangeListener((v, checked) -> {
            dbHelper.setComplete(toDoTasks.get(holder.getAdapterPosition()).getId(), checked);
        });
    }


    @Override
    public int getItemCount() {
        return toDoTasks.size();
    }

    public class ToDoListViewHolder extends RecyclerView.ViewHolder {
        TextView taskTextView;
        CheckBox completeCheckBox;
        TextView dateTextView;
        ImageButton deleteBtn;

        public ToDoListViewHolder(@NonNull View taskView) {
            super(taskView);
            taskTextView = taskView.findViewById(R.id.task_text);
            completeCheckBox = taskView.findViewById(R.id.task_checkbox);
            dateTextView = taskView.findViewById(R.id.task_date);
            deleteBtn = taskView.findViewById(R.id.delete_task_btn);
        }
    }
}
