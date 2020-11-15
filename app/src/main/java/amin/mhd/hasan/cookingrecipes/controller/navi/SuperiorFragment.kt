package amin.mhd.hasan.cookingrecipes.controller.navi

import androidx.fragment.app.Fragment

/**
 * Created by Hasan Mhd Amin on 10.11.20
 */
open class SuperiorFragment : Fragment(), OnBackPressed {
    /**
     * Could handle back press.
     * @return true if back press was handled
     */
    override fun onBackPressed(): Boolean {
        return false
    }
}