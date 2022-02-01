package com.scoto.fodamy.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scoto.fodamy.BR
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.util.findGenericSuperclass

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.12.2021 at 14:48
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int
) : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    lateinit var viewModel: VM
    open val isSharedViewModel = false
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var dialog: Dialog

    @Suppress("UNCHECKED_CAST")
    val viewModelClass: Class<VM>
        get() = findGenericSuperclass<BaseFragment<VB, VM>>()
            ?.actualTypeArguments
            ?.getOrNull(1) as? Class<VM>
            ?: throw IllegalStateException("viewModelClass does not equal Class<VM>")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            if (isSharedViewModel) {
                requireActivity()
            } else {
                this
            }
        )[viewModelClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        navController = findNavController()
        bottomNavigationView =
            (activity as AppCompatActivity).findViewById(R.id.bottom_navigation_view)

        dialog = Dialog(requireActivity())
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.progress_custom_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        initViews()
        eventObserver()
        registerObservables()
        addAdapterLoadStateListener()
        addItemClicks()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, viewModel)

        return binding.root
    }

    protected open fun initViews() {}
    protected open fun registerObservables() {}
    protected open fun addItemClicks() {}
    protected open fun addAdapterLoadStateListener() {}

    private fun eventObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.baseEvent.observe(viewLifecycleOwner) { event ->
                eventHandler(event)
            }
        }
    }

    private fun eventHandler(event: BaseViewEvent) {
        when (event) {
            BaseViewEvent.BackTo -> navController.popBackStack()
            is BaseViewEvent.NavigateTo -> navController.navigate(event.directions)
            is BaseViewEvent.ShowMessage -> snackbar(event.message, bottomNavigationView)
            is BaseViewEvent.OpenDialog -> navController.navigate(event.actionId)
            is BaseViewEvent.ShowMessageRes -> snackbar(
                getString(event.messageId),
                bottomNavigationView
            )
            BaseViewEvent.ShowDialog -> dialog.show()
            BaseViewEvent.HideDialog -> dialog.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
