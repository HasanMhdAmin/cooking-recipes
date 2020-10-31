package amin.mhd.hasan.cookingrecipes.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Hasan Mhd Amin on 30.10.20
 */
class ImageUtils {
    companion object {

        fun saveImageToAppStorage(
            context: Context,
            selectedImage: Uri,
            folderName: String,
            imageName: String
        ): File {
            val cw = ContextWrapper(context)
            val directory: File = cw.getDir("recipe_images", Context.MODE_PRIVATE)
            if (!directory.exists()) {
                directory.mkdir()
            }


            val mypath = File(directory, imageName)

            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(mypath)
                val bitmap: Bitmap
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        context?.contentResolver,
                        selectedImage
                    )
                } else {
                    val source =
                        selectedImage?.let {
                            context?.contentResolver?.let { ctx ->
                                ImageDecoder.createSource(
                                    ctx,
                                    it
                                )
                            }
                        }
                    bitmap = ImageDecoder.decodeBitmap(source!!)
                }

                bitmap.compress(Bitmap.CompressFormat.PNG, 5, fos)
                fos.close()
            } catch (e: Exception) {
                Log.e("SAVE_IMAGE", e.message, e)
            }
            return mypath
        }

    }
}