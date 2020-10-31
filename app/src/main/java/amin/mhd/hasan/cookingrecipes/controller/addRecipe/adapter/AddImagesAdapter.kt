package amin.mhd.hasan.cookingrecipes.controller.addRecipe.adapter

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.controller.addRecipe.listener.OnRecyclerViewItemClickListener
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView


class AddImagesAdapter(
    private val images: List<String>,
    private val onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TYPE_ITEM) {
            val cell: View =
                LayoutInflater.from(parent.context).inflate(R.layout.image_item_with_delete, null)
            return ImageViewHolder(cell, onRecyclerViewItemClickListener)
        } else if (viewType == TYPE_HEADER) {
            val cell: View =
                LayoutInflater.from(parent.context).inflate(R.layout.add_image_item, null)
            return HeaderViewHolder(cell, onRecyclerViewItemClickListener)
        }

        throw RuntimeException("There is no type that matches the type $viewType  make sure your using types correctly");

    }

    override fun getItemCount(): Int {
        return images.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ImageViewHolder) {
            val item = getItem(position)
            holder.bindViews(Uri.parse(item))
        } else if (holder is HeaderViewHolder) {
            // nothing to do
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) TYPE_HEADER else TYPE_ITEM
    }

    private fun isPositionHeader(position: Int): Boolean {
        return position == 0
    }

    private fun getItem(position: Int): String {
        return images[position - 1]
    }

    ////

    class ImageViewHolder(
        rootView: View,
        private var onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener
    ) : RecyclerView.ViewHolder(rootView) {

        private var imageItem: AppCompatImageView = rootView.findViewById(R.id.imageItem)
        private var deleteIcon: AppCompatImageView = rootView.findViewById(R.id.deleteIcon)


        fun bindViews(uri: Uri) {
            imageItem.setImageURI(uri)
            deleteIcon.setOnClickListener {
                onRecyclerViewItemClickListener.onItemRecyclerViewClickListener(uri)
            }
        }


    }

    class HeaderViewHolder(
        rootView: View,
        private var onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener
    ) : RecyclerView.ViewHolder(rootView) {
        private var addImage: RelativeLayout = rootView.findViewById(R.id.addImage)

        init {
            addImage.setOnClickListener {
                onRecyclerViewItemClickListener.onRecyclerViewHeaderClickListener()
            }
        }

    }

}
