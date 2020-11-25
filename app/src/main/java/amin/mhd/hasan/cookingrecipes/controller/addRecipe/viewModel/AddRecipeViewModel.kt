package amin.mhd.hasan.cookingrecipes.controller.addRecipe.viewModel

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.enums.DisplayMood
import amin.mhd.hasan.cookingrecipes.model.Recipe
import amin.mhd.hasan.cookingrecipes.model.RecipesDatabase
import amin.mhd.hasan.cookingrecipes.utils.ImageUtils
import amin.mhd.hasan.cookingrecipes.utils.LocalStorage
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.*

class AddRecipeViewModel(application: Application) : AndroidViewModel(application) {


    var saved: MutableLiveData<Boolean> = MutableLiveData()
    var goBackConfirmationNeeded: MutableLiveData<Boolean> = MutableLiveData()
    var title: MutableLiveData<String> = MutableLiveData("")
    var description: MutableLiveData<String> = MutableLiveData("")
    var images: MutableLiveData<MutableList<String>> = MutableLiveData()

    lateinit var recipe: Recipe
    private var displayMood: DisplayMood = DisplayMood.ADD

    init {
        images.value = mutableListOf()
    }

    var titleErrorMessage: MutableLiveData<String> = MutableLiveData("")
    var descriptionErrorMessage: MutableLiveData<String> = MutableLiveData("")

    fun onTitleTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (s.isEmpty()) {
            titleErrorMessage.postValue(getApplication<Application>().resources.getString(R.string.title_error_message))
        } else {
            titleErrorMessage.postValue("")
        }
    }

    fun onDescriptionTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (s.isEmpty()) {
            descriptionErrorMessage.postValue(getApplication<Application>().resources.getString(R.string.desc_error_message))
        } else {
            descriptionErrorMessage.postValue("")
        }
    }


    fun saveRecipe() {
        if (isValid()) {
            if (displayMood == DisplayMood.ADD) {
                val recipe = Recipe()
//                recipe.id = UUID.randomUUID().toString()
                recipe.title = title.value!!
                recipe.description = description.value!!
                recipe.images = images.value!!

                var db = RecipesDatabase.getInstance(getApplication());
                db.recipesDao().insertRecipe(recipe)

//                LocalStorage.getInstance().addRecipe(getApplication(), recipe)
                saved.postValue(true)
            } else if (displayMood == DisplayMood.EDIT) {
                recipe.title = title.value!!
                recipe.description = description.value!!
                recipe.images = images.value!!
                LocalStorage.getInstance().editRecipe(getApplication(), recipe)
                saved.postValue(true)
            }
        }
    }

    private fun isValid(): Boolean {
        if (title.value?.isEmpty()!!) {
            titleErrorMessage.postValue(getApplication<Application>().resources.getString(R.string.title_error_message))
        } else {
            titleErrorMessage.postValue("")
        }

        if (description.value?.isEmpty()!!) {
            descriptionErrorMessage.postValue(getApplication<Application>().resources.getString(R.string.desc_error_message))
        } else {
            descriptionErrorMessage.postValue("")
        }

        return title.value?.isNotEmpty()!! && description.value?.isNotEmpty()!!
    }

    private fun addToImages(absolutePath: String) {
        images.value?.add(absolutePath)
        images.postValue(images.value)
    }

    fun removeImage(imageUri: Uri) {
        val itemToRemove = images.value?.find { it == imageUri.path }
        images.value?.remove(itemToRemove)
        images.postValue(images.value)
    }

    fun saveImage(selectedImage: Uri) {
        getApplication<Application>().let {
            val imageName = System.currentTimeMillis().toString() + ".jpg"
            val savedImagePath =
                getApplication<Application>().let { ctx ->
                    ImageUtils.saveImageToAppStorage(
                        ctx,
                        selectedImage,
                        "recipe_images",
                        imageName
                    )
                }
            addToImages(savedImagePath.absolutePath)
        }

    }

    fun bindRecipe(recipe: Recipe) {
        this.recipe = recipe
        displayMood = DisplayMood.EDIT
        title.postValue(recipe.title)
        description.postValue(recipe.description)
        images.postValue(recipe.images.toMutableList())
    }

    fun onBackPressed() {
        if (title.value!!.isEmpty() && description.value!!.isEmpty()) {
            goBackConfirmationNeeded.postValue(false)
        } else {
            goBackConfirmationNeeded.postValue(true)
        }
    }
}