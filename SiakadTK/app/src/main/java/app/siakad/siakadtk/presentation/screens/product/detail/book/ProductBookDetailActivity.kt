package app.siakad.siakadtk.presentation.screens.main.product.detail.book

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.product.Buku

//NEED TO BE FIXED
class ProductBookDetailActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val pageTitle = "Produk Buku"

    private lateinit var toolbar: Toolbar
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductOrderDeadline: TextView
    private lateinit var etProductSum: EditText
    private lateinit var tvProductTotalPayment: TextView
    private lateinit var btnProductAddToBasket: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_book_detail)
        setupItemView()
        setupView()

        val data = intent.getParcelableExtra<Parcelable>("buku") as Buku
        if (data != null) {
            tvProductName.text = data.namaProduk
            tvProductPrice.text = data.harga.toString()
            etProductSum.setText(data.jumlah.toString())
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvProductName = findViewById(R.id.tv_product_bookdetail_nama)
        tvProductPrice = findViewById(R.id.tv_product_bookdetail_harga)
        tvProductOrderDeadline = findViewById(R.id.tv_product_bookdetail_batas_pesan)
        etProductSum = findViewById(R.id.et_product_bookdetail_jumlah)
        tvProductTotalPayment = findViewById(R.id.tv_product_bookdetail_total_harga)
        btnProductAddToBasket = findViewById(R.id.btn_product_bookdetail_tambah_ke_basket)
    }

    private fun setupView() {
        setupAppBar()
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun validateInput(): Boolean {
        return true
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            Toast.makeText(this,
                "OnItemSelectedListener : " + p0.selectedItem.toString(),
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}