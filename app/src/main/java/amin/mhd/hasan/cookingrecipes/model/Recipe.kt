package amin.mhd.hasan.cookingrecipes.model

/**
 * Created by Hasan Mhd Amin on 29.10.20
 */
class Recipe {
    lateinit var title: String
    lateinit var description: String
    var images: List<String> = emptyList()
}