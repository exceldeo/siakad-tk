package app.siakad.siakadtk.presentation.screens.history.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.presentation.screens.main.MainActivity
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.infrastructure.data.Pesanan
import app.siakad.siakadtk.infrastructure.viewmodels.screens.order.OrderViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import app.siakad.siakadtk.presentation.screens.history.detail.adapter.OrderHistoryDetailAdapter
import app.siakad.siakadtk.presentation.screens.order.detail.adapter.OrderDetailAdapter
import app.siakad.siakadtk.presentation.views.preview.ImagePreviewActivity

class HistoryDetailActivity : AppCompatActivity() {
    private val pageTitle = "Detail Riwayat Pembayaran"

    private lateinit var toolbar: Toolbar

    private lateinit var rvOrderHistoryDetailAdapter: OrderHistoryDetailAdapter
    private var pesanan = Pesanan(PesananModel())
    private var items = arrayListOf<DetailKeranjangModel>()

    private lateinit var tvOrderTitle: TextView

    private lateinit var tvOrderDate: TextView
    private lateinit var tvProcessDate: TextView
    private lateinit var tvDoneDate: TextView

    private lateinit var tvOrderStatus: TextView
    private lateinit var btnLihatBukti: Button

    private lateinit var rvOrderHistoryDetail: RecyclerView
    private lateinit var tvOrderTotalPayment: TextView

    private lateinit var vmOrderList: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)

        val data = intent.getParcelableExtra<Parcelable>("pesanan_selesai") as Pesanan
        pesanan = data

        for (item in pesanan.pesanan.detailPesanan!!)
            items.add(item)
        
        setupItemView()
        setupView()
    }

    private fun setupView() {
        setupAppBar()

        rvOrderHistoryDetail.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HistoryDetailActivity)
            rvOrderHistoryDetailAdapter = OrderHistoryDetailAdapter(items)
            adapter = rvOrderHistoryDetailAdapter
        }

        vmOrderList = ViewModelProvider(
            this,
            ViewModelFactory(this, this)
        ).get(OrderViewModel::class.java)

        var totalPayment = 0
        for (item in items)
            totalPayment += item.jumlah * item.harga

        tvOrderTotalPayment.text = "Rp. $totalPayment"
        tvOrderDate.text = pesanan.pesanan.tanggalDipesan
        tvOrderStatus.text = pesanan.pesanan.statusPesan

        btnLihatBukti.setOnClickListener {
            previewImage()
        }
    }

    private fun previewImage() {
        val intent = Intent(this@HistoryDetailActivity, ImagePreviewActivity::class.java)
        intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE_TYPE, ImagePreviewActivity.IMG_URL)
        intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE, pesanan.pesanan.fotoBayar)
        startActivity(intent)
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
        tvOrderTitle = findViewById(R.id.tv_history_detail_nota_title)

        tvOrderDate = findViewById(R.id.tv_history_detail_tglorder_date)
        tvProcessDate = findViewById(R.id.tv_history_detail_tglsetuju_date)
        tvDoneDate = findViewById(R.id.tv_history_detail_tglselesai_date)

        tvOrderStatus = findViewById(R.id.tv_history_detail_desc_status)
        btnLihatBukti = findViewById(R.id.btn_history_detail_lihat_bukti)

        rvOrderHistoryDetail = findViewById(R.id.rv_history_detail_product_list)
        tvOrderTotalPayment = findViewById(R.id.tv_history_detail_total_payment)
        
        rvOrderHistoryDetail.setHasFixedSize(true)
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}