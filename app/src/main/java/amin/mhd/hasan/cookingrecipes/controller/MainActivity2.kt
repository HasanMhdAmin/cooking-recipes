package amin.mhd.hasan.cookingrecipes.controller

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.controller.addRecipe.ui.AddRecipeFragment
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.ui.AllRecipesFragment
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
//            .addToBackStack("AllRecipesFragment")
//            .setCustomAnimations(R.anim.no_anim, R.anim.no_anim)
            .replace(R.id.container, AllRecipesFragment.newInstance())
            .commit()
    }

    public fun setAddRecipeFragment() {

        supportFragmentManager.beginTransaction()
//            .setCustomAnimations(
//                R.anim.right_in,
//                0,
//                0,
//                R.anim.right_out
//            )
            .addToBackStack("AddRecipeFragment")
            .replace(R.id.container, AddRecipeFragment.newInstance())
            .commit()
    }
}