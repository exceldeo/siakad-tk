package app.siakad.siakadtkadmin.ui.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.ui.announcement.AnnouncementActivity
import app.siakad.siakadtkadmin.ui.announcement.AnnouncementAddActivity
import app.siakad.siakadtkadmin.ui.main.MainActivity
import app.siakad.siakadtkadmin.ui.notification.NotificationActivity

class ProductAddActivity : AppCompatActivity() {

    private val pageTitle = "Tambah Produk"

    private lateinit var toolbar: Toolbar
    private lateinit var btnAddPhoto: RelativeLayout
    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etNum: EditText
    private lateinit var btnAddData: RelativeLayout
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        setupItemView()
        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@ProductAddActivity, ProductListActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        btnAddPhoto = findViewById(R.id.iv_product_add_foto)
        etName = findViewById(R.id.et_product_add_nama)
        etPrice = findViewById(R.id.et_product_add_harga)
        etNum = findViewById(R.id.et_product_add_jumlah)
        btnAddData = findViewById(R.id.iv_product_add_tambah_data)
        btnCancel = findViewById(R.id.btn_product_add_batal)
        btnSave = findViewById(R.id.btn_product_add_simpan)
    }

    private fun setupView() {
        setupAppBar()
    }

    private fun setupAppBar() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_clear_24)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}