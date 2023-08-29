package com.example.mytodos.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytodos.R
import com.example.mytodos.data.models.Priority
import com.example.mytodos.data.models.ToDoData
import com.example.mytodos.data.viewmodel.ToDoViewModel

class AddFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private lateinit var mTitle: EditText
    private lateinit var mPriority: Spinner
    private lateinit var mDescription: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        mTitle = view.findViewById(R.id.title_editText)
        mPriority = view.findViewById(R.id.priority_spinner)
        mDescription = view.findViewById(R.id.description_editTextTextMultiLine)
        // Set menu
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val title = mTitle.toString()
        val description = mDescription.toString()
        val priority = mPriority.toString()
        val validation = verifyDataFromUser(title, description)
        if(validation) {
            val newData = ToDoData(
                0,
                title,
                parsePriority(priority),
                description
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    private fun parsePriority(priority: String): Priority {
        return when(priority) {
            "High Priority" -> {Priority.HIGH}
            "Medium Priority" -> {Priority.MEDIUM}
            "Low Priority" -> {Priority.LOW}
            else -> Priority.LOW
        }
    }

    private fun verifyDataFromUser(title: String, description: String): Boolean {
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else {
            !(title.isEmpty() || description.isEmpty())
        }
    }
}