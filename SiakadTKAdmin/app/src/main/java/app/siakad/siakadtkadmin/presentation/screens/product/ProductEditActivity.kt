package app.siakad.siakadtkadmin.presentation.screens.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
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
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}