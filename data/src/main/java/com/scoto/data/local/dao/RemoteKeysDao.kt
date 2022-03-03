package com.scoto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scoto.data.local.local_dto.RemoteKeyComment
import com.scoto.data.local.local_dto.RemoteKeysCategory
import com.scoto.data.local.local_dto.RemoteKeysEditor
import com.scoto.data.local.local_dto.RemoteKeysLast

/**
 * @author Sefa ÇOTOĞLU
 * Created 23.02.2022 at 11:20
 */
@Dao
interface RemoteKeysDao {

    // EditorChoices Recipes Remote Keys
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEditorRemoteKeys(remoteKeys: List<RemoteKeysEditor>)

    @Query("select * from remote_keys_editor where id =:id")
    fun getEditorRemoteKeys(id: Int): RemoteKeysEditor

    @Query("delete from remote_keys_editor")
    fun deleteEditorKeys()

    // Last Added Recipes Remote Keys
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastAddedRemoteKeys(remoteKeys: List<RemoteKeysLast>)

    @Query("select * from remote_keys_last where id =:id")
    fun getLastAddedRemoteKeys(id: Int): RemoteKeysLast

    @Query("delete from remote_keys_last")
    fun deleteLastKeys()

    // Recipe Comments Remote Key
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommentsRemoteKeys(remoteKeys: List<RemoteKeyComment>)

    @Query("select * from remote_key_comments where id =:id")
    fun getCommentsRemoteKey(id: Int): RemoteKeyComment

    @Query("delete from remote_key_comments")
    fun deleteCommentsRemoteKeys()

    // Category Recipes Remote Key
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryRecipesRemoteKeys(remoteKeys: List<RemoteKeysCategory>)

    @Query("select * from remote_keys_category where id =:id")
    fun getCategoryRemoteKeys(id: Int): RemoteKeysCategory

    @Query("delete from remote_keys_category")
    fun deleteCategoryRecipesKeys()
}
