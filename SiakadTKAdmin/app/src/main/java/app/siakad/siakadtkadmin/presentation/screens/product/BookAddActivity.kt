package app.siakad.siakadtkadmin.presentation.screens.product

import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.product.dialog.UniformProductDialog
import com.google.android.material.button.MaterialButton

class BookAddActivity : AppCompatActivity() {

    private val pageTitle = "Tambah Produk"

    private lateinit var btnAddPhoto: RelativeLayout
    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etNum: EditText
    private lateinit var btnCancel: MaterialButton
    private lateinit var btnSave: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)

        etName = findViewById(R.id.et_book_add_nama)
        etPrice = findViewById(R.id.et_book_add_harga)
        etNum = findViewById(R.id.et_book_add_jumlah)

        setupAppBar()
        setupButtons()
    }

    private fun setupButtons() {
        btnAddPhoto = findViewById(R.id.iv_book_add_foto)
        btnCancel = findViewById(R.id.btn_book_add_batal)
        btnSave = findViewById(R.id.btn_book_add_simpan)
    }

    private fun setupAppBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}