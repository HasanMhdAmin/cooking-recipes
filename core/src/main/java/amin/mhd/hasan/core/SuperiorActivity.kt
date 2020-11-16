package amin.mhd.hasan.core

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Hasan Mhd Amin on 10.11.20
 */
open class SuperiorActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val fragmentList = supportFragmentManager.fragments
        var handled = false
        for (fragment in fragmentList) {
            if (fragment is SuperiorFragment) {
                handled = fragment.onBackPressed()
                if (handled) {
                    break
                }
            }
        }
        if (!handled) {
            super.onBackPressed()
        }
    }

}