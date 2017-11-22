package com.example.todo;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;

public class TodoActivity extends AppCompatActivity
{
    private static final int IS_SUCCESS = 0;

    //Used to store all the tasks the person wants to do.
    private ArrayList<String> m_tasksTodo = new ArrayList<>();

    //Used to store/access unique colours for the texts drawn on-screen.
    private int[] m_colourArray;

    //Stores index for current the task entry (m_tasksTodo) displayed on-screen.
    private int m_currentTask = 0;

    //Key for our m_currentTask variable. Used to push m_currentTask onto savedInstanceState.
    //Allows us to retrieve the original contents of m_currentTask if the activity is re-created
    //Example: orientation change.
    private static final String KEY_CURRENT_TASK = "CURRENT_TASK";
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

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
        //Restore any critical variables accordingly.
        if (savedInstanceState != null)
        {
            m_currentTask = savedInstanceState.getInt(KEY_CURRENT_TASK, 0);
        }

        //Bind a layout xml to our current activity.
        setContentView(R.layout.activity_todo);

        //We're ready to initialise the app's internal data/ui elements.
        InitialiseApp();

        Intent intent = TodoDetailActivity.newIntent(TodoActivity.this, m_currentTask);
        startActivity(intent);
    }

    /*
    requestCode is the integer request code originally supplied to startActivityForResult
    resultCode is the integer result code returned by the child activity through its setResult()
    intent date attached with intent "extras"
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        Log.d("TEST", "ONACTRESULT" + IS_SUCCESS);
        if (requestCode == IS_SUCCESS)
        {
            if (intent != null)
            {
                // data in intent from child activity
                updateTodoComplete(intent.getBooleanExtra(IS_TODO_COMPLETE, false));
            }
            else
            {
                Toast.makeText(this, R.string.back_button_pressed, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, R.string.request_code_mismatch, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTodoComplete(boolean is_todo_complete)
    {
        final TextView textViewTodo = findViewById(R.id.textViewTodo);

        if (is_todo_complete)
        {
            textViewTodo.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundSuccess));
            textViewTodo.setTextColor(ContextCompat.getColor(this, R.color.colorSuccess));
            setTextViewComplete("\u2713");
        }
    }

    private void setTextViewComplete( String message )
    {
        final TextView textViewComplete = findViewById(R.id.textViewComplete);
        textViewComplete.setText(message);
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

        //Read up the colours array from res/values/colors.xml
        m_colourArray = getResources().getIntArray(R.array.colour_values);

        //Update the text view so initial texts are shown on-screen.
        UpdateTextView();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        //Peserve m_currentTask in the saved Instance State.
        //Allows us to restore the value upon activity re-creation (onCreate).
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_CURRENT_TASK, m_currentTask);
    }

    private void InitialiseButtonListeners()
    {
        final Button buttonNext = findViewById(R.id.buttonNext);
        final Button buttonPrev = findViewById(R.id.buttonPrev);
        final Button buttonAdd = findViewById(R.id.buttonAdd);
        final Button buttonTodoDetail = findViewById(R.id.buttonTodoDetail);

        final EditText editTextAddTask = findViewById(R.id.editText);

        buttonNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                m_currentTask = ++m_currentTask % m_tasksTodo.size();

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

        buttonTodoDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                // Note, the child class being called has a static method determining the parameter
                // to be passed to it in the intent object
                Intent intent = TodoDetailActivity.newIntent(TodoActivity.this, m_currentTask);

                // second param requestCode identifies the call as there could be many "intents"
                startActivityForResult(intent, IS_SUCCESS);
                // The result will come back through
                // onActivityResult(requestCode, resultCode, Intent) method
            }
        });
    }

    private void UpdateTextView()
    {
        //Retrieve the text view object we're using to display the tasks to do.
        final TextView textView = findViewById(R.id.textViewTaskList);

        //Clamp
        ///@HACK - Because m_tasksTodo is no longer fixed length.
        ///Temporarily clamp colour index back if exceeding fixed size limit.
        if(m_currentTask > m_colourArray.length-1)
        {
            textView.setTextColor(m_colourArray[0]);
        }
        else
        {
            textView.setTextColor(m_colourArray[m_currentTask]);
        }

        textView.setText(m_tasksTodo.get(m_currentTask));
        setTextViewComplete("");
    }
}