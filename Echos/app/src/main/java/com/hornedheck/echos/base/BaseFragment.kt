package com.hornedheck.echos.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import moxy.MvpAppCompatFragment

abstract class BaseFragment(@LayoutRes private val layoutRes: Int = 0) :
    MvpAppCompatFragment(),
    BaseView {

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (layoutRes == 0) {
            super.onCreateView(inflater, container, savedInstanceState)
        } else {
            inflater.inflate(layoutRes, container, false)
        }
    }

    override fun showError(title: String, content: String) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }
}