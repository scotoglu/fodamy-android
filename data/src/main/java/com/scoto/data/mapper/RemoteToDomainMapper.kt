package com.scoto.data.mapper

import com.scoto.data.remote.remote_dto.AuthResponse
import com.scoto.data.remote.remote_dto.CategoryResponse
import com.scoto.data.remote.remote_dto.CommentResponse
import com.scoto.data.remote.remote_dto.CommonResponse
import com.scoto.data.remote.remote_dto.ImageResponse
import com.scoto.data.remote.remote_dto.NumberOfPersonResponse
import com.scoto.data.remote.remote_dto.PaginationResponse
import com.scoto.data.remote.remote_dto.RecipePagingResponse
import com.scoto.data.remote.remote_dto.RecipeResponse
import com.scoto.data.remote.remote_dto.TimeOfRecipeResponse
import com.scoto.data.remote.remote_dto.UserResponse
import com.scoto.domain.models.Auth
import com.scoto.domain.models.Category
import com.scoto.domain.models.Comment
import com.scoto.domain.models.Common
import com.scoto.domain.models.Image
import com.scoto.domain.models.NumberOfPerson
import com.scoto.domain.models.Pagination
import com.scoto.domain.models.Recipe
import com.scoto.domain.models.RecipePaging
import com.scoto.domain.models.TimeOfRecipe
import com.scoto.domain.models.User

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:56
 */

fun RecipeResponse.toDomainModel(): Recipe =
    Recipe(
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
        images = this.images.map {
            it.toDomainModel()
        }
    )

fun ImageResponse.toDomainModel(): Image {
    return Image(
        width = this.width,
        height = this.height,
        url = this.url
    )
}

fun UserResponse.toDomainModel(): User =
    User(
        id = this.id,
        image = this.image?.toDomainModel(),
        name = this.name ?: "",
        username = this.username ?: "",
        favoritesCount = this.favoritesCount ?: 0,
        followingCount = this.followingCount ?: 0,
        followedCount = this.followedCount ?: 0,
        isFollowing = this.isFollowing,
        likesCount = this.likesCount ?: 0,
        recipeCount = this.recipeCount ?: 0
    )

fun TimeOfRecipeResponse.toDomainModel(): TimeOfRecipe =
    TimeOfRecipe(
        id = this.id,
        text = this.text
    )

fun NumberOfPersonResponse.toDomainModel(): NumberOfPerson =
    NumberOfPerson(
        id = this.id,
        text = this.text
    )

fun CategoryResponse.toDomainModel(): Category =
    Category(
        id = this.id,
        name = this.name ?: "",
        image = image?.toDomainModel(),
        recipes = recipes?.map {
            it.toDomainModel()
        }

    )

fun CommentResponse.toDomainModel(): Comment =
    Comment(
        id = this.id,
        text = this.text,
        user = this.user.toDomainModel(),
        difference = this.difference
    )

fun AuthResponse.toDomainModel(): Auth =
    Auth(
        token = this.token,
        user = this.user.toDomainModel()
    )

fun CommonResponse.toDomainModel(): Common =
    Common(
        code = this.code ?: "",
        message = this.message ?: "",
        error = this.error ?: ""
    )

fun PaginationResponse.toDomainModel(): Pagination =
    Pagination(
        currentPage = this.currentPage,
        firstItem = this.firstItem,
        lastItem = this.lastItem,
        lastPage = this.lastPage,
        perPage = this.perPage,
        total = this.total
    )

fun RecipePagingResponse.toDomainModel(): RecipePaging =
    RecipePaging(
        data = this.data.map { it.toDomainModel() },
        pagination = this.pagination.toDomainModel()
    )
