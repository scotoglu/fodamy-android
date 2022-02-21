package com.scoto.data.mapper

import com.scoto.data.local.local_dto.CategoryDb
import com.scoto.data.local.local_dto.CommentDb
import com.scoto.data.local.local_dto.ImageDb
import com.scoto.data.local.local_dto.NumberOfPersonDb
import com.scoto.data.local.local_dto.RecipeDb
import com.scoto.data.local.local_dto.TimeOfRecipeDb
import com.scoto.data.local.local_dto.UserDb
import com.scoto.domain.models.Category
import com.scoto.domain.models.Comment
import com.scoto.domain.models.Image
import com.scoto.domain.models.NumberOfPerson
import com.scoto.domain.models.Recipe
import com.scoto.domain.models.TimeOfRecipe
import com.scoto.domain.models.User

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.02.2022 at 00:09
 */

fun RecipeDb.toDomainModel(): Recipe {
    return Recipe(
        id = this.id,
        title = this.title ?: "",
        definition = this.definition,
        ingredients = this.ingredients,
        directions = this.directions,
        difference = this.difference,
        isEditorChoice = this.isEditorChoice,
        isLiked = this.isLiked,
        likeCount = this.likeCount,
        commentCount = this.commentCount,
        user = this.user.toDomainModel(),
        timeOfRecipe = this.timeOfRecipe.toDomainModel(),
        numberOfPerson = this.numberOfPerson.toDomainModel(),
        category = this.category.toDomainModel(),
        images = this.image.map { it.toDomainModel() }
    )
}

fun UserDb.toDomainModel(): User {
    return User(
        id = this.id,
        image = this.image.toDomainModel(),
        name = this.name,
        username = this.username,
        favoritesCount = this.favoritesCount,
        followedCount = this.followedCount,
        followingCount = this.followingCount,
        isFollowing = this.isFollowing,
        likesCount = this.likesCount,
        recipeCount = this.recipeCount
    )
}

fun ImageDb.toDomainModel(): Image {
    return Image(
        height = this.height,
        width = this.width,
        url = this.url
    )
}

fun TimeOfRecipeDb.toDomainModel(): TimeOfRecipe {
    return TimeOfRecipe(id, text)
}

fun NumberOfPersonDb.toDomainModel(): NumberOfPerson {
    return NumberOfPerson(id, text)
}

fun CategoryDb.toDomainModel(): Category {
    return Category(
        id = this.id,
        name = this.name,
        image = this.image.toDomainModel(),
        recipes = this.recipes.map { it.toDomainModel() }
    )
}

fun CommentDb.toDomainModel(): Comment {
    return Comment(
        id = this.id,
        text = this.text,
        user = this.user.toDomainModel(),
        difference = this.difference
    )
}