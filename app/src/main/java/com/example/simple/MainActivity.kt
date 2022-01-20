package com.example.simple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import org.apache.commons.io.FileUtils

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // Remove the item from the list
                listOfTasks.removeAt(position)
                //Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

//        // Detecting if the user clicked the add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            //This code will execute when the user clicks a button
//            Log.i("Aksel", "User clicked a button")
//        }

        loadItems()


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)



        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // Set up the button and the input field, so that the user can enter a task and add it to the list

        // Get a reference to the button and then set an onclicklistener

        findViewById<Button>(R.id.button).setOnClickListener {

            // Grabbing the text that user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // Adding string to our list of tasks
            listOfTasks.add(userInputtedTask)

            // Notifying the adapter
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // Reset text field
            inputTextField.setText("")

            saveItems()
        }

    }

    // Save the data that the user has inputted
    // Save data by writing and reading from the file

    //Get the file we need
    fun getDataFile() : File {
        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }
    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

    // Save items by writing them into our data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}