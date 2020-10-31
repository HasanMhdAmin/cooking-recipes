package amin.mhd.hasan.cookingrecipes.controller.allRecipes.ui

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.controller.MainActivity2
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.adapter.RecipesAdapter
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.viewModel.AllRecipesViewModel
import amin.mhd.hasan.cookingrecipes.model.Recipe
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.all_recipes_fragment.*
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.listener.OnImageClickListener as OnImageClickListener1


private const val TAG = "AllRecipesFragment"

class AllRecipesFragment : Fragment(), OnImageClickListener1 {

    companion object {
        fun newInstance() =
            AllRecipesFragment()
    }

    private lateinit var viewModel: AllRecipesViewModel
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.all_recipes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // set custom toolbar & remove the default title
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProvider(this).get(AllRecipesViewModel::class.java)

        allRecipesRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.recipes.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Log.d(TAG, "onActivityCreated: list size: ${it.size}")
            recipesAdapter = context?.let { ctx -> RecipesAdapter(it, ctx, this) }!!
            allRecipesRecyclerView.adapter = recipesAdapter
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.all_recipes_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_add_recipe) {
            (activity as MainActivity2).setAddRecipeFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClickListener(imageUri: Uri, images: ArrayList<String>) {
        (activity as MainActivity2).setGalleryFragment(imageUri, images)
    }

    override fun onMoreClickListener(recipe: Recipe, view: View) {
        val popup = PopupMenu(context, view)
        popup.menuInflater.inflate(
            R.menu.recipe_actions,
            popup.menu
        )
        popup.show()
        popup.setOnMenuItemClickListener { mItem ->
            when (mItem.itemId) {
                R.id.edit -> {
                    (activity as MainActivity2).setEditRecipeFragment(recipe)
                }
                R.id.delete -> {
                    Log.d("RecipeViewHolder", "bindViews: delete")
                }
                R.id.share -> {
                    Log.d("RecipeViewHolder", "bindViews: share")
                }
                else -> {
                }
            }
            true
        }
    }

}