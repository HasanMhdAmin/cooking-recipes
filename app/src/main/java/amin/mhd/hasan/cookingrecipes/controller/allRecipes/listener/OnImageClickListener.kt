package amin.mhd.hasan.cookingrecipes.controller.allRecipes.listener

import android.net.Uri

interface OnImageClickListener {
    fun onItemClickListener(imageUri: Uri, images: ArrayList<String>)
}