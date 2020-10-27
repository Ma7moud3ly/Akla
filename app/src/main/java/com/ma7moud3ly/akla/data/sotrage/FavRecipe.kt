package com.ma7moud3ly.akla.data.sotrage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = TABLE_NAME)
data class FavRecipe(
    @ColumnInfo(name = "recipe")
    @PrimaryKey
    var recipe: String
)

const val DB_NAME = "fav_recipes_db"
const val TABLE_NAME = "fav_recipes"
const val COLUMN1 = "recipe"