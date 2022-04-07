package com.scoto.fodamy.ui.walkthrough.model

import androidx.annotation.StringRes
import com.scoto.fodamy.R

/**
 * @author Sefa ÇOTOĞLU
 * Created 17.12.2021 at 10:51
 */

data class Walkthrough(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val image: Int
) {
    companion object {
        fun getPrePopulatedData(): List<Walkthrough> =
            listOf(
                Walkthrough(
                    R.string.walkthrough_title_1,
                    R.string.walkthrough_descriptions_1,
                    R.drawable.walkthrough1
                ),
                Walkthrough(
                    R.string.walkthrough_title_2,
                    R.string.walkthrough_descriptions_2,
                    R.drawable.walkthrough2
                ),
                Walkthrough(
                    R.string.walkthrough_title_3,
                    R.string.walkthrough_descriptions_3,
                    R.drawable.walkthrough3
                ),
                Walkthrough(
                    R.string.walkthrough_title_4,
                    R.string.walkthrough_descriptions_4,
                    R.drawable.walkthrough4
                ),
            )
    }
}
