package com.scoto.fodamy.ui.dialog.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.scoto.fodamy.BR
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.util.findGenericSuperclass

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.02.2022 at 00:09
 */
abstract class BaseBottomDialog<VB : ViewDataBinding, VM : BaseDialogViewModel>(
    @LayoutRes private val layoutId: Int
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    val viewModelClass: Class<VM>
        get() = findGenericSuperclass<BaseBottomDialog<VB, VM>>()
            ?.actualTypeArguments
            ?.getOrNull(1) as? Class<VM>
            ?: throw IllegalStateException("viewModelClass does not equal Class<VM>")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        viewModel = ViewModelProvider(this)[viewModelClass]
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetDialog)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        eventObserver()
        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            eventHandler(event)
        }
    }

    private fun eventHandler(event: BaseDialogEvent) {
        when (event) {
            BaseDialogEvent.Close -> findNavController().popBackStack()
            is BaseDialogEvent.ShowMessage -> snackbar(event.message, null)
            is BaseDialogEvent.ShowMessageWithRes -> snackbar(getString(event.message), null)
            is BaseDialogEvent.Extras -> setFragmentResult(REQUEST_KEY, bundleOf(event.key to event.value))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUEST_KEY = "DialogAction"
    }
}
