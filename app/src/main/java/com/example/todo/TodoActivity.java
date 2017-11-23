package com.example.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TodoActivity extends AppCompatActivity
{
    //Used to store all the tasks the person wants to do.
    //Note: Now dynamic sized array to allow adding tasks on the fly.
    private ArrayList<String> m_tasksTodo = new ArrayList<>();

    //Stores index for current the task entry (m_tasksTodo) displayed on-screen.
    private int m_currentTask = 0;

    //Key for our m_currentTask variable. Used to push m_currentTask onto savedInstanceState.
    //Allows us to retrieve the original contents of m_currentTask if the activity is re-created
    //Example: orientation change.
    private static final String KEY_CURRENT_TASK = "CURRENT_TASK";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Only log state changes for debug builds.
        if(BuildConfig.DEBUG)
        {
            Log.d("TodoActivity.onCreate", "Info: Application State change detected, current is: onCreate!");
        }

        //Call the super class's onCreate method to complete initialisation of the activity.
        super.onCreate(savedInstanceState);

        //If not null the app has an incoming previously saved instance state.
        //Restore any critical variables this activity instance has lost.
        if (savedInstanceState != null)
        {
            m_currentTask = savedInstanceState.getInt(KEY_CURRENT_TASK, 0);
        }

        //Bind a layout xml to our current activity.
        setContentView(R.layout.activity_todo);

        //We're ready to initialise the app's internal data/ui elements.
        InitialiseApp();
    }

    @Override
    protected void onStart()
    {
        //Only log state changes for debug builds.
        if(BuildConfig.DEBUG)
        {
            Log.d("TodoActivity.onStart", "Info: Application State change detected, current is: onStart!");
        }

        super.onStart();
        getDelegate().onStart();
    }

    @Override
    public void onResume()
    {
        //Only log state changes for debug builds.
        if(BuildConfig.DEBUG)
        {
            Log.d("TodoActivity.onResume", "Info: Application State change detected, current is: onResume!");
        }

        super.onResume();
    }

    @Override
    public void onPause()
    {
        //Only log state changes for debug builds.
        if(BuildConfig.DEBUG)
        {
            Log.d("TodoActivity.onPause", "Info: Application State change detected, current is: onPause!");
        }

        super.onPause();
    }

    @Override
    protected void onStop()
    {
        //Only log state changes for debug builds.
        if(BuildConfig.DEBUG)
        {
            Log.d("TodoActivity.onStop", "Info: Application State change detected, current is: onStop!");
        }

        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        //Only log state changes for debug builds.
        if(BuildConfig.DEBUG)
        {
            Log.d("TodoActivity.onDestroy", "Info: Application State change detected, current is: onDestroy!");
        }

        super.onDestroy();
    }

    private void InitialiseApp()
    {
        //Setup the onClick call-backs for our buttons.
        InitialiseButtonListeners();

        //Read the task names array from res/values/strings.xml
        m_tasksTodo.addAll(Arrays.asList(getResources().getStringArray(R.array.task_names)));

        //Update the text view so initial texts are shown on-screen.
        UpdateTextView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        //Preserve m_currentTask in the saved Instance State.
        //Allows us to restore the value upon activity re-creation (onCreate).
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_CURRENT_TASK, m_currentTask);
    }

    private void InitialiseButtonListeners()
    {
        final Button buttonNext = findViewById(R.id.buttonNext);
        final Button buttonPrev = findViewById(R.id.buttonPrev);
        final Button buttonAdd = findViewById(R.id.buttonAdd);
        final EditText editTextAddTask = findViewById(R.id.editText);

        buttonNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                m_currentTask = ++m_currentTask % m_tasksTodo.size();

                if(BuildConfig.DEBUG)
                    assert(m_currentTask < m_tasksTodo.size() && m_currentTask > -1);

                //Updates the text view to the current task since it's now modified.
                UpdateTextView();
            }
        });

        buttonPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                m_currentTask = m_currentTask == 0 ? m_tasksTodo.size()-1 : --m_currentTask;

                if(BuildConfig.DEBUG)
                    assert(m_currentTask < m_tasksTodo.size() && m_currentTask > -1);

                //Updates the text view to the current task since it's now modified.
                UpdateTextView();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               m_tasksTodo.add(editTextAddTask.getText().toString());
            }
        });
    }

    private void UpdateTextView()
    {
        //Retrieve the text view object we're using to display the tasks to do.
        final TextView textView = findViewById(R.id.textViewTaskList);
        Random r = new Random();

        textView.setText(m_tasksTodo.get(m_currentTask));
        textView.setTextColor(0xFF000000 | r.nextInt(255) << 16 & 0x00FF0000 | r.nextInt(255) << 8 & 0x0000FF00 | r.nextInt(255) & 0xFF);
    }
}