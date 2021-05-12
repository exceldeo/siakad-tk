package app.siakad.siakadtk.presentation.utils.decoder

import android.content.Context
import android.graphics.*
import android.net.Uri
import com.davemorrissey.labs.subscaleview.decoder.ImageRegionDecoder
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.OkHttpDownloader
import java.io.InputStream


class PicassoRegionDecoder(private val client: OkHttpClient) : ImageRegionDecoder {
    private var decoder: BitmapRegionDecoder? = null
    private val decoderLock = Any()

    override fun isReady(): Boolean {
        return decoder != null && !decoder!!.isRecycled
    }

    override fun init(context: Context?, uri: Uri): Point {
        val downloader = OkHttpDownloader(client)
        val inputStream: InputStream = downloader.load(uri, 0).inputStream
        decoder = BitmapRegionDecoder.newInstance(inputStream, false)
        return Point(decoder!!.width, decoder!!.height)
    }

    override fun decodeRegion(rect: Rect, sampleSize: Int): Bitmap {
        synchronized(decoderLock) {
            val options = BitmapFactory.Options()
            options.inSampleSize = sampleSize
            options.inPreferredConfig = Bitmap.Config.RGB_565
            val bitmap = decoder!!.decodeRegion(rect, options)
            return bitmap
                ?: throw RuntimeException("Region decoder returned null bitmap - image format may not be supported")
        }
    }

    override fun recycle() {
        decoder!!.recycle()
    }

}