package com.gojek.trendingrepo.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.gojek.trendingrepo.R

class AppUtils {

    companion object {
        fun showLoadingDialog(context: Context?): Dialog {
            val dialog = Dialog(context!!)
            dialog.show()

            val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
            dialog.setContentView(inflate)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            return dialog
        }
    }
}