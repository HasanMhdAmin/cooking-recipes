package amin.mhd.hasan.cookingrecipes.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Created by Hasan Mhd Amin on 23.11.20
 */
@Dao
interface RecipesDao {
    @Insert
    fun insertRecipe(recipe: Recipe)

    @Query("select * from recipes")
    fun getRecipes() :MutableList<Recipe>
}