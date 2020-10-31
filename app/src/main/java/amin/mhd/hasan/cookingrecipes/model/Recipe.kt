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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}