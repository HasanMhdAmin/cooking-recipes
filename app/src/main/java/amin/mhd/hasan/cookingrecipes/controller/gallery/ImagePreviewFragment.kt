package amin.mhd.hasan.cookingrecipes.controller.gallery

import amin.mhd.hasan.cookingrecipes.R
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_image_preview.*

private const val IMAGE_URI = "image_uri"

class ImagePreviewFragment : Fragment() {
    private var imageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUri = it.getString(IMAGE_URI)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image.setImageURI(Uri.parse(imageUri))
    }

    companion object {
        fun newInstance(imageUri: String) =
            ImagePreviewFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_URI, imageUri)
                }
            }
    }
}