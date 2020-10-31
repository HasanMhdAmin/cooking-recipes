package amin.mhd.hasan.cookingrecipes.controller.allRecipes.adapter

import amin.mhd.hasan.cookingrecipes.R
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView

class ImagesAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val cell: View = LayoutInflater.from(parent.context).inflate(R.layout.image_item, null)
        return ImageViewHolder(
            cell
        )
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = images[position]
        holder.bindViews(Uri.parse(item))

    }


    class ImageViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        var imageItem: AppCompatImageView = rootView.findViewById(R.id.imageItem)

        fun bindViews(uri: Uri) {
            imageItem.setImageURI(uri)
        }


    }

}
