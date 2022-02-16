package com.scoto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scoto.data.local.local_dto.CategoryDb
import com.scoto.data.local.local_dto.CommentDb
import com.scoto.data.local.local_dto.RecipeDb

/**
 * @author Sefa ÇOTOĞLU
 * Created 10.02.2022 at 13:49
 */
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(category: List<CategoryDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentDb>)

    @Query("select * from recipes where id =:recipeId")
    suspend fun getRecipeDetails(recipeId: Int): RecipeDb

    /*
    * Boolean values stored as 0(false) and 1(true)
    * Last added and editor choice recipes stores in one table.
    * if recipe is editor choice then field value is 1 else 0
    * */
    // TODO("")
    @Query("select * from recipes where is_editor_choice =:recipeType")
    suspend fun getRecipes(recipeType: Int): List<RecipeDb>

    @Query("select * from categories")
    suspend fun getCategories(): List<CategoryDb>

    @Query("select * from categories where id =:categoryId")
    suspend fun getRecipesByCategory(categoryId: Int): CategoryDb

    @Query("select * from comments where recipe_id =:recipeId")
    suspend fun getRecipeComments(recipeId: Int): List<CommentDb>


}
