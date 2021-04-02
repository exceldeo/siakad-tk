package app.siakad.siakadtk.presentation.screens.main.product.detail.uniform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtk.R

class ProductUniformDetailActivity : AppCompatActivity() {
    private val pageTitle = "Produk Buku"

    private lateinit var toolbar: Toolbar
    private lateinit var tvProductKewajiban: TextView
    private lateinit var tvProductOrderDeadline: TextView
    private lateinit var spProductJenisKelamin: Spinner
    private lateinit var tvProductTopPrice: TextView
    private lateinit var spProductTopSize: Spinner
    private lateinit var spProductTopSum: Spinner
    private lateinit var tvProductBottomPrice: TextView
    private lateinit var spProductBottomSize: Spinner
    private lateinit var spProductBottomSum: Spinner
    private lateinit var tvProductTotalPayment: TextView
    private lateinit var btnProductAddToBasket: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_uniform_detail)
        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvProductKewajiban = findViewById(R.id.tv_product_unidetail_kewajiban)
        tvProductOrderDeadline = findViewById(R.id.tv_product_unidetail_batas_pesan)
        spProductJenisKelamin = findViewById(R.id.sp_product_unidetail_jeniskelamin)
        tvProductTopPrice = findViewById(R.id.tv_product_unidetail_price_atasan)
        spProductTopSize = findViewById(R.id.sp_product_unidetail_ukuran_atasan)
        spProductTopSum = findViewById(R.id.sp_product_unidetail_jumlah_atasan)
        tvProductBottomPrice = findViewById(R.id.tv_product_unidetail_price_bawahan)
        spProductBottomSize = findViewById(R.id.sp_product_unidetail_ukuran_bawahan)
        spProductBottomSum = findViewById(R.id.sp_product_unidetail_jumlah_bawahan)
        tvProductTotalPayment = findViewById(R.id.tv_product_unidetail_total_harga)
        btnProductAddToBasket = findViewById(R.id.btn_product_unidetail_tambah_ke_basket)
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