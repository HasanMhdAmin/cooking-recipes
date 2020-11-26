package amin.mhd.hasan.cookingrecipes.database

import amin.mhd.hasan.cookingrecipes.model.Recipe
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by Hasan Mhd Amin on 23.11.20
 */
@Database(entities = [Recipe::class], version = 1)
@TypeConverters(ImageUriConverter::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}