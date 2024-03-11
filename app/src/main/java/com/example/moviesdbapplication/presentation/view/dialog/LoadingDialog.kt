package com.example.moviesdbapplication.presentation.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.example.moviesdbapplication.databinding.LoadingDialogBinding

class LoadingDialog(context: Context) : Dialog(context) {

    private lateinit var binding: LoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = LoadingDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object{
        private var instance: LoadingDialog?= null
        fun getInstance(context: Context): LoadingDialog {
            return instance ?: synchronized(this){
                instance ?: LoadingDialog(context).also { instance = it }
            }
        }
    }

}