package amin.mhd.hasan.cookingrecipes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Hasan Mhd Amin on 29.10.20
 */
@Entity(tableName = "recipes")
class Recipe : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    lateinit var title: String
    lateinit var description: String
    var images: RecipeImages = RecipeImages(mutableListOf())
}