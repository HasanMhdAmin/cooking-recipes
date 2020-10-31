package amin.mhd.hasan.cookingrecipes.controller.allRecipes.viewModel

import amin.mhd.hasan.cookingrecipes.model.Recipe
import amin.mhd.hasan.cookingrecipes.utils.LocalStorage
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AllRecipesViewModel(application: Application) : AndroidViewModel(application) {

    var recipes: MutableLiveData<List<Recipe>> = MutableLiveData()

    init {
        getRecipes()
    }

    private fun getRecipes() {
        val recipesList = LocalStorage.getInstance().getRecipes(getApplication())
        recipes.postValue(recipesList)
    }


}