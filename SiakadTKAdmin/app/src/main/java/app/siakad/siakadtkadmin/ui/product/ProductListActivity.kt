package app.siakad.siakadtkadmin.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class ProductListActivity : AppCompatActivity() {

    private lateinit var svProduct: SearchView
    private lateinit var tvNumProduct: TextView
    private lateinit var ivAddProduct: ImageView
    private lateinit var rvProduct: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
    }

    private fun setupItemView() {
        svProduct = findViewById(R.id.sv_product_list_cari)
        tvNumProduct = findViewById(R.id.tv_product_list_jumlah_produk)
        ivAddProduct = findViewById(R.id.iv_product_list_tambah_produk)
        rvProduct = findViewById(R.id.rv_product_list_daftar_produk)
    }

    private fun setupView() {}
}