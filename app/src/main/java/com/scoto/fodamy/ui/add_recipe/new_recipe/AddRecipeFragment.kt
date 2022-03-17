package com.scoto.fodamy.ui.add_recipe.new_recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.scoto.domain.models.CategoryDraft
import com.scoto.domain.models.NumberOfPerson
import com.scoto.domain.models.TimeOfRecipe
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentAddRecipeBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.03.2022
 */
@AndroidEntryPoint
class AddRecipeFragment : BaseFragment<FragmentAddRecipeBinding, AddRecipeViewModel>(
    R.layout.fragment_add_recipe
) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun registerObservables() {
        super.registerObservables()
        viewModel.serving.observe(viewLifecycleOwner) {
            binding.numberOfPerson.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_dropdown,
                    it
                )
            )
        }
        viewModel.times.observe(viewLifecycleOwner) {
            binding.timeOfRecipe.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_dropdown,
                    it
                )
            )
        }
        viewModel.categories.observe(viewLifecycleOwner) {
            binding.category.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_dropdown,
                    it
                )
            )
        }
    }

    override fun addItemClicks() {
        super.addItemClicks()
        binding.apply {
            timeOfRecipe.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position) as TimeOfRecipe
                viewModel?.timeOfRecipe = selected
            }
            numberOfPerson.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position) as NumberOfPerson
                viewModel?.numberOfPerson = selected
            }
            category.setOnItemClickListener { parent, _, position, _ ->
                val selected = parent.getItemAtPosition(position) as CategoryDraft
                viewModel?.category = selected
            }
        }
    }
}
