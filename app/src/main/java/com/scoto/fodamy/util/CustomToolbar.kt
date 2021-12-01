package com.scoto.fodamy.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.CustomToolbarBinding

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) :
    ConstraintLayout(context, attr, defStyle) {

    private var endIcon: ImageView
    private var backIcon: ImageView

    private var binding: CustomToolbarBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)
        binding = CustomToolbarBinding.bind(view)


        endIcon = binding.toolbarEndIcon
        backIcon = binding.toolbarIvBack


        val typedArray = context.obtainStyledAttributes(attr, R.styleable.CustomToolbar)
        try {
            setTitle(
                if (typedArray.hasValue(R.styleable.CustomToolbar_title)) {
                    typedArray.getString(R.styleable.CustomToolbar_title)
                } else {
                    null
                }
            )
            setBackButtonVisibility(
                if (typedArray.hasValue(R.styleable.CustomToolbar_backButtonVisible)) {
                    typedArray.getBoolean(R.styleable.CustomToolbar_backButtonVisible, true)
                } else {
                    false
                }
            )
            setEndIcon(
                if (typedArray.hasValue(R.styleable.CustomToolbar_endIcon)) {
                    typedArray.getDrawable(R.styleable.CustomToolbar_endIcon)
                } else {
                    null
                }
            )

        } finally {
            typedArray.recycle()
        }

    }

    fun getEndIcon(): ImageView = endIcon
    fun getBackIcon(): ImageView = backIcon

    fun setTitle(title: String?) {
        binding.apply {
            tvToolbarTitle.isVisible = title != null
            if (title != null) tvToolbarTitle.text = title
            toolbarLogo.isVisible = !tvToolbarTitle.isVisible
        }
    }

    fun setEndIcon(drawable: Drawable?) {
        binding.apply {
            drawable?.let { toolbarEndIcon.setImageDrawable(drawable) }
        }
    }

    fun setBackButtonVisibility(isVisible: Boolean) {
        binding.apply {
            toolbarIvBack.isVisible = isVisible
            toolbarTvBack.isVisible = isVisible
        }
    }
}