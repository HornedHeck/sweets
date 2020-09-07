package com.hornedheck.yankeesphisics.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hornedheck.yankeesphisics.R
import com.hornedheck.yankeesphisics.data.ConversionGroup
import com.hornedheck.yankeesphisics.databinding.FragmentFieldsBinding

class FieldsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFieldsBinding>(
            inflater,
            R.layout.fragment_fields,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }
}