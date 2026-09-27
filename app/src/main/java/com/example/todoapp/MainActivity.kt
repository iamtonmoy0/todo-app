package com.example.todoapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var list: ListView

    var itemList = ArrayList<String>()
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button = findViewById(R.id.add)
        editText = findViewById(R.id.editText)
        list = findViewById(R.id.listView)

        itemList = fileHelper.readData(this)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemList)
        list.adapter = arrayAdapter

        button.setOnClickListener {
            val itemName: String = editText.text.toString()
            if (itemName.isNotBlank()) {
                itemList.add(itemName)
                editText.setText("")
                fileHelper.writeData(itemList, applicationContext)
                arrayAdapter.notifyDataSetChanged()
            }
        }

        list.setOnItemClickListener { _, _, position, _ ->
            itemList.removeAt(position)
            fileHelper.writeData(itemList, applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }
    }
}
