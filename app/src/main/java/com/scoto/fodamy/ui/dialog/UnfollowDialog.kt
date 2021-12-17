package com.scoto.fodamy.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.DialogUnfollowBinding
import com.scoto.fodamy.ext.onClick

class UnfollowDialog : BottomSheetDialogFragment() {
    private var _binding: DialogUnfollowBinding? = null
    private val binding: DialogUnfollowBinding get() = _binding!!


    var dialogAction: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetDialog)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogUnfollowBinding.inflate(inflater, container, false)



        binding.apply {
            btnCancel.onClick { dismiss() }
            btnUnfollow.onClick {
                findNavController()
                    .previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("ACTION", "unfollow")

                findNavController().popBackStack()
            }
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "UnfollowDialog"
    }
}