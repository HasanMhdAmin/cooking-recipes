package amin.mhd.hasan.cookingrecipes.utils

import amin.mhd.hasan.cookingrecipes.model.Recipe
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Hasan Mhd Amin on 30.10.20
 */

private const val RECIPES_LIST = "recipes_list"
private const val sharedPreferencesKey = "CookingRecipes_PREFS"

class LocalStorage {

    private val gson = Gson()

    companion object {

        private var localStorage: LocalStorage? = null
        fun getInstance(): LocalStorage {
            return if (localStorage == null)
                LocalStorage()
            else
                localStorage as LocalStorage
        }
    }

    fun getRecipes(context: Context): MutableList<Recipe> {
        return try {
            val sharedPreferences = context.getSharedPreferences(
                sharedPreferencesKey, Context.MODE_PRIVATE
            )
            val defValue: String = gson.toJson(emptyList<Recipe>().toMutableList())
            gson.fromJson(
                sharedPreferences.getString(RECIPES_LIST, defValue),
                object : TypeToken<List<Recipe>>() {}.type
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<Recipe>().toMutableList()
        }
    }

    fun addRecipe(
        context: Context,
        recipe: Recipe
    ) {
        // get recipes and add the passed recipe to them
        val recipes = getRecipes(context)
        recipes.add(recipe)

        val sharedPreferences = context.getSharedPreferences(
            sharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(
            RECIPES_LIST,
            gson.toJson(recipes)
        )
        editor.apply()
    }

    private fun overwriteRecipes(
        context: Context,
        recipes: MutableList<Recipe>
    ) {
        val sharedPreferences = context.getSharedPreferences(
            sharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(
            RECIPES_LIST,
            gson.toJson(recipes)
        )
        editor.apply()
    }

    fun editRecipe(context: Context, recipe: Recipe) {
        val recipes = getRecipes(context)
        for (r in recipes) {
            if (r.id == recipe.id) {
                r.title = recipe.title
                r.description = recipe.description
                r.images = recipe.images
            }
        }
        overwriteRecipes(context, recipes)
    }

    fun deleteRecipe(context: Context, recipe: Recipe) {
        val recipes = getRecipes(context)
        recipes.remove(recipes.find { r -> r.id == recipe.id })
        overwriteRecipes(context, recipes)
    }

}