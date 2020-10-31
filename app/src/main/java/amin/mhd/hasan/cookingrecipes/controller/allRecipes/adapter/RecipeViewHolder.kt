package amin.mhd.hasan.cookingrecipes.controller.allRecipes.adapter

import amin.mhd.hasan.cookingrecipes.R
import amin.mhd.hasan.cookingrecipes.controller.allRecipes.listener.OnImageClickListener
import amin.mhd.hasan.cookingrecipes.model.Recipe
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Hasan Mhd Amin on 30.10.20
 */
class RecipeViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

    var title: TextView = rootView.findViewById(R.id.title)
    var desc: TextView = rootView.findViewById(R.id.desc)
    var more: ImageView = rootView.findViewById(R.id.more)

    private var imagesRecyclerView: RecyclerView = rootView.findViewById(R.id.imagesRecyclerView)

    fun bindViews(item: Recipe, context: Context, onImageClickListener: OnImageClickListener) {
        title.text = item.title
        desc.text = item.description

        more.setOnClickListener {
            onImageClickListener.onMoreClickListener(
                recipe = item,
                view = it
            )
        }
        imagesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        imagesRecyclerView.setHasFixedSize(true)

        if (item.images.isEmpty()) {
            imagesRecyclerView.visibility = View.GONE
        } else {
            val imagesAdapter = ImagesAdapter(item.images, onImageClickListener)
            imagesRecyclerView.adapter = imagesAdapter
        }
    }
}