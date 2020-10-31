package amin.mhd.hasan.cookingrecipes.controller

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.controller.addRecipe.ui.AddRecipeFragment
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.ui.AllRecipesFragment
import amin.mhd.hasan.cookingrecipes.controller.gallery.GalleryFragment
import amin.mhd.hasan.cookingrecipes.model.Recipe
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity2_activity)
        if (savedInstanceState == null) {
            setAllRecipesFragment()
        }
    }

    public fun setAllRecipesFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, AllRecipesFragment.newInstance())
            .commit()
    }

    public fun setAddRecipeFragment() {
        supportFragmentManager.beginTransaction()
            .addToBackStack("AddRecipeFragment")
            .replace(R.id.container, AddRecipeFragment.newInstance())
            .commit()
    }

    public fun setEditRecipeFragment(recipe: Recipe) {
        supportFragmentManager.beginTransaction()
            .addToBackStack("EditRecipeFragment")
            .replace(R.id.container, AddRecipeFragment.newInstance(recipe))
            .commit()
    }

    public fun setGalleryFragment(imageUri: Uri, images: ArrayList<String>) {
        supportFragmentManager.beginTransaction()
            .addToBackStack("GalleryFragment")
            .add(R.id.container, GalleryFragment.newInstance(imageUri, images))
            .commit()
    }
}