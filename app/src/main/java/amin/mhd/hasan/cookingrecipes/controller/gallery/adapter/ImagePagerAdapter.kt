package amin.mhd.hasan.cookingrecipes.controller.gallery.adapter

import amin.mhd.hasan.cookingrecipes.controller.gallery.ImagePreviewFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by Hasan Mhd Amin on 31.10.20
 */
class ImagePagerAdapter(var images: List<String>, fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return ImagePreviewFragment.newInstance(images[position])
    }

    override fun getCount(): Int {
        return images.size
    }
}