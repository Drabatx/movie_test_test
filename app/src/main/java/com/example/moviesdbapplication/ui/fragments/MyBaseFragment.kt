package com.example.moviesdbapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.moviesdbapplication.ui.dialog.LoadingDialog
import com.example.moviesdbapplication.ui.dialog.MessageDialog

open class MyBaseFragment: Fragment() {

    fun showMessage(message: String) {
        val messageDialog = context?.let { MessageDialog.getInstance(it) }
        messageDialog?.showMessageInformation(message)
    }

    fun showError(error: String) {
        val messageDialog = context?.let { MessageDialog.getInstance(it) }
        messageDialog?.showMessageInformation(error)
    }

    fun showLoading(){
        val loadingDialog = context?.let { LoadingDialog.getInstance(it) }
        loadingDialog?.show()
    }

    fun hideLoading(){
        val loadingDialog = context?.let { LoadingDialog.getInstance(it) }
        loadingDialog?.dismiss()
    }
}