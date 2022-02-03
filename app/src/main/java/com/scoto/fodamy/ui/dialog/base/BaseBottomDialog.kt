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
import com.scoto.fodamy.ui.MainActivity
import com.scoto.fodamy.ui.base.BaseViewEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.util.findGenericSuperclass

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.02.2022 at 00:09
 */
abstract class BaseBottomDialog<VB : ViewDataBinding, VM : BaseViewModel>(
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
        dialogObserver()
        binding.setVariable(BR.vm, viewModel)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun dialogObserver() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            val activity = (requireActivity() as MainActivity)
            if (isLoading) activity.showDialog()
            else activity.hideDialog()
        }
    }

    private fun eventObserver() {
        viewModel.baseEvent.observe(viewLifecycleOwner) { event ->
            eventHandler(event)
        }
    }

    private fun eventHandler(event: BaseViewEvent) {
        when (event) {
            BaseViewEvent.BackTo -> findNavController().popBackStack()
            is BaseViewEvent.ShowMessage -> snackbar(event.message, null)
            is BaseViewEvent.ShowMessageRes -> snackbar(getString(event.messageId), null)
            is BaseViewEvent.Extras -> setFragmentResult(
                REQUEST_KEY,
                bundleOf(event.key to event.value)
            )
            is BaseViewEvent.NavigateTo -> return
            is BaseViewEvent.OpenDialog -> return
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
