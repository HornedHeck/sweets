package com.hornedheck.echos.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import moxy.MvpAppCompatActivity

abstract class BaseActivity(@LayoutRes private val layoutRes: Int = 0) : MvpAppCompatActivity(),
    BaseView {

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        if (layoutRes != 0) {
            setContentView(layoutRes)
        }
    }

    override fun showError(title: String, content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }
}