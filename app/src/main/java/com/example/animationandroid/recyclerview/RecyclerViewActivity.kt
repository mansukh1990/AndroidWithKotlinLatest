package com.example.animationandroid.recyclerview

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animationandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecyclerViewActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var recyclerView: RecyclerView? = null
    private var floatingActionButton: FloatingActionButton? = null
    var arrayContact = ArrayList<ContactModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recyclerView)
        floatingActionButton = findViewById(R.id.fbAddContact)

        //androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        //android.widget
        //setActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Title"
        //toolbar?.setTitle("Title")
        toolbar?.setSubtitle("Sub Title")

        recyclerView?.layoutManager = LinearLayoutManager(this)

        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))
        arrayContact.add(ContactModel(R.drawable.ic_contact_profile, "Steven Smith", "98004654564"))

        val recyclerViewContactAdapter = RecyclerViewContactAdapter(this, arrayContact)
        recyclerView?.setAdapter(recyclerViewContactAdapter)

        floatingActionButton?.setOnClickListener {
            val dialog = Dialog(this@RecyclerViewActivity)
            dialog.setContentView(R.layout.dialog_add_update_contact)
            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

            val editTextName = dialog.findViewById<EditText>(R.id.edtName)
            val editTextNumber = dialog.findViewById<EditText>(R.id.edtNumber)
            val btnAdd = dialog.findViewById<Button>(R.id.btnAdd)
            val textViewContact = dialog.findViewById<TextView>(R.id.tvAddContact)

            btnAdd.setOnClickListener {
                val name = editTextName.text.toString().trim()
                val number = editTextNumber.text.toString().trim()

                if (name.isEmpty()) {
                    Toast.makeText(
                        this@RecyclerViewActivity,
                        "Please Enter Contact Name!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (number.isEmpty()) {
                    Toast.makeText(
                        this@RecyclerViewActivity,
                        "Please Enter Mobile Numer",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                arrayContact.add(
                    ContactModel(
                        img = R.drawable.ic_contact_profile,
                        name = name,
                        number = number
                    )
                )
                recyclerViewContactAdapter.notifyItemInserted(arrayContact.size - 1)
                recyclerView!!.scrollToPosition(arrayContact.size - 1)

                Toast.makeText(
                    this@RecyclerViewActivity,
                    "Add Contact Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val itemId = item.itemId

        when (itemId) {
            R.id.option_new -> {
                Toast.makeText(this, "Create New File", Toast.LENGTH_SHORT).show()
            }

            R.id.option_open -> {
                Toast.makeText(this, "Open File", Toast.LENGTH_SHORT).show()
            }

            R.id.option_save -> {
                Toast.makeText(this, "Save File", Toast.LENGTH_SHORT).show()
            }

            R.id.option_search -> {
                Toast.makeText(this, "Clicked Search", Toast.LENGTH_SHORT).show()
            }

            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}