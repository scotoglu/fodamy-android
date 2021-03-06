package com.scoto.data.remote.services

import com.scoto.data.remote.remote_dto.CategoryDraftData
import com.scoto.data.remote.remote_dto.CategoryPagingResponse
import com.scoto.data.remote.remote_dto.CommentPagingResponse
import com.scoto.data.remote.remote_dto.CommentResponse
import com.scoto.data.remote.remote_dto.CommonResponse
import com.scoto.data.remote.remote_dto.RecipePagingResponse
import com.scoto.data.remote.remote_dto.RecipeResponse
import com.scoto.data.remote.remote_dto.RecipeServingResponse
import com.scoto.data.remote.remote_dto.RecipeTimesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:37
 */
interface RecipeService {

    @GET("editor-choices")
    suspend fun getEditorChoiceRecipes(
        @Query("page") page: Int
    ): RecipePagingResponse

    @GET("recipe")
    suspend fun getLastAddedRecipes(
        @Query("page") page: Int
    ): RecipePagingResponse

    @GET("recipe/{recipe_id}")
    suspend fun getRecipeById(
        @Path("recipe_id") recipeId: Int
    ): RecipeResponse

    //   Recipe Comment Related Operations
    @GET("recipe/{recipe_id}/comment")
    suspend fun getRecipeComments(
        @Path("recipe_id") recipeId: Int,
        @Query("page") page: Int
    ): CommentPagingResponse

    @POST("recipe/{recipe_id}/comment")
    suspend fun sendComment(
        @Path("recipe_id") recipeId: Int,
        @Query("text") text: String
    ): CommentResponse

    @PUT("recipe/{recipe_id}/comment/{comment_id}")
    suspend fun editComment(
        @Path("recipe_id") recipeId: Int,
        @Path("comment_id") commentId: Int,
        @Query("text") text: String
    ): CommonResponse

    @DELETE("recipe/{recipe_id}/comment/{comment_id}")
    suspend fun deleteComment(
        @Path("recipe_id") recipeId: Int,
        @Path("comment_id") commentId: Int
    ): CommonResponse

    // Recipe Like Related Operations
    @POST("recipe/{recipe_id}/like")
    suspend fun likeRecipe(
        @Path("recipe_id") recipeId: Int
    ): CommonResponse

    @DELETE("recipe/{recipe_id}/like")
    suspend fun dislikeRecipe(
        @Path("recipe_id") recipeId: Int
    ): CommonResponse

    //    Category and related recipes
    @GET("category-recipes")
    suspend fun getCategoriesWithRecipes(
        @Query("page") page: Int
    ): CategoryPagingResponse

    @GET("category/{category_id}/recipe")
    suspend fun getRecipesByCategory(
        @Path("category_id") categoryId: Int,
        @Query("page") page: Int
    ): RecipePagingResponse

    @Multipart
    @POST("recipe")
    suspend fun sendRecipe(
        @Part("title") title: RequestBody,
        @Part("ingredients") ingredients: RequestBody,
        @Part("directions") directions: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("number_of_person_id") numberOfPersonId: RequestBody,
        @Part("time_of_recipe_id") timeOfRecipeId: RequestBody,
        @Part images: Array<MultipartBody.Part>
    ): RecipeResponse

    @GET("time")
    suspend fun getRecipeTimes(): RecipeTimesResponse

    @GET("serving")
    suspend fun getRecipeServing(): RecipeServingResponse

    @GET("category")
    suspend fun getAllCategories(): CategoryDraftData
}
