package com.scoto.fodamy.ui.add_recipe.publish_draft

import androidx.core.view.isVisible
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentPublishDraftBinding
import com.scoto.fodamy.ui.add_recipe.choose_photo.adapter.CaptureAndPickPhotoAdapter
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.03.2022
 */
@AndroidEntryPoint
class PublishDraftFragment : BaseFragment<FragmentPublishDraftBinding, PublishDraftViewModel>(
    R.layout.fragment_publish_draft
) {
    private lateinit var captureAndPickPhotoAdapter: CaptureAndPickPhotoAdapter
    override fun registerObservables() {
        super.registerObservables()
        viewModel.recipeDraft.observe(viewLifecycleOwner) {
            binding.apply {
                if (it.image.isNotEmpty()) {
                    tvLoadedPhotos.isVisible = true
                    captureAndPickRecyclerview.isVisible = true
                    captureAndPickPhotoAdapter.hideDelete()
                    captureAndPickPhotoAdapter.setData(
                        it.image
                    )
                } else {
                    tvLoadedPhotos.isVisible = false
                    captureAndPickRecyclerview.isVisible = false
                }
            }
        }
    }

    override fun initViews() {
        super.initViews()
        captureAndPickPhotoAdapter = CaptureAndPickPhotoAdapter()
        binding.apply {
            captureAndPickRecyclerview.adapter = captureAndPickPhotoAdapter
        }
    }
}
