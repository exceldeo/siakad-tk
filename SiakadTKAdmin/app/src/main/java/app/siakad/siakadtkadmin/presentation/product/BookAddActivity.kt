package app.siakad.siakadtkadmin.presentation.product

import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.product.dialog.UniformProductDialog

class BookAddActivity : AppCompatActivity() {

    private val pageTitle = "Tambah Produk"

    private lateinit var toolbar: Toolbar
    private lateinit var btnAddPhoto: RelativeLayout
    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etNum: EditText
    private lateinit var btnAddData: RelativeLayout
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    private lateinit var dialogUniform: UniformProductDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)

        setupItemView()
        setupView()
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

        dialogUniform =
            UniformProductDialog()
    }

    private fun setupView() {
        setupAppBar()

        btnAddData.setOnClickListener{
            dialogUniform.show(supportFragmentManager, null)
        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}