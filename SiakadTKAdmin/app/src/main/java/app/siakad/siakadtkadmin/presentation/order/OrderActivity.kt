package app.siakad.siakadtkadmin.presentation.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class OrderActivity : AppCompatActivity() {

    private val pageTitle = "Pesanan"

    private lateinit var toolbar: Toolbar
    private lateinit var svOrder: SearchView
    private lateinit var tvNumOrder: TextView
    private lateinit var rvOrder: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        setupItemView()
        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        svOrder = findViewById(R.id.sv_order_cari)
        tvNumOrder = findViewById(R.id.tv_order_jumlah_pesananan)
        rvOrder = findViewById(R.id.rv_order_daftar_pesananan)
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