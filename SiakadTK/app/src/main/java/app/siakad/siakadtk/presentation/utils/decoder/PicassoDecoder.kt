package app.siakad.siakadtk.presentation.utils.decoder

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.davemorrissey.labs.subscaleview.decoder.ImageDecoder
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


/**
 * Created by gokhanbarisaker on 8/30/15.
 */
class PicassoDecoder(private val tag: String, private val picasso: Picasso) : ImageDecoder {

    override fun decode(context: Context?, uri: Uri): Bitmap {
        return picasso
            .load(uri)
            .tag(tag)
            .config(Bitmap.Config.RGB_565)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .get()
    }
}