package com.scoto.fodamy.ui.recipe_details

import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeDetailsBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment :
    BaseFragment<FragmentRecipeDetailsBinding, RecipeDetailsViewModel>(
        R.layout.fragment_recipe_details
    ) {

    override fun registerObservables() {
        super.registerObservables()
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("ACTION")
            ?.observe(viewLifecycleOwner) {
                if (it.equals("unfollow")) {
                    viewModel.unfollow()
                }
            }
    }
}
