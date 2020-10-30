package com.hornedheck.echos.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.hornedheck.echos.EchosApp
import moxy.MvpAppCompatActivity
import timber.log.Timber

abstract class BaseActivity(@LayoutRes private val layoutRes: Int = 0) : MvpAppCompatActivity(),
    BaseView {

    protected val appComponent
        get() = (application as EchosApp).appComponent

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

    override fun showError(e: Throwable) {
        Timber.e(e)
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }
}