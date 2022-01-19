package com.scoto.data.network.services

import com.scoto.data.network.dto.*
import retrofit2.http.*

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 17:37
 */
interface RecipeService {

    @GET("editor-choices")
    suspend fun getEditorChoiceRecipes(
        @Query("page") page: Int
    ): BaseResponse<List<RecipeResponse>>

    @GET("recipe")
    suspend fun getLastAddedRecipes(
        @Query("page") page: Int
    ): BaseResponse<List<RecipeResponse>>

    @GET("recipe/{recipe_id}")
    suspend fun getRecipeById(
        @Path("recipe_id") recipeId: Int
    ): RecipeResponse

    //   Recipe Comment Related Operations
    @GET("recipe/{recipe_id}/comment")
    suspend fun getRecipeComments(
        @Path("recipe_id") recipeId: Int,
        @Query("page") page: Int
    ): BaseResponse<List<CommentResponse>>

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
    ): BaseResponse<List<CategoryResponse>>

    @GET("category/{category_id}/recipe")
    suspend fun getRecipesByCategory(
        @Path("category_id") categoryId: Int,
        @Query("page") page: Int
    ): BaseResponse<List<RecipeResponse>>
}