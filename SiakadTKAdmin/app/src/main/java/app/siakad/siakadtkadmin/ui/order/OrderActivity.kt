package app.siakad.siakadtkadmin.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class OrderActivity : AppCompatActivity() {

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
        svOrder = findViewById(R.id.sv_order_cari)
        tvNumOrder = findViewById(R.id.tv_order_jumlah_pesananan)
        rvOrder = findViewById(R.id.rv_order_daftar_pesananan)
    }

    private fun setupView() {}

}