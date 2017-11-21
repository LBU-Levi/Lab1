
package com.example.todo;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity
{
    //Used to store our texts in res/values/strings.xml
    private String[] m_stringArray;

    //Used to store our colours assigned to the texts
    private int[] m_colourArray;

    //Stores index into m_stringArray for current text displayed on-screen
    private int m_currentStringIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState);

        // set the user interface layout for this Activity
        // the layout file is defined in the project res/layout/activity_todo.xml file
        setContentView(R.layout.activity_todo);

        // initialize member TextView so we can manipulate it later
        final TextView TodoTextView;
        TodoTextView = (TextView) findViewById(R.id.textViewTodo);

        // read the todo array from res/values/strings.xml
        m_stringArray = getResources().getStringArray(R.array.colour_names);


        // Read up the colours array from res/values/colors.xml
        m_colourArray = getResources().getIntArray(R.array.colour_values);


        // display the first task from mTodo array in the TodoTextView
        TodoTextView.setText(m_stringArray[m_currentStringIndex]);

        //Set colour
        TodoTextView.setTextColor(m_colourArray[m_currentStringIndex]);

        Button buttonNext, buttonPrev;
        buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonPrev = (Button) findViewById(R.id.buttonPrev);

        // OnClick listener for the  Next button
        buttonNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TodoTextView.setText(m_stringArray[m_currentStringIndex = ++m_currentStringIndex % m_stringArray.length]);
                TodoTextView.setTextColor(m_colourArray[m_currentStringIndex]);

            }
        });

        // OnClick listener for the  Prev button
        buttonPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TodoTextView.setText(m_stringArray[m_currentStringIndex == 0 ? m_currentStringIndex = m_stringArray.length-1 : --m_currentStringIndex]);
                TodoTextView.setTextColor(m_colourArray[m_currentStringIndex]);
            }
        });
    }
}