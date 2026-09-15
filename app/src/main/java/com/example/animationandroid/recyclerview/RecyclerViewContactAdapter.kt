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
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.animationandroid.R
import com.example.animationandroid.databinding.DialogAddUpdateContactBinding
import com.example.animationandroid.databinding.RowContactBinding

class RecyclerViewContactAdapter(
    private val context: Context,
    private val arrContacts: ArrayList<ContactModel>
) : RecyclerView.Adapter<RecyclerViewContactAdapter.ViewHolder>() {

    private var lastPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RowContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact = arrContacts[position]

        holder.binding.apply {
            imgProfile.setImageResource(contact.img)
            tvName.text = contact.name
            tvContactNumber.text = contact.number
            imgEdit.setOnClickListener {
                val currentPosition = holder.bindingAdapterPosition
                if (currentPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                } else {
                    showUpdateDialog(currentPosition)
                }
            }

            imgDelete.setOnClickListener {
                val currentPosition = holder.bindingAdapterPosition
                if (currentPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                showDeleteDialog(currentPosition)
            }
        }

        setAnimation(holder.binding.root, position)

    }

    private fun showDeleteDialog(position: Int) {
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

    private fun showUpdateDialog(position: Int) {
        val dialog = Dialog(context)
        val binding = DialogAddUpdateContactBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        val contact = arrContacts[position]

        binding.tvAddContact.text = "Update Contact"
        binding.btnAdd.text = "Update"

        binding.edtName.setText(contact.name)
        binding.edtNumber.setText(contact.number)

        binding.btnAdd.setOnClickListener {

            val name = binding.edtName.text.toString().trim()
            val number = binding.edtNumber.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(context, "Please Enter Contact Name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (number.isEmpty()) {
                Toast.makeText(context, "Please Enter Mobile Numer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            arrContacts[position] = ContactModel(
                img = contact.img,
                name = name,
                number = number
            )
            notifyItemChanged(position)

            Toast.makeText(context, "Update Contact Successfully", Toast.LENGTH_SHORT).show()

            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun getItemCount(): Int {
        return arrContacts.size
    }

    class ViewHolder(
        val binding: RowContactBinding
    ) : RecyclerView.ViewHolder(binding.root)

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