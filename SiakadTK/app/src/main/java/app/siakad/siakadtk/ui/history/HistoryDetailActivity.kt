package app.siakad.siakadtk.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R

class HistoryDetailActivity : AppCompatActivity() {
    private lateinit var tvDateOrder: TextView
    private lateinit var tvDateAccept: TextView
    private lateinit var tvDatePay: TextView
    private lateinit var tvOrderStatus: TextView
    private lateinit var rvProductOrdered: RecyclerView
    private lateinit var tvPayTotal:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)
        setupItemView()
    }

    private fun setupItemView() {
        tvDateOrder = findViewById(R.id.tv_historydetail_tglorder_date)
        tvDateAccept = findViewById(R.id.tv_historydetail_tglsetuju_date)
        tvDatePay = findViewById(R.id.tv_historydetail_tglbayar_date)
        tvOrderStatus = findViewById(R.id.tv_historydetail_desc_status)
        rvProductOrdered = findViewById(R.id.rv_historydetail_product_ordered_list)
        tvPayTotal  = findViewById(R.id.tv_historydetail_total_harga)
    }
}