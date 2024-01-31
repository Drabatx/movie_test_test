package com.example.moviesdbapplication.ui.dialog

import android.app.AlertDialog
import android.content.Context
import com.example.moviesdbapplication.R

class MessageDialog(private val context: Context) {

    private val alertDialog: AlertDialog by lazy {
        AlertDialog.Builder(context)
            .setTitle("Mensaje") // Puedes personalizar el título aquí
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    companion object {
        private var instance: MessageDialog? = null

        fun getInstance(context: Context): MessageDialog {
            return instance ?: synchronized(this) {
                instance ?: MessageDialog(context).also { instance = it }
            }
        }
    }

    fun showMessageInformation(message: String) {
        alertDialog.setTitle(context.getString(R.string.s_title_dialog_info))
        alertDialog.setMessage(message)
        alertDialog.show()
    }

    fun shoeError(message: String){
        alertDialog.setTitle(context.getString(R.string.s_title_dialog_error))
        alertDialog.setMessage(message)
        alertDialog.show()
    }
}