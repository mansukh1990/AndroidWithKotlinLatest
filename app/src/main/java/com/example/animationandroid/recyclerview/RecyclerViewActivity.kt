package com.example.animationandroid.recyclerview

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animationandroid.R
import com.example.animationandroid.databinding.ActivityRecyclerViewBinding
import com.example.animationandroid.databinding.DialogAddUpdateContactBinding

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding
    private var recyclerViewContactAdapter: RecyclerViewContactAdapter? = null

    private var arrayContact = ArrayList<ContactModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupToolbar()
        setupRecyclerView()
        addContacts()
        setupAdapter()
        setupAddContactButton()

    }

    private fun setupAddContactButton() {
        binding.fbAddContact.setOnClickListener {

            val dialog = Dialog(this@RecyclerViewActivity)
            val dialogBinding = DialogAddUpdateContactBinding.inflate(LayoutInflater.from(this))
            dialog.setContentView(dialogBinding.root)

            dialog.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

            dialogBinding.apply {
                tvAddContact.text = "Add Contact"
                btnAdd.text = "Add"

                btnAdd.setOnClickListener {
                    val name = dialogBinding.edtName.text.toString().trim()
                    val number = dialogBinding.edtNumber.text.toString().trim()

                    if (name.isEmpty()) {
                        Toast.makeText(this@RecyclerViewActivity, "Please Enter Contact Name!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    if (number.isEmpty()) {
                        Toast.makeText(this@RecyclerViewActivity, "Please Enter Mobile Numer", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    arrayContact.add(
                        ContactModel(
                            img = R.drawable.ic_contact_profile,
                            name = name,
                            number = number
                        )
                    )
                    val newPosition = arrayContact.lastIndex

                    recyclerViewContactAdapter?.notifyItemInserted(newPosition)
                    binding.recyclerView.scrollToPosition(newPosition)

                    Toast.makeText(this@RecyclerViewActivity, "Add Contact Successfully", Toast.LENGTH_SHORT).show()

                    dialog.dismiss()
                }
            }

            dialog.setCancelable(false)
            dialog.show()
        }
    }

    private fun setupAdapter() {
        recyclerViewContactAdapter = RecyclerViewContactAdapter(this, arrayContact)
        binding.recyclerView.setAdapter(recyclerViewContactAdapter)
    }

    private fun addContacts() {
        repeat(32) {
            arrayContact.add(
                ContactModel(
                    img = R.drawable.ic_contact_profile,
                    name = "Steven Smith",
                    number = "98004654564"
                )
            )
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Title"
            subtitle = "Sub Title"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
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