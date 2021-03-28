package app.siakad.siakadtk.presentation.screens.history.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import app.siakad.siakadtk.R

class HistoryDetailActivity : AppCompatActivity() {
    private val pageTitle = "Detail Riwayat Pembayaran"

    private lateinit var toolbar: Toolbar
    private lateinit var tvDateOrder: TextView
    private lateinit var tvDateAccept: TextView
    private lateinit var tvDatePay: TextView
    private lateinit var tvOrderStatus: TextView
    private lateinit var rvProductOrdered: RecyclerView
    private lateinit var tvPayTotal:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)
        setupView()
        setupItemView()
    }

    private fun setupView() {
        setupAppBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this@HistoryDetailActivity,
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvDateOrder = findViewById(R.id.tv_historydetail_tglorder_date)
        tvDateAccept = findViewById(R.id.tv_historydetail_tglsetuju_date)
        tvDatePay = findViewById(R.id.tv_historydetail_tglbayar_date)
        tvOrderStatus = findViewById(R.id.tv_historydetail_desc_status)
        rvProductOrdered = findViewById(R.id.rv_historydetail_product_ordered_list)
        tvPayTotal  = findViewById(R.id.tv_historydetail_total_harga)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}