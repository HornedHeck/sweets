package com.hornedheck.yankeesphisics.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hornedheck.yankeesphisics.R
import com.hornedheck.yankeesphisics.databinding.FragmentKeyboardBinding

class KeyboardFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentKeyboardBinding>(
            inflater,
            R.layout.fragment_keyboard,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }
}