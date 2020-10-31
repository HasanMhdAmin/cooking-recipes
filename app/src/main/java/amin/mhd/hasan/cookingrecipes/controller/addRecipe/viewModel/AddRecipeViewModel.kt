package amin.mhd.hasan.cookingrecipes.controller.addRecipe.viewModel

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.model.Recipe
import amin.mhd.hasan.cookingrecipes.utils.ImageUtils
import amin.mhd.hasan.cookingrecipes.utils.LocalStorage
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AddRecipeViewModel(application: Application) : AndroidViewModel(application) {


    var saved: MutableLiveData<Boolean> = MutableLiveData()
    var title: MutableLiveData<String> = MutableLiveData("")
    var description: MutableLiveData<String> = MutableLiveData("")
    var images: MutableLiveData<MutableList<String>> = MutableLiveData()

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
            val recipe = Recipe()
            recipe.title = title.value!!
            recipe.description = description.value!!
            recipe.images = images.value!!
            LocalStorage.getInstance().addRecipe(getApplication(), recipe)
            saved.postValue(true)
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

    fun addToImages(absolutePath: String) {
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
}