package com.scoto.fodamy.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.DialogAuthBinding
import com.scoto.fodamy.ext.onClick


class AuthDialog() : DialogFragment(R.layout.dialog_auth) {

    private var _binding: DialogAuthBinding? = null
    private val binding: DialogAuthBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
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
            btnLogin.onClick { navController.navigate(AuthDialogDirections.actionAuthDialogToLoginFragment()) }
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "AuthDialog"
    }
}