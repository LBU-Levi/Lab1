package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class TodoActivity extends AppCompatActivity
{
    private static final int IS_SUCCESS = 0;

    //Used to store/access our texts defined in res/values/strings.xml
    private String[] m_stringArray;

    //Used to store/access our colours defined in res/values/colours.xml
    private int[] m_colourArray;

    //Stores index for current the text to be displayed on-screen
    private int m_currentStringIndex = 0;

    private static final String STRING_INDEX_KEY = "STRING_INDEX";
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Call the super class's onCreate method complete initialisation of the activity.
        super.onCreate(savedInstanceState);

        //Pull m_currentStringIndex from previous saved state.
        if (savedInstanceState != null)
        {
            m_currentStringIndex = savedInstanceState.getInt(STRING_INDEX_KEY, 0);
        }

        // set the user interface layout for this Activity
        // the layout file is defined in the project res/layout/activity_todo.xml file
        setContentView(R.layout.activity_todo);

        //Sets up default values and button listeners.
        InitialiseColoursApp();


        Intent intent = TodoDetailActivity.newIntent(TodoActivity.this, m_currentStringIndex);
        startActivity(intent);

    }

    /*
    requestCode is the integer request code originally supplied to startActivityForResult
    resultCode is the integer result code returned by the child activity through its setResult()
    intent date attached with intent "extras"
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == IS_SUCCESS ){
            if (intent != null) {
                // data in intent from child activity
                boolean isTodoComplete = intent.getBooleanExtra(IS_TODO_COMPLETE, false);
                updateTodoComplete(isTodoComplete);
            } else {
                Toast.makeText(this, R.string.back_button_pressed, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.request_code_mismatch,
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void updateTodoComplete(boolean is_todo_complete) {

        final TextView textViewTodo;
        textViewTodo = (TextView) findViewById(R.id.textViewTodo);

        if (is_todo_complete) {
            textViewTodo.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.backgroundSuccess));
            textViewTodo.setTextColor(
                    ContextCompat.getColor(this, R.color.colorSuccess));

            setTextViewComplete("\u2713");
        }
    }

    private void setTextViewComplete( String message ){
        final TextView textViewComplete;
        textViewComplete = (TextView) findViewById(R.id.textViewComplete);

        textViewComplete.setText(message);
    }

    private void InitialiseColoursApp()
    {
        final TextView textView;
        textView = findViewById(R.id.textViewTodo);

        InitialiseButtonListeners(textView);

        //Read the todo array from res/values/strings.xml
        m_stringArray = getResources().getStringArray(R.array.task_names);

        //Read up the colours array from res/values/colors.xml
        m_colourArray = getResources().getIntArray(R.array.colour_values);

        //Display the first task from m_stringArray array in the textView
        textView.setText(m_stringArray[m_currentStringIndex]);

        //Set colour of current text displayed.
        textView.setTextColor(m_colourArray[m_currentStringIndex]);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(STRING_INDEX_KEY, m_currentStringIndex);
    }

    private void InitialiseButtonListeners(final TextView textView)
    {
        Button buttonNext, buttonPrev;
        buttonNext = findViewById(R.id.buttonNext);
        buttonPrev = findViewById(R.id.buttonPrev);

        buttonNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                textView.setText(m_stringArray[m_currentStringIndex = ++m_currentStringIndex % m_stringArray.length]);
                textView.setTextColor(m_colourArray[m_currentStringIndex]);
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                textView.setText(m_stringArray[m_currentStringIndex == 0 ? m_currentStringIndex = m_stringArray.length-1 : --m_currentStringIndex]);
                textView.setTextColor(m_colourArray[m_currentStringIndex]);
            }
        });
    }
}