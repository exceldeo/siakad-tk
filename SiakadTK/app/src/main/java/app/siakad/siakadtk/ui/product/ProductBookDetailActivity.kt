package app.siakad.siakadtk.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.TextView
import app.siakad.siakadtk.R

class ProductBookDetailActivity : AppCompatActivity() {

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
    }

    private fun setupItemView() {
        tvProductKewajiban = findViewById(R.id.tv_product_bookdetail_kewajiban)
        tvProductPrice = findViewById(R.id.tv_product_bookdetail_harga)
        tvProductOrderDeadline = findViewById(R.id.tv_product_bookdetail_batas_pesan)
        spProductSum = findViewById(R.id.sp_product_bookdetail_jumlah)
        tvProductTotalPayment = findViewById(R.id.tv_product_bookdetail_total_harga)
        btnProductAddToBasket = findViewById(R.id.btn_product_bookdetail_tambah_ke_basket)
    }
}