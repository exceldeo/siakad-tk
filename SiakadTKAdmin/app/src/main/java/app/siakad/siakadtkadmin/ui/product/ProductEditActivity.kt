package app.siakad.siakadtkadmin.ui.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class ProductEditActivity : AppCompatActivity() {

    private val pageTitle = "Edit Produk"

    private lateinit var toolbar: Toolbar
    private lateinit var btnEditPhoto: CardView
    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etNum: EditText
    private lateinit var rvListData: RecyclerView
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_edit)

        setupItemView()
        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this@ProductEditActivity, ProductListActivity::class.java)
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
        btnEditPhoto = findViewById(R.id.btn_product_edit_ganti_foto)
        etName = findViewById(R.id.et_product_edit_nama)
        etPrice = findViewById(R.id.et_product_edit_harga)
        etNum = findViewById(R.id.et_product_edit_jumlah)
        rvListData = findViewById(R.id.rv_product_add_list_addit_data)
        btnCancel = findViewById(R.id.btn_product_edit_batal)
        btnSave = findViewById(R.id.btn_product_edit_simpan)
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