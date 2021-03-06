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
            val directory: File = cw.getDir(folderName, Context.MODE_PRIVATE)
            if (!directory.exists()) {
                directory.mkdir()
            }
            val myPath = File(directory, imageName)

            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(myPath)
                var bitmap: Bitmap
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        selectedImage
                    )
                } else {
                    val source =
                        selectedImage.let {
                            context.contentResolver?.let { ctx ->
                                ImageDecoder.createSource(
                                    ctx,
                                    it
                                )
                            }
                        }
                    bitmap = ImageDecoder.decodeBitmap(source!!)
                }

                bitmap = resize(bitmap, 512, 512)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
                fos.close()
            } catch (e: Exception) {
            }
            return myPath
        }


        private fun resize(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
            var image = image
            return if (maxHeight > 0 && maxWidth > 0) {
                val width = image.width
                val height = image.height
                val ratioBitmap = width.toFloat() / height.toFloat()
                val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
                var finalWidth = maxWidth
                var finalHeight = maxHeight
                if (ratioMax > 1) {
                    finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
                } else {
                    finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
                }
                image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
                image
            } else {
                image
            }
        }


    }
}