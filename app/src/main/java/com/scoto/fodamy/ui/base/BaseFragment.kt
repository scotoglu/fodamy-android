package com.scoto.fodamy.ui.base

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

    lateinit var binding: VB
    lateinit var viewModel: VM
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    @Suppress("UNCHECKED_CAST")
    val viewModelClass: Class<VM>
        get() = findGenericSuperclass<BaseFragment<VB, VM>>()
            ?.actualTypeArguments
            ?.getOrNull(1) as? Class<VM>
            ?: throw IllegalStateException("viewModelClass does not equal Class<VM>")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(viewModelClass)
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        navController = findNavController()
        bottomNavigationView =
            (activity as AppCompatActivity).findViewById(R.id.bottom_navigation_view)
        initViews()
        viewStateObservables()
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

    private fun viewStateObservables() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.baseEvent.observe(viewLifecycleOwner) { event ->
                eventHandler(event)
            }
        }
    }

    private fun eventHandler(event: BaseViewState) {
        when (event) {
            BaseViewState.BackTo -> navController.popBackStack()
            is BaseViewState.NavigateTo -> navController.navigate(event.directions)
            is BaseViewState.ShowMessage -> snackbar(event.message, bottomNavigationView)
            is BaseViewState.OpenDialog -> navController.navigate(event.actionId)
        }
    }
}