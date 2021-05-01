package app.siakad.siakadtkadmin.presentation.views.preview

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.utils.decoder.PicassoDecoder
import app.siakad.siakadtkadmin.presentation.utils.decoder.PicassoRegionDecoder
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.squareup.okhttp.OkHttpClient
import com.squareup.picasso.Picasso


class ImagePreviewActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_SOURCE_TYPE = "Image_src_type"
        const val IMAGE_SOURCE = "Image_Uri"

        const val IMG_URL = 1
        const val IMG_URI = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        if (intent.getIntExtra(IMAGE_SOURCE_TYPE, IMG_URI) == IMG_URL) {
            val imageView: SubsamplingScaleImageView = findViewById(R.id.imageView)
            val picasso = Picasso.with(imageView.context)

            imageView.setBitmapDecoderFactory {
                PicassoDecoder(
                    intent.getStringExtra(IMAGE_SOURCE),
                    picasso
                )
            }

            imageView.setRegionDecoderFactory { PicassoRegionDecoder(OkHttpClient()) }

            imageView.setImage(ImageSource.uri(intent.getStringExtra(IMAGE_SOURCE)))
        } else {
            val imageView: SubsamplingScaleImageView = findViewById(R.id.imageView)

            imageView.setImage(ImageSource.uri(intent.getStringExtra(IMAGE_SOURCE)))
        }

        setupAppBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Lihat Gambar"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}