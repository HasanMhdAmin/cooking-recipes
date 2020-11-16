package amin.mhd.hasan.cookingrecipes.controller.addRecipe.ui

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.controller.MainActivity2
import amin.mhd.hasan.cookingrecipes.controller.addRecipe.adapter.AddImagesAdapter
import amin.mhd.hasan.cookingrecipes.controller.addRecipe.listener.OnRecyclerViewItemClickListener
import amin.mhd.hasan.cookingrecipes.controller.addRecipe.viewModel.AddRecipeViewModel
import amin.mhd.hasan.cookingrecipes.databinding.AddRecipeFragmentBinding
import amin.mhd.hasan.cookingrecipes.model.Recipe
import amin.mhd.hasan.cookingrecipes.utils.DialogUtils
import amin.mhd.hasan.core.SuperiorFragment
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.add_recipe_fragment.*

private const val RECIPE = "recipe"

/**
 * This fragment will handle the logic of create new recipe
 * and edit an already added recipe.
 */
class AddRecipeFragment : SuperiorFragment(), OnRecyclerViewItemClickListener {
    private val GALLERY_ACTIVITY_RESULT = 1
    lateinit var binding: AddRecipeFragmentBinding

    private var images = mutableListOf<String>()
    private var recipe: Recipe? = null
    private lateinit var imagesAdapter: AddImagesAdapter

    companion object {
        fun newInstance() = AddRecipeFragment()
        fun newInstance(recipe: Recipe): AddRecipeFragment {
            return AddRecipeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(RECIPE, recipe)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipe = it.getSerializable(RECIPE) as Recipe?
        }
    }

    private lateinit var viewModel: AddRecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.add_recipe_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // set custom toolbar & remove the default title
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(AddRecipeViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        imagesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        imagesAdapter = AddImagesAdapter(images, this)
        imagesRecyclerView.adapter = imagesAdapter

        // check if the screen is in edit mode
        if (recipe != null) {
            viewModel.bindRecipe(recipe!!)
        }

        viewModel.titleErrorMessage.observe(viewLifecycleOwner, Observer {
            title.error = it
        })

        viewModel.descriptionErrorMessage.observe(viewLifecycleOwner, Observer {
            description.error = it
        })

        viewModel.images.observe(viewLifecycleOwner, Observer {
            images.clear()
            images.addAll(it)
            imagesAdapter.notifyDataSetChanged()
        })

        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, getString(R.string.saved), Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
                (activity as MainActivity2).setAllRecipesFragment()
            }
        })

        viewModel.goBackConfirmationNeeded.observe(viewLifecycleOwner, Observer {
            goBack(it)
        })

    }

    override fun onBackPressed(): Boolean {
        viewModel.onBackPressed()
        return true
    }

    private fun goBack(isGoBackConfirmationNeeded: Boolean?) {
        if (isGoBackConfirmationNeeded == null)
            return
        if (isGoBackConfirmationNeeded) {
            DialogUtils.showAlertDialog(
                context,
                true,
                null,
                getString(R.string.exit_confirmation),
                getString(R.string.exit),
                DialogInterface.OnClickListener { dialog, which ->
                    activity?.supportFragmentManager?.popBackStack()
                },
                getString(R.string.keep),
                null
            )
        } else {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onItemRecyclerViewClickListener(imageUri: Uri) {
        viewModel.removeImage(imageUri)
    }

    override fun onRecyclerViewHeaderClickListener() {
        openSystemGallery()
    }

    private fun openSystemGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        if (activity?.packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivityForResult(intent, GALLERY_ACTIVITY_RESULT)
        } else {
            Toast.makeText(
                context,
                "gallery_not_found",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_ACTIVITY_RESULT) {
            if (intent!!.clipData != null) {
                val count = intent.clipData!!.itemCount
                for (i in 0 until count) {
                    var imageUri = intent.clipData!!.getItemAt(i).uri
                    viewModel.saveImage(imageUri)
                }
            } else if (intent.data != null) {
                val selectedImage = intent.data
                selectedImage?.let { viewModel.saveImage(it) }
            }
        } else {
            Toast.makeText(
                context,
                getString(R.string.no_picture_selected),
                Toast.LENGTH_LONG
            ).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_recipe_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_save) {
            viewModel.saveRecipe()
        } else if (id == android.R.id.home) {
            viewModel.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}