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
import com.scoto.fodamy.databinding.DialogCommentBinding
import com.scoto.fodamy.ext.onClick
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CommentDialog : BottomSheetDialogFragment() {


    private var _binding: DialogCommentBinding? = null
    private val binding: DialogCommentBinding get() = _binding!!


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
        _binding = DialogCommentBinding.inflate(inflater, container, false)

        binding.btbDelete.onClick {

            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "DialogAction",
                "DELETE"
            )
            findNavController().popBackStack()

        }
        binding.btnEdit.onClick {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "DialogAction",
                "EDIT"
            )
            findNavController().popBackStack()
        }
        binding.btnCancel.onClick {
            dismiss()
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val TAG = "CommentDialog"
    }
}