package com.scoto.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.scoto.data.local.local_dto.CategoryDb
import com.scoto.data.local.local_dto.CommentDb
import com.scoto.data.local.local_dto.RecipeDb
import com.scoto.data.local.local_dto.RecipeDraftDb

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

    @Query("select * from recipes where id =:recipeId ")
    suspend fun getRecipeDetails(recipeId: Int): RecipeDb

    @Query("select * from recipes where is_editor_choice = 1 order by id desc")
    fun getEditorChoicesPaging(): PagingSource<Int, RecipeDb>

    @Query("select * from recipes where is_last_added = 1 order by id desc")
    fun getLastAddedPaging(): PagingSource<Int, RecipeDb>

    @Query("select * from recipes where is_editor_choice =1")
    suspend fun getEditorChoices(): List<RecipeDb>

    @Query("select * from recipes where is_last_added=1")
    suspend fun getLastAdded(): List<RecipeDb>

    @Query("select * from categories")
    suspend fun getCategories(): List<CategoryDb>

    @Query("select * from categories where id =:categoryId")
    suspend fun getRecipesByCategory(categoryId: Int): CategoryDb

    @Query("select * from categories")
    fun getCategoriesPaging(): PagingSource<Int, CategoryDb>

    @Query("select * from comments where recipe_id =:recipeId order by id desc")
    fun getRecipeCommentsPaging(recipeId: Int): PagingSource<Int, CommentDb>

    @Query("select * from comments where recipe_id =:recipeId")
    suspend fun getRecipeComments(recipeId: Int): List<CommentDb>

    @Query("delete from comments where id =:commentId")
    suspend fun deleteComment(commentId: Int)

    @Query("select * from comments where recipe_id =:recipeId order by id desc limit 1")
    suspend fun getFirstRecipeComments(recipeId: Int): CommentDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(drafts: RecipeDraftDb)

    @Query("select * from recipe_drafts")
    suspend fun getAllDrafts(): List<RecipeDraftDb>

    @Query("delete from recipe_drafts where id =:draftId")
    suspend fun deleteDraft(draftId: Int)

    @Update
    suspend fun updateDraft(draft: RecipeDraftDb)
}
