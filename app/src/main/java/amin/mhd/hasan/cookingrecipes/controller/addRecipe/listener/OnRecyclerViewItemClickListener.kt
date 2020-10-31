package amin.mhd.hasan.cookingrecipes.controller.addRecipe.listener

import android.net.Uri

interface OnRecyclerViewItemClickListener {
    fun onItemRecyclerViewClickListener(imageUri: Uri)
    fun onRecyclerViewHeaderClickListener()
}