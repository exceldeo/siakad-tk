package app.siakad.siakadtkadmin.presentation.views.preview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.product.ProductListActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import kotlinx.android.synthetic.main.activity_image_preview.*

class ImagePreviewActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_SOURCE = "Image_Uri"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)
        val imageView: SubsamplingScaleImageView = findViewById(R.id.imageView);

        imageView.setImage(ImageSource.uri(intent.getStringExtra(IMAGE_SOURCE)));

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