package com.example.tokenlab.extensions

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.example.tokenlab.R

fun Context.createLoadingDialog(): Dialog {
    val builder = AlertDialog.Builder(this)
    builder.setView(R.layout.progress_dialog)
    val dialog: Dialog = builder.create()
    dialog.setCancelable(false)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    return dialog
}

fun Context.showErrorDialogWithAction(
    message: String,
    onPositiveClick: DialogInterface.OnClickListener
) {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(message)
        .setCancelable(false)
        .setPositiveButton(
            this.getString(R.string.alert_dialog_text), onPositiveClick
        )
    val alert = builder.create()
    alert.show()
}
