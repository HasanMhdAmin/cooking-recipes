package amin.mhd.hasan.cookingrecipes.controller.allRecipes.adapter

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.model.Recipe
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecipesAdapter(private val recipes: List<Recipe>, private val context: Context) :
    RecyclerView.Adapter<RecipeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val cell: View = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, null)
        return RecipeViewHolder(cell)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = recipes[position]
        holder.bindViews(item, context)
    }
}
