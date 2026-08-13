package com.example.animationandroid.recyclerview

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.animationandroid.R

class RecyclerViewContactAdapter(
    var context: Context,
    var arrContacts: ArrayList<ContactModel>
) : RecyclerView.Adapter<RecyclerViewContactAdapter.ViewHolder>() {

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact = arrContacts[position]

        holder.imgContact.setImageResource(contact.img)
        holder.txtName.text = contact.name
        holder.txtNumber.text = contact.number

        setAnimation(holder.itemView, position)

        holder.editContact.setOnClickListener {

            val dialog = Dialog(context)
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

            textViewContact.text = "Update Contact"
            btnAdd.text = "Update"

            editTextName.setText(contact.name)
            editTextNumber.setText(contact.number)

            btnAdd.setOnClickListener {

                val name = editTextName.text.toString().trim()
                val number = editTextNumber.text.toString().trim()

                if (name.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Please Enter Contact Name!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (number.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Please Enter Mobile Numer",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                arrContacts[position] = ContactModel(arrContacts[position].img, name, number)
                notifyItemChanged(position)

                Toast.makeText(context, "Update Contact Successfully", Toast.LENGTH_SHORT).show()

                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.show()
        }

        holder.deleteContact.setOnClickListener {
            val builder = AlertDialog.Builder(context)
                .setTitle("Delete Contact")
                .setMessage("Are Your Sure Want To Delete Contact?")
                .setIcon(R.drawable.ic_baseline_delete_forever_24)
                .setPositiveButton(
                    "YES"
                ) { dialogInterface, i: Int ->
                    arrContacts.removeAt(position)
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Delete Contact Successfully", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                }
                .setNegativeButton(
                    "NO"
                ) { dialogInterface, i: Int ->
                    dialogInterface!!.dismiss()
                }
            builder.show()
        }
    }

    override fun getItemCount(): Int {
        return arrContacts.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.tvName)
        val txtNumber: TextView = itemView.findViewById(R.id.tvContactNumber)
        val imgContact: ImageView = itemView.findViewById(R.id.imgProfile)
        val editContact: ImageView = itemView.findViewById(R.id.imgEdit)
        val deleteContact: ImageView = itemView.findViewById(R.id.imgDelete)
    }

    private fun setAnimation(
        view: View,
        position: Int
    ) {
        if (position > lastPosition) {
            val slideIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            view.startAnimation(slideIn)
            lastPosition = position
        }
    }
}