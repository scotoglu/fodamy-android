package com.scoto.fodamy.ui.home.pages

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentPagesOfTabBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagesOfTab : BaseFragment<FragmentPagesOfTabBinding>(
    R.layout.fragment_pages_of_tab
) {


    private val viewModel: PagesOfTabViewModel by viewModels()
    private lateinit var adapter: RecipesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = RecipesAdapter()

        adapter.addLoadStateListener {state ->
            //Check
        }
        setupRv()

        viewModel.recipes.observe(viewLifecycleOwner, {
//            adapter.submitData(viewLifecycleOwner.lifecycle, it)
            Log.d(TAG, "onViewCreated:$it ")
        })

    }

    private fun setupRv() {
        binding.rvRecipes.apply {
            setHasFixedSize(true)
            adapter = adapter
        }
    }
companion object{
    private const val TAG = "PagesOfTab"
}
}