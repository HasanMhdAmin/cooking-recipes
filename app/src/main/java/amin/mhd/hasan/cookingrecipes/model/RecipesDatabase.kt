package amin.mhd.hasan.cookingrecipes.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Hasan Mhd Amin on 23.11.20
 */
@Database(entities = [Recipe::class], version = 1)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

    companion object {

        private var instance: RecipesDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RecipesDatabase {
            if (instance == null)
                instance =
                    Room.databaseBuilder(context.applicationContext, RecipesDatabase::class.java, "recipes_database")
                        .fallbackToDestructiveMigration()
                        .build()
            return instance as RecipesDatabase
        }
    }

}