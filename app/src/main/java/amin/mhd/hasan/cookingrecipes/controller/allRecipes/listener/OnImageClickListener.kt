package amin.mhd.hasan.cookingrecipes.controller.allRecipes.listener

import amin.mhd.hasan.cookingrecipes.model.Recipe
import android.net.Uri
import android.view.View

interface OnImageClickListener {
    fun onItemClickListener(imageUri: Uri, images: ArrayList<String>)
    fun onMoreClickListener(recipe: Recipe, view: View)
}