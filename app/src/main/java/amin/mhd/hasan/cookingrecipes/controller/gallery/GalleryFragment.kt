package amin.mhd.hasan.cookingrecipes.controller.gallery

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.controller.gallery.adapter.ImagePagerAdapter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.fragment_gallery.*

private const val IMAGE_URI = "image_uri"
private const val IMAGES = "images"

class GalleryFragment : Fragment() {
    private lateinit var images: List<String>
    private lateinit var selectedImageUri: String
    private lateinit var imagePagerAdapter: ImagePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedImageUri = it.getString(IMAGE_URI)!!
            images = it.getStringArrayList(IMAGES)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagePagerAdapter = ImagePagerAdapter(
            images,
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPager.adapter = imagePagerAdapter
        val selectedIndex = findSelectedImagePosition(images, selectedImageUri)
        viewPager.currentItem = selectedIndex
    }

    private fun findSelectedImagePosition(images: List<String>, selectedImageUri: String): Int {
        for (i in images.indices) {
            if (images[i] == selectedImageUri)
                return i
        }
        throw Exception("Image not found")
    }

    companion object {
        fun newInstance(imageUri: Uri, images: ArrayList<String>) =
            GalleryFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_URI, imageUri.path)
                    putStringArrayList(IMAGES, images)
                }
            }
    }
}