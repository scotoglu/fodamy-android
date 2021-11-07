package com.scoto.fodamy.ui.walkthrough.walkthrough_pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentOnboardingBinding

class WalkthroughPagesFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"

        fun getInstance(position: Int) = WalkthroughPagesFragment().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = requireArguments().getInt(ARG_POSITION)
        val titles = requireContext().resources.getStringArray(R.array.walkthrouh_title)
        val descriptions =
            requireContext().resources.getStringArray(R.array.walkthrouhg_descriptions)

        val images = listOf(
            R.drawable.walkthrough1,
            R.drawable.walkthrough2,
            R.drawable.walkthrough3,
            R.drawable.walkthrough4
        )

       binding.apply{
            ivWalkthrough.setImageResource(images[position])
            tvWalkthroughTitle.text = titles[position]
            tvWalkthroughDescription.text = descriptions[position]
        }
    }
}