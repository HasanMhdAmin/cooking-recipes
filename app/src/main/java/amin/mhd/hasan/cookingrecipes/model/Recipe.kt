package amin.mhd.hasan.cookingrecipes.model

import java.io.Serializable

/**
 * Created by Hasan Mhd Amin on 29.10.20
 */
class Recipe : Serializable {
    lateinit var id: String
    lateinit var title: String
    lateinit var description: String
    var images: List<String> = emptyList()
}