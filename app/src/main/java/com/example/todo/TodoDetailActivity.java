package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

public class TodoDetailActivity extends AppCompatActivity
{
    private static final String TODO_INDEX = "com.example.todoIndex";
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

    public static Intent newIntent(Context packageContext, int todoIndex)
    {
        Intent intent = new Intent(packageContext, TodoDetailActivity.class);
        intent.putExtra(TODO_INDEX,todoIndex);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        int todoIndex = getIntent().getIntExtra(TODO_INDEX, 0);

        CheckBox checkboxIsComplete = findViewById(R.id.checkBoxIsComplete);
        // Register the onClick listener with the generic implementation mTodoListener
        checkboxIsComplete.setOnClickListener(mTodoListener);
    }

    // Create an anonymous implementation of OnClickListener for all clickable view objects
    private OnClickListener mTodoListener = new OnClickListener()
    {
        public void onClick(View v)
        {
            // get the clicked object and do something
            switch (v.getId() /*to get clicked view object id**/)
            {
                case R.id.checkBoxIsComplete:
                    CheckBox checkboxIsComplete = findViewById(R.id.checkBoxIsComplete);

                    Intent intent = new Intent();
                    intent.putExtra(IS_TODO_COMPLETE, checkboxIsComplete.isChecked());
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}