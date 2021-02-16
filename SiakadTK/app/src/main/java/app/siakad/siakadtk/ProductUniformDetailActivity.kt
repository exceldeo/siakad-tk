package app.siakad.siakadtk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.TextView

class ProductUniformDetailActivity : AppCompatActivity() {

    private lateinit var tvProductKewajiban: TextView
    private lateinit var tvProductOrderDeadline: TextView
    private lateinit var tvProductJenisKelamin: TextView
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
    }

    private fun setupItemView() {
        tvProductKewajiban
        tvProductOrderDeadline = findViewById(R.id.tv_product_unidetail_batas_pesan)
        tvProductJenisKelamin = findViewById(R.id.sp_product_unidetail_jeniskelamin)
        tvProductTopPrice = findViewById(R.id.tv_product_unidetail_price_atasan)
        spProductTopSize = findViewById(R.id.sp_product_unidetail_ukuran_atasan)
        spProductTopSum = findViewById(R.id.sp_product_unidetail_jumlah_atasan)
        tvProductBottomPrice = findViewById(R.id.tv_product_unidetail_price_bawahan)
        spProductBottomSize = findViewById(R.id.sp_product_unidetail_ukuran_bawahan)
        spProductBottomSum = findViewById(R.id.sp_product_unidetail_jumlah_bawahan)
        tvProductTotalPayment = findViewById(R.id.tv_product_unidetail_total_harga)
        btnProductAddToBasket = findViewById(R.id.btn_product_unidetail_tambah_ke_basket)
    }
}