/**
 * recipe app (AKLA أكلة)
 * @author  Mahmoud Aly
 * @version 1.0
 * @since   2020-10-25
 */
package com.ma7moud3ly.akla.data

import android.content.Context
import com.ma7moud3ly.akla.data.sotrage.FavRecipe
import com.ma7moud3ly.akla.data.sotrage.FavRecipeDao
import com.ma7moud3ly.akla.data.sotrage.FavRoomDatabase

//handles the stored recipes in the database
class FavRepository(context: Context) {
    private val dao: FavRecipeDao?
    suspend fun insert(row: FavRecipe) {
        dao?.insert(row)
    }

    suspend fun delete(col: FavRecipe?) {
        dao?.delete(col)
    }

    suspend fun getAll(): List<FavRecipe>? {
        return dao?.getAll()
    }

    suspend fun exists(str: String): Boolean {
        if (dao != null) {
            return dao.exists(str)
        }
        return false
    }


    init {
        val db = FavRoomDatabase.getDatabase(context)
        dao = db?.dao()
    }
}
