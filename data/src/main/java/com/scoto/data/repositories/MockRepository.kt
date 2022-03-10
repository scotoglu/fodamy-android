package com.scoto.data.repositories

import androidx.paging.PagingData
import com.scoto.data.local.converters.fromJson
import com.scoto.data.mapper.toDomainModel
import com.scoto.data.remote.exceptions.Authentication
import com.scoto.data.remote.exceptions.GettingEmptyListItem
import com.scoto.data.remote.exceptions.SimpleHttpException
import com.scoto.data.remote.remote_dto.AuthResponse
import com.scoto.data.remote.remote_dto.CategoryResponse
import com.scoto.data.remote.remote_dto.CommentResponse
import com.scoto.data.remote.remote_dto.CommonResponse
import com.scoto.data.remote.remote_dto.RecipeResponse
import com.scoto.data.remote.remote_dto.UserResponse
import com.scoto.data.utils.FAIL_AUTH_LOGIN
import com.scoto.data.utils.FAIL_FORBIDDEN
import com.scoto.data.utils.FAIL_RECIPE_DETAILS
import com.scoto.data.utils.JsonReader
import com.scoto.data.utils.SUCCESS_AUTH_LOGIN
import com.scoto.data.utils.SUCCESS_AUTH_LOGOUT
import com.scoto.data.utils.SUCCESS_AUTH_REGISTER
import com.scoto.data.utils.SUCCESS_CATEGORY
import com.scoto.data.utils.SUCCESS_CATEGORY_RECIPES
import com.scoto.data.utils.SUCCESS_RECIPE_ALL
import com.scoto.data.utils.SUCCESS_RECIPE_COMMENTS
import com.scoto.data.utils.SUCCESS_RECIPE_DETAILS
import com.scoto.data.utils.SUCCESS_USER_DETAILS
import com.scoto.domain.models.Auth
import com.scoto.domain.models.Category
import com.scoto.domain.models.Comment
import com.scoto.domain.models.Common
import com.scoto.domain.models.Recipe
import com.scoto.domain.models.User
import com.scoto.domain.repositories.AuthRepository
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 8.03.2022 at 16:02
 */
class MockRepository @Inject constructor(
    private val jsonReader: JsonReader
) :
    AuthRepository,
    RecipeRepository,
    UserRepository {

    var willThrowException = false
    var isWillBeFail = false
    var throwException: Exception? = null

    override suspend fun register(username: String, email: String, password: String) {
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_AUTH_REGISTER)
        fromJson<AuthResponse>(jsonString)
    }

    override suspend fun forgot(email: String): Auth {
        TODO("Service is not working")
    }

    override suspend fun logout(): Common {
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_AUTH_LOGOUT)
        return fromJson<CommonResponse>(jsonString).toDomainModel()
    }

    override suspend fun login(username: String, password: String) {
        if (isWillBeFail) {
            val failJson = jsonReader.readJsonFromFile(FAIL_AUTH_LOGIN)
            failJson.parseAndThrow()
        }
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_AUTH_LOGIN)
        fromJson<AuthResponse>(jsonString)
    }

    override suspend fun getEditorChoicePaging(): Flow<PagingData<Recipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLastAddedPaging(): Flow<PagingData<Recipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeCommentsPaging(recipeId: Int): Flow<PagingData<Comment>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriesPaging(): Flow<PagingData<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun getEditorChoiceRecipes(page: Int): List<Recipe> {
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_RECIPE_ALL)
        return fromJson<List<RecipeResponse>>(jsonString).map { it.toDomainModel() }
    }

    override suspend fun getLastAdded(page: Int): List<Recipe> {
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_RECIPE_ALL)
        return fromJson<List<RecipeResponse>>(jsonString).map { it.toDomainModel() }
    }

    override suspend fun getRecipeById(recipeId: Int, onlyRemote: Boolean): Recipe {
        if (isWillBeFail) {
            val failJson = jsonReader.readJsonFromFile(FAIL_RECIPE_DETAILS)
            failJson.parseAndThrow()
        }
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_RECIPE_DETAILS)
        return fromJson<RecipeResponse>(jsonString).toDomainModel()
    }

    override suspend fun getRecipeComments(recipeId: Int, page: Int): List<Comment> {
        if (isWillBeFail) {
            val failJson = jsonReader.readJsonFromFile(FAIL_RECIPE_DETAILS)
            failJson.parseAndThrow()
        }
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_RECIPE_COMMENTS)
        return fromJson<List<CommentResponse>>(jsonString).map { it.toDomainModel() }
    }

    override suspend fun getFirstComment(recipeId: Int): Comment {
        if (willThrowException) throw GettingEmptyListItem()
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_RECIPE_COMMENTS)
        return fromJson<List<CommentResponse>>(jsonString).map { it.toDomainModel() }[0]
    }

    override suspend fun sendComment(recipeId: Int, text: String) {
        if (willThrowException) throw Authentication()
    }

    override suspend fun editComment(recipeId: Int, commentId: Int, text: String) {
        if (willThrowException) throw Authentication()
    }

    override suspend fun deleteComment(recipeId: Int, commentId: Int) {
        if (willThrowException) throw Authentication()
    }

    override suspend fun likeRecipe(recipeId: Int) {
        if (willThrowException) throw Authentication()
        if (isWillBeFail) {
            val failJson = jsonReader.readJsonFromFile(FAIL_FORBIDDEN)
            failJson.parseAndThrow()
        }
    }

    override suspend fun dislikeRecipe(recipeId: Int) {
        if (willThrowException) throw Authentication()
        if (isWillBeFail) {
            val failJson = jsonReader.readJsonFromFile(FAIL_FORBIDDEN)
            failJson.parseAndThrow()
        }
    }

    override suspend fun getCategoriesWithRecipes(page: Int): List<Category> {
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_CATEGORY)
        val data = fromJson<List<CategoryResponse>>(jsonString)
        return data.filter { it.recipes?.size!! > 0 }.map { it.toDomainModel() }
    }

    override suspend fun getRecipesByCategory(categoryId: Int, page: Int): List<Recipe> {
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_CATEGORY_RECIPES)
        return fromJson<List<RecipeResponse>>(jsonString).map { it.toDomainModel() }
    }

    override suspend fun followUser(followedId: Int) {
        if (willThrowException) throw Authentication()
    }

    override suspend fun unFollowUser(followedId: Int) {
        if (willThrowException) throw Authentication()
    }

    override suspend fun getUserDetails(userId: Int): User {
        if (willThrowException) throw Authentication()
        val jsonString = jsonReader.readJsonFromFile(SUCCESS_USER_DETAILS)
        return fromJson<UserResponse>(jsonString).toDomainModel()
    }

    private fun String.parseAndThrow(): Exception {
        val model = fromJson<CommonResponse>(this)
        throw SimpleHttpException(model.code, model.error)
    }
}
