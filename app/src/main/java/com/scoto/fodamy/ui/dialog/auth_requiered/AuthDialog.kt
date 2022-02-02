package com.scoto.fodamy.ui.dialog.auth_requiered

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.databinding.DialogAuthBinding
import com.scoto.fodamy.ext.onClick

class AuthDialog : DialogFragment() {

    private var _binding: DialogAuthBinding? = null
    private val binding: DialogAuthBinding get() = _binding!!

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAuthBinding.inflate(inflater, container, false)

        val navController = findNavController()

        binding.apply {
            btnCancel.onClick { dismiss() }
            btnLogin.onClick { navController.navigate(AuthDialogDirections.actionAuthDialogToLoginFlow()) }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
