package amin.mhd.hasan.cookingrecipes.controller.allRecipes.viewModel

import amin.mhd.hasan.cookingrecipes.model.Recipe
import amin.mhd.hasan.cookingrecipes.repository.Repository
import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AllRecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    var recipes: MutableLiveData<List<Recipe>> = MutableLiveData()

    init {
        getRecipes()
    }

    private fun getRecipes() {
        viewModelScope.launch {
            val recipesList = repository.getRecipes()
            recipes.postValue(recipesList)
        }
    }

    fun deleteRecipes(recipe: Recipe) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
            getRecipes()
        }
    }

}