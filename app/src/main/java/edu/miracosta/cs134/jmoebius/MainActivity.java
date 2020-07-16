package edu.miracosta.cs134.jmoebius;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import edu.miracosta.cs134.jmoebius.model.DBHelper;
import edu.miracosta.cs134.jmoebius.model.Task;

public class MainActivity extends AppCompatActivity {

    // Create a reference to the database
    private DBHelper mDB;
    private List<Task> mAllTasks;
    private EditText descriptionEditText;
    private ListView taskListView;
    private TaskListAdapter mTaskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wire up the views
        descriptionEditText = findViewById(R.id.taskEditText);
        taskListView = findViewById(R.id.taskListView);

        mDB = new DBHelper(this);
        //mDB.clearAllTasks();

        mAllTasks = mDB.getAllTasks();

        // Instantiate the ListAdapter
        mTaskListAdapter = new TaskListAdapter(this, R.layout.task_item, mAllTasks);

        // Connect ListView with ListAdapter
        taskListView.setAdapter(mTaskListAdapter);

        // Let's loop through them and print them to the log
//        for (Task t : mAllTasks) {
//            Log.i("ToDo2Day", t.toString());
//        }

        // Let's create some dummy data and add it to the database
//        mDB.addTask(new Task("Wash dishes you left in the sink this morning Mike"));
//        mDB.addTask(new Task("Answer Week 6 Quiz Questions"));
//        mDB.addTask(new Task("Read Chapter 5"));
    }

    /**
     * Adds a Task to the list
     *
     * @param v     View the button is clicked on
     */
    public void addTask(View v) {
        //Let's extract the description from the task
        String description = descriptionEditText.getText().toString();

        // id = -1, description = User input, isDone = false
        Task newTask = new Task(description);
        long id = mDB.addTask(newTask);
        newTask.setmId(id);

        //Add the new task to the list
        mAllTasks.add(newTask);
        mTaskListAdapter.notifyDataSetChanged();
    }

    /**
     * Clears all Tasks in the list.
     *
     * @param v     View the button is clicked on
     */
    public void clearAllTasks(View v) {
        mDB.clearAllTasks();
        mAllTasks.clear();

        // Update ListView
        mTaskListAdapter.notifyDataSetChanged();
    }

    /**
     * Changes the CheckBox to (un)checked.
     *
     * @param v     View that the button is clicked on
     */
    public void changeTaskStatus(View v) {
        Task selectedTask = (Task) v.getTag();
        selectedTask.setIsDone(!selectedTask.isDone());
    }

    /**
     * Closes the database when the app is closed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDB.close();
    }
}
