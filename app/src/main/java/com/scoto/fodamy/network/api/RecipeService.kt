package com.scoto.fodamy.network.api

import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.models.responses.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET("editor-choices")
    suspend fun getEditorChoiceRecipes(
        @Query("page") page: Int
    ): RecipeResponse


    @GET("recipe")
    suspend fun getLastAddedRecipes(
        @Query("page") page: Int
    ): RecipeResponse


    @GET("recipe/{recipe_id}")
    suspend fun getRecipeById(
        @Path("recipe_id") recipeId: Int
    ): Response<Recipe>
}