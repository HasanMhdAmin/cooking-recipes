package amin.mhd.hasan.cookingrecipes.database

import amin.mhd.hasan.cookingrecipes.model.RecipeImages
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageUriConverter {

    private val gson = Gson()

    @TypeConverter
    fun storedStringToImages(value: String): RecipeImages {
        val images = gson.fromJson<MutableList<String>>(value, object : TypeToken<MutableList<String>>() {}.type)
        return RecipeImages(images)
    }

    @TypeConverter
    fun imagesToStoredString(cl: RecipeImages): String {
        return gson.toJson(cl.recipeImages)
    }
}
