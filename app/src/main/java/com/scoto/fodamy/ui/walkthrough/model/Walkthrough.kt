package com.scoto.fodamy.ui.walkthrough.model

import com.scoto.fodamy.R

/**
 * @author Sefa ÇOTOĞLU
 * Created 17.12.2021 at 10:51
 */
data class Walkthrough(
    val title: String,
    val description: String,
    val image: Int
) {
    companion object {
        fun getPrePopulatedData(): List<Walkthrough> =
            listOf(
                Walkthrough(
                    "Welcom to Fodamy Network!",
                    "Fodamy is the best place to find your favorite recipes in all around the word.",
                    R.drawable.walkthrough1
                ),
                Walkthrough(
                    "Finding recipes were not that easy.",
                    "Fodamy is the best place to find your favorite recipes in all around the word.",
                    R.drawable.walkthrough2
                ),
                Walkthrough(
                    "Add new recipe.",
                    "Fodamy is the best place to find your favorite recipes in all around the word.",
                    R.drawable.walkthrough3
                ),
                Walkthrough(
                    "Share recipes with others.",
                    "Fodamy is the best place to find your favorite recipes in all around the word.",
                    R.drawable.walkthrough4
                ),

                )
    }

}
