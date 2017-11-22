package com.example.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity
{
    //Used to store all the tasks the person wants to do.
    private String[] m_tasksTodo;

    //Used to store/access unique colours for the texts drawn on-screen.
    private int[] m_colourArray;

    //Stores index for current the task entry (m_tasksTodo) displayed on-screen.
    private int m_currentTask = 0;

    private static final String STRING_INDEX_KEY = "STRING_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Call the super class's onCreate method to complete initialisation of the activity.
        super.onCreate(savedInstanceState);

        //Pull m_currentStringIndex from previous saved state.
        if (savedInstanceState != null)
        {
            m_currentTask = savedInstanceState.getInt(STRING_INDEX_KEY, 0);
        }

        // set the user interface layout for this Activity
        // the layout file is defined in the project res/layout/activity_todo.xml file
        setContentView(R.layout.activity_todo);

        //Sets up default values for the app and button listeners.
        InitialiseApp();
    }

    private void InitialiseApp()
    {
        //First off, let's set the initial texts shown on-screen.

        //Setup the onClick call-back for our buttons.
        InitialiseButtonListeners();

        //Read the task names array from res/values/strings.xml
        m_tasksTodo = getResources().getStringArray(R.array.task_names);

        //Read up the colours array from res/values/colors.xml
        m_colourArray = getResources().getIntArray(R.array.colour_values);

        //Update the text view
        UpdateTextView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(STRING_INDEX_KEY, m_currentTask);
    }

    private void InitialiseButtonListeners()
    {
        Button buttonNext, buttonPrev;

        buttonNext = findViewById(R.id.buttonNext);
        buttonPrev = findViewById(R.id.buttonPrev);

        buttonNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                m_currentTask = ++m_currentTask % m_tasksTodo.length;

                //Updates the text view to the current task since it's now modified.
                UpdateTextView();
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                m_currentTask = m_currentTask == 0 ? m_tasksTodo.length-1 : --m_currentTask;

                //Updates the text view to the current task since it's now modified.
                UpdateTextView();
            }
        });
    }

    private void UpdateTextView()
    {
        //Retrieve the text view object
        final TextView textView = findViewById(R.id.textViewTodo);
        textView.setText(m_tasksTodo[m_currentTask]);
        textView.setTextColor(m_colourArray[m_currentTask]);
    }
}