package com.hornedheck.yankeesphisics.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.hornedheck.yankeesphisics.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clipboardData.observe(requireActivity()) {
            ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
                ?.setPrimaryClip(ClipData.newPlainText("", it))

            Toast.makeText(requireContext() , it , Toast.LENGTH_SHORT).show()
        }
    }

}