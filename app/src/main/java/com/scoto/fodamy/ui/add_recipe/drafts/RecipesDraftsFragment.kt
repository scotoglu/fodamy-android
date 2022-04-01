package com.scoto.fodamy.ui.add_recipe.drafts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentDraftsBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
@AndroidEntryPoint
class RecipesDraftsFragment : BaseFragment<FragmentDraftsBinding, RecipeDraftsViewModel>(
    R.layout.fragment_drafts
) {
    override val isSharedViewModel: Boolean = true

    private lateinit var draftsAdapter: RecipeDraftsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getDrafts()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun registerObservables() {
        super.registerObservables()
        viewModel.drafts.observe(viewLifecycleOwner) {
            draftsAdapter.setData(it)
        }

        viewModel.event.observe(viewLifecycleOwner) { event ->
            val logoutIconVisibility: Boolean = when (event) {
                is RecipeDraftsEvent.IsLogin -> event.isLogin
                RecipeDraftsEvent.Success -> false
            }
            binding.customToolbar.setEndIconVisibility(logoutIconVisibility)
        }
    }

    override fun initViews() {
        super.initViews()
        draftsAdapter = RecipeDraftsAdapter()
        binding.draftRecyclerview.apply {
            setHasFixedSize(true)
            adapter = draftsAdapter
        }
    }

    override fun addItemClicks() {
        super.addItemClicks()
        draftsAdapter.apply {
            itemClicked = {
                viewModel.toPublishDraft(it)
            }
            deleteClicked = {
                viewModel.deleteDraft(it.id)
            }
            editClicked = {
                viewModel.toEdit(it)
            }
            addPhotoClicked = {
                viewModel.toChoosePhoto(it)
            }
        }
    }
}
