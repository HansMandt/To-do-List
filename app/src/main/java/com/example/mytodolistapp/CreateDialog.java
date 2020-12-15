package com.example.mytodolistapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class CreateDialog extends DialogFragment {
    private EditText taskText;
    private CalendarView datePicker;
    private int dd, mm, yy;
    private ToDoItemListener listener;

    public interface ToDoItemListener {
        void setTask(String text, String date);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_task_layout, container);
    }

    @Override
    public void onAttach (@NonNull Context context) {
        super.onAttach(context);
        listener = (ToDoItemListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.create_task_layout, null);
        taskText = view.findViewById(R.id.task_text_input);
        datePicker = view.findViewById(R.id.date_picker);
        Calendar calendar = Calendar.getInstance();
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        mm = calendar.get(Calendar.MONTH) + 1;
        yy = calendar.get(Calendar.YEAR);
        datePicker.setOnDateChangeListener((view1, year, month, dayofMonth) -> {
            dd = dayofMonth /*+ 1*/;
            mm = month + 1;
            yy = year;
        });

        builder.setTitle("To-Do List");
        builder.setView(view);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Add", (dialog, which) -> {
            System.out.println("Positive clicked");
            String inputText = taskText.getText().toString();
            if (TextUtils.isEmpty(inputText)) {
                taskText.setError("Error");
            }
            listener.setTask(inputText, String.format("%02d/%02d/%d", dd, mm, yy));
        });
        return builder.create();
    }

}
