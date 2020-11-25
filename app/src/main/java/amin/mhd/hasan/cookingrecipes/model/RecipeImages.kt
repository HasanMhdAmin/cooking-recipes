package amin.mhd.hasan.cookingrecipes.model

import java.io.Serializable

/**
 * Created by Hasan Mhd Amin on 25.11.20
 */
class RecipeImages : Serializable {
    var recipeImages: MutableList<String> = mutableListOf()

    constructor(recipeImages: MutableList<String>) {
        this.recipeImages = recipeImages
    }
}