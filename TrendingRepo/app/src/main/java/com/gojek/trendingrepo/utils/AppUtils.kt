package com.gojek.trendingrepo.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.gojek.trendingrepo.R
import com.google.android.material.snackbar.Snackbar

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

        fun showSnackBar(v: View?, snackBarText: String?) {
            if (v == null || snackBarText == null) {
                return
            }
            Snackbar.make(v, snackBarText, Snackbar.LENGTH_LONG).show()
        }
    }
}