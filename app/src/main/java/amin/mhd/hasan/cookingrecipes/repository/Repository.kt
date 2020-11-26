package amin.mhd.hasan.cookingrecipes.repository

import amin.mhd.hasan.cookingrecipes.database.RecipesDao
import amin.mhd.hasan.cookingrecipes.model.Recipe
import javax.inject.Inject

/**
 * Created by Hasan Mhd Amin on 26.11.20
 */
class Repository @Inject constructor(var recipesDao: RecipesDao) {

    public suspend fun insertRecipe(recipe: Recipe) {
        recipesDao.insertRecipe(recipe)
    }

    public suspend fun updateRecipe(recipe: Recipe) {
        recipesDao.updateRecipe(recipe)
    }

    public suspend fun deleteRecipe(recipe: Recipe) {
        recipesDao.deleteRecipe(recipe)
    }

    public suspend fun getRecipes(): MutableList<Recipe> {
        return recipesDao.getRecipes()
    }
}