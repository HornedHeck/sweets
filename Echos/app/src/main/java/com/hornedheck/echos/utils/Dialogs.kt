package com.hornedheck.echos.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StringRes
import com.hornedheck.echos.R
import kotlinx.android.synthetic.main.dialog_edit_text.view.*

fun Context.textInputDialog(
    success: (String) -> Unit,
    @StringRes title: Int? = null,
    @StringRes message: Int? = null,
    @StringRes hint: Int? = null,
    cancel: () -> Unit = {}
): AlertDialog {

    val view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_text, null, false)
    val edit = view.etInput
    hint?.let(edit::setHint)
    val builder = AlertDialog.Builder(this)
        .setView(view)
        .setPositiveButton(R.string.input_ok) { _, _ -> success(edit.text.toString()) }
        .setNegativeButton(R.string.input_cancel) { _, _ -> cancel() }
    title?.let(builder::setTitle)
    message?.let(builder::setMessage)
    return builder.create();
}