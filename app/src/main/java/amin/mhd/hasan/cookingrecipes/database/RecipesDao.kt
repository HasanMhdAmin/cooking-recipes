package amin.mhd.hasan.cookingrecipes.database

import amin.mhd.hasan.cookingrecipes.model.Recipe
import androidx.room.*

/**
 * Created by Hasan Mhd Amin on 23.11.20
 */
@Dao
interface RecipesDao {
    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("select * from recipes")
    suspend fun getRecipes(): MutableList<Recipe>
}