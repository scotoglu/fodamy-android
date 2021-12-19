package com.scoto.fodamy.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.CustomToolbarBinding
import com.scoto.fodamy.ext.onClick

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) :
    ConstraintLayout(context, attr, defStyle) {

    interface OnBackListener {
        fun onClick()
    }

    interface OnEndIconListener {
        fun onClick()
    }

//    var onBackClick: (() -> Unit)? = null
//    var onEndClick: (() -> Unit)? = null

    var onBackListener: OnBackListener? = null
    var onEndIconListener: OnEndIconListener? = null

    private var binding: CustomToolbarBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)
        binding = CustomToolbarBinding.bind(view)

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
            setEndIconVisibility(
                if (typedArray.hasValue(R.styleable.CustomToolbar_endIconVisibility)) {
                    typedArray.getBoolean(R.styleable.CustomToolbar_endIconVisibility, true)
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
            toolbarEndIcon.setOnClickListener {
                Log.d("Custom Toolbar", "setEndIcon:called ")
                onEndIconListener?.onClick()
            }
        }
    }

    fun setEndIconVisibility(isVisible: Boolean) {
        binding.toolbarEndIcon.apply {
            this.isVisible = isVisible
            setOnClickListener {
                Log.d("Custom Toolbar", "setEndIconVisibility:called ")
                onEndIconListener?.onClick()
            }
        }
    }

    fun setBackButtonVisibility(isVisible: Boolean) {
        binding.apply {
            toolbarIvBack.isVisible = isVisible
            toolbarTvBack.isVisible = isVisible
            toolbarIvBack.onClick { onBackListener?.onClick() }
            toolbarTvBack.onClick { onBackListener?.onClick() }
        }
    }
}
