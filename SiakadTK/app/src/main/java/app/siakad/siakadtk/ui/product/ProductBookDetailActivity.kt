package app.siakad.siakadtk.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtk.R

class ProductBookDetailActivity : AppCompatActivity() {
    private val pageTitle = "Produk Buku"

    private lateinit var toolbar: Toolbar
    private lateinit var tvProductKewajiban: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductOrderDeadline: TextView
    private lateinit var spProductSum: Spinner
    private lateinit var tvProductTotalPayment: TextView
    private lateinit var btnProductAddToBasket: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_book_detail)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvProductKewajiban = findViewById(R.id.tv_product_bookdetail_kewajiban)
        tvProductPrice = findViewById(R.id.tv_product_bookdetail_harga)
        tvProductOrderDeadline = findViewById(R.id.tv_product_bookdetail_batas_pesan)
        spProductSum = findViewById(R.id.sp_product_bookdetail_jumlah)
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
}