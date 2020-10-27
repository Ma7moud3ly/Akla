package com.ma7moud3ly.akla.data.sotrage

import androidx.room.*

@Dao
interface FavRecipeDao {
    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(row: FavRecipe?): Long

    @Insert
    fun insertAll(rows: List<FavRecipe>): LongArray

    @Delete
    suspend fun delete(col: FavRecipe?): Int

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll(): Int


    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAll(): List<FavRecipe>

    @Update
    fun update(row: FavRecipe?): Int

    @Query("SELECT EXISTS (SELECT 1 FROM $TABLE_NAME WHERE $COLUMN1 = :col)")
    suspend fun exists(col: String): Boolean
}