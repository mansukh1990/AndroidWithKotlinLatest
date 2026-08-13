package com.example.animationandroid.listview_spinner_autocomplete_textview

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.animationandroid.R

class ListViewActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var spinner: Spinner
    private lateinit var autoCompleteTextView: AutoCompleteTextView

    //array
    var arrNo: IntArray = intArrayOf(1, 2, 3, 4)

    //arrayList
    private val arrName = ArrayList<String>()
    private val arrayIds = ArrayList<String>()
    private val arrayLanguages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        listView = findViewById<ListView>(R.id.listView)
        spinner = findViewById<Spinner>(R.id.spinner)
        autoCompleteTextView = findViewById(R.id.autoCompleteTextview)

        arrName.add("Ram")
        arrName.add("Raman")
        arrName.add("Ramanuj")
        arrName.add("Rameez")
        arrName.add("Ram")
        arrName.add("Raman")
        arrName.add("Ramanuj")
        arrName.add("Rameez")
        arrName.add("Ram")
        arrName.add("Raman")
        arrName.add("Ramanuj")
        arrName.add("Rameez")
        arrName.add("Ram")
        arrName.add("Raman")
        arrName.add("Ramanuj")
        arrName.add("Rameez")
        arrName.add("Ram")
        arrName.add("Raman")
        arrName.add("Ramanuj")
        arrName.add("Rameez")
        arrName.add("Ram")
        arrName.add("Raman")
        arrName.add("Ramanuj")
        arrName.add("Rameez")
        arrName.add("Ram")
        arrName.add("Raman")
        arrName.add("Ramanuj")
        arrName.add("Rameez")

        val adapter = ArrayAdapter<String>(
            this@ListViewActivity,
            android.R.layout.simple_list_item_1,
            arrName
        )
        listView.adapter = adapter

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                if (position == 0) {
                    Toast.makeText(applicationContext, "First Item Click", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Other Item Clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        //Spinner
        //Spinner
        arrayIds.add("Aadhaar Card")
        arrayIds.add("PAN Card")
        arrayIds.add("Voter Card")
        arrayIds.add("Driving Licence Card")
        arrayIds.add("Ration Card")
        arrayIds.add("10th Certificate")
        arrayIds.add("12th Certificate")

        val spinnerAdapter =
            ArrayAdapter<String>(
                this@ListViewActivity,
                android.R.layout.simple_spinner_dropdown_item,
                arrayIds
            )
        spinner.adapter = spinnerAdapter

        //AutoCompleteTextview

        arrayLanguages.add("C")
        arrayLanguages.add("C++")
        arrayLanguages.add("Java")
        arrayLanguages.add("Kotlin")
        arrayLanguages.add("PHP")
        arrayLanguages.add("Laravel")
        arrayLanguages.add("React")
        arrayLanguages.add(".Net")
        arrayLanguages.add("Dart")

        val autoCompleteTextViewAdapter = ArrayAdapter(
            this@ListViewActivity,
            android.R.layout.simple_list_item_1,
            arrayLanguages
        )
        autoCompleteTextView.setAdapter(autoCompleteTextViewAdapter)
        autoCompleteTextView.threshold = 0
    }
}