package amin.mhd.hasan.cookingrecipes.controller.allRecipes.viewModel

import amin.mhd.hasan.cookingrecipes.database.RecipesDatabase
import amin.mhd.hasan.cookingrecipes.model.Recipe
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AllRecipesViewModel(application: Application) : AndroidViewModel(application) {

    var recipes: MutableLiveData<List<Recipe>> = MutableLiveData()

    init {
        getRecipes()
    }

    private fun getRecipes() {
        viewModelScope.launch {
            var db = RecipesDatabase.getInstance(getApplication());
            val recipesList = db.recipesDao().getRecipes()
            recipes.postValue(recipesList)
        }
    }

    fun deleteRecipes(recipe: Recipe) {
        viewModelScope.launch {
            var db = RecipesDatabase.getInstance(getApplication());
            db.recipesDao().deleteRecipe(recipe)
            getRecipes()
        }
    }

}