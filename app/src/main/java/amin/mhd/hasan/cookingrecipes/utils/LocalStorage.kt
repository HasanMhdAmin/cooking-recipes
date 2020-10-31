package amin.mhd.hasan.cookingrecipes.utils

import amin.mhd.hasan.cookingrecipes.model.Recipe
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Hasan Mhd Amin on 30.10.20
 */
class LocalStorage {
    private val TAG = "LocalStorage"
    private val sharedPreferencesKey = "CookingRecipes_PREFS"
    private val RECIPES_LIST = "recipes_list"
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
        Log.d(TAG, "addRecipe: recipes size before add: ${recipes.size}")
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
}