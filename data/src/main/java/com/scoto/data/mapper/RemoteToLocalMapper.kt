package com.scoto.data.mapper

import com.scoto.data.local.local_dto.CategoryDb
import com.scoto.data.local.local_dto.ImageDb
import com.scoto.data.local.local_dto.NumberOfPersonDb
import com.scoto.data.local.local_dto.RecipeDb
import com.scoto.data.local.local_dto.TimeOfRecipeDb
import com.scoto.data.local.local_dto.UserDb
import com.scoto.data.remote.remote_dto.CategoryResponse
import com.scoto.data.remote.remote_dto.ImageResponse
import com.scoto.data.remote.remote_dto.NumberOfPersonResponse
import com.scoto.data.remote.remote_dto.RecipeResponse
import com.scoto.data.remote.remote_dto.TimeOfRecipeResponse
import com.scoto.data.remote.remote_dto.UserResponse

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.02.2022 at 14:51
 */

fun RecipeResponse.toLocalDto(): RecipeDb {
    return RecipeDb(
        id = this.id,
        title = this.title,
        definition = this.definition,
        ingredients = this.ingredients,
        directions = this.directions,
        difference = this.difference,
        isEditorChoice = this.isEditorChoice,
        isLiked = this.isLiked,
        likeCount = this.likeCount,
        commentCount = this.commentCount,
        user = this.user.toLocalDto(),
        timeOfRecipe = this.timeOfRecipe.toLocalDto(),
        numberOfPerson = this.numberOfPerson.toLocalDto(),
        category = this.category.toLocalDto(),
        image = this.images.map { it.toLocalDto() }
    )
}

fun ImageResponse.toLocalDto(): ImageDb {
    return ImageDb(
        width = this.width,
        height = this.height,
        url = this.url,
        image = null
    )
}

fun UserResponse.toLocalDto(): UserDb {
    return UserDb(
        id = this.id,
        name = this.name ?: "",
        username = this.username ?: "",
        favoritesCount = this.favoritesCount ?: 0,
        followedCount = this.followedCount ?: 0,
        followingCount = this.followingCount ?: 0,
        isFollowing = this.isFollowing,
        likesCount = this.likesCount ?: 0,
        recipeCount = this.recipeCount ?: 0,
        image = this.image?.toLocalDto()?:ImageDb(0, "", null, 0)
    )
}

fun TimeOfRecipeResponse.toLocalDto(): TimeOfRecipeDb {
    return TimeOfRecipeDb(id = this.id, text = this.text)
}

fun NumberOfPersonResponse.toLocalDto(): NumberOfPersonDb {
    return NumberOfPersonDb(id = this.id, text = this.text)
}

fun CategoryResponse.toLocalDto(): CategoryDb {
    return CategoryDb(
        id = this.id,
        name = this.name ?: "",
        recipes = this.recipes?.map { it.toLocalDto() } ?: emptyList(),
        image = this.image?.toLocalDto() ?: ImageDb(0, "", null, 0)
    )
}