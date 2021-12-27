package com.scoto.fodamy.util

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.CustomStateViewBinding
import com.scoto.fodamy.ext.onClick


class CustomStateView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attr, defStyle) {

    private var binding: CustomStateViewBinding

    var isLoading: Boolean = false
    var isErrorOccured: Boolean = false
    var onRetryClick: OnRetryClick? = null

    interface OnRetryClick {
        fun onClick()
    }

    init {
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_state_view, this, true)
        binding = CustomStateViewBinding.bind(view)

        val typedArray = context.obtainStyledAttributes(attr, R.styleable.CustomStateView)
        isLoading = typedArray.getBoolean(R.styleable.CustomStateView_isLoading, false)
        isErrorOccured =
            typedArray.getBoolean(R.styleable.CustomStateView_isErrorOccured, false)


        try {

            setLoadingState(
                isLoading
            )
            setErrorState(
                isErrorOccured
            )

        } finally {
            typedArray.recycle()
        }
    }


    fun setLoadingState(isLoading: Boolean) {
        binding.apply {
            progressbar.isVisible = isLoading
            tvLoading.isVisible = isLoading
        }
    }

    fun setErrorState(isErrorOccured: Boolean) {
        binding.apply {
            ivWarning.isVisible = isErrorOccured
            tvGoesWrong.isVisible = isErrorOccured
            btnRetry.isVisible = isErrorOccured
            btnRetry.onClick { onRetryClick?.onClick() }

        }
    }

    companion object {
        private const val TAG = "CustomStateView"
    }
}