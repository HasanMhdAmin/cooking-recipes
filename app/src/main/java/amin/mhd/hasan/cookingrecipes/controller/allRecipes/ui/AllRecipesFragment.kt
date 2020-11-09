package amin.mhd.hasan.cookingrecipes.controller.allRecipes.ui

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.controller.MainActivity2
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.adapter.RecipesAdapter
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.viewModel.AllRecipesViewModel
import amin.mhd.hasan.cookingrecipes.model.Recipe
import amin.mhd.hasan.cookingrecipes.utils.DialogUtils
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.all_recipes_fragment.*
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.listener.OnImageClickListener as OnImageClickListener1

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

            if (it.isNotEmpty()) {
                recipesAdapter = context?.let { ctx -> RecipesAdapter(it, ctx, this) }!!
                allRecipesRecyclerView.adapter = recipesAdapter
                addRecipe.visibility = View.GONE
                allRecipesRecyclerView.visibility = View.VISIBLE
            } else {
                addRecipe.visibility = View.VISIBLE
                allRecipesRecyclerView.visibility = View.GONE
                addRecipe.setOnClickListener { (activity as MainActivity2).setAddRecipeFragment() }
            }
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
                    DialogUtils.showAlertDialog(
                        context,
                        true,
                        null,
                        "Are you sure you want to delete this recipe?",
                        "Delete",
                        DialogInterface.OnClickListener { dialog, which ->
                            viewModel.deleteRecipes(
                                recipe
                            )
                        },
                        "Cancel",
                        null
                    )
                }
                R.id.share -> {
                    activity?.let {
                        ShareCompat.IntentBuilder.from(it)
                            .setType("text/plain")
                            .setChooserTitle(R.string.share)
                            .setText(recipe.title + "\n\n" + recipe.description)
                            .startChooser()
                    }
                }
                else -> {
                }
            }
            true
        }
    }

}