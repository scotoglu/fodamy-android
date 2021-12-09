package com.scoto.fodamy.network.api

import com.scoto.fodamy.network.models.Category
import com.scoto.fodamy.network.models.Comment
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.models.responses.BaseResponse
import com.scoto.fodamy.network.models.responses.RecipeResponse
import retrofit2.http.*

interface RecipeService {

    @GET("editor-choices")
    suspend fun getEditorChoiceRecipes(
        @Query("page") page: Int
    ): RecipeResponse<List<Recipe>>


    @GET("recipe")
    suspend fun getLastAddedRecipes(
        @Query("page") page: Int
    ): RecipeResponse<List<Recipe>>


    @GET("recipe/{recipe_id}")
    suspend fun getRecipeById(
        @Path("recipe_id") recipeId: Int
    ): Recipe

    @GET("recipe/{recipe_id}/comment")
    suspend fun getRecipeComments(
        @Path("recipe_id") recipeId: Int,
        @Query("page") page: Int
    ): RecipeResponse<List<Comment>>

    @POST("recipe/{recipe_id}/comment")
    suspend fun sendComment(
        @Path("recipe_id") recipeId: Int,
        @Query("text") text: String
    ): Comment

    @POST("recipe/{recipe_id}/like")
    suspend fun likeRecipe(
        @Path("recipe_id") recipeId: Int
    ): BaseResponse

    @DELETE("recipe/{recipe_id}/like")
    suspend fun dislikeRecipe(
        @Path("recipe_id") recipeId: Int
    ): BaseResponse


    @GET("category-recipes")
    suspend fun getCategoriesWithRecipes(
        @Query("page") page: Int
    ): RecipeResponse<List<Category>>

    @GET("category/{category_id}/recipe")
    suspend fun getRecipesByCategory(
        @Path("category_id") categoryId: Int,
        @Query("page") page: Int
    ): RecipeResponse<List<Recipe>>
}