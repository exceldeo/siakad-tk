package app.siakad.siakadtk.presentation.screens.order.detail

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.PesananModel
import app.siakad.siakadtk.domain.utils.helpers.model.OrderStateModel
import app.siakad.siakadtk.domain.utils.listeners.order.OrderListListener
import app.siakad.siakadtk.infrastructure.data.Pesanan
import app.siakad.siakadtk.presentation.screens.order.OrderListActivity
import app.siakad.siakadtk.presentation.screens.order.detail.adapter.OrderDetailAdapter
import app.siakad.siakadtk.presentation.views.preview.ImagePreviewActivity

class OrderDetailActivity : AppCompatActivity() {
    private val pageTitle = "Detail Pemesanan"

    private lateinit var rvOrderDetailAdapter: OrderDetailAdapter
    private var pesanan = Pesanan(PesananModel())
    private var items = arrayListOf<DetailKeranjangModel>()

    private lateinit var tvOrderTitle: TextView

    private lateinit var tvOrderDate: TextView
    private lateinit var tvProcessDate: TextView
    private lateinit var tvDoneDate: TextView

    private lateinit var tvOrderStatus: TextView
    private lateinit var btnUploadBukti: Button
    private lateinit var llOrderStatus: LinearLayout

    private lateinit var rvOrderDetail: RecyclerView
    private lateinit var tvOrderTotalPayment: TextView

    private lateinit var cvOrderPay: CardView
    private lateinit var btnCancel: TextView
    private lateinit var btnPay: TextView


    private var paymentImage: Uri? = null

    companion object {
        const val PICK_PHOTO_REQUEST = 1000
        const val PERMISSION_REQUEST = 1001
    }

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        setupItemView()

        val data = intent.getParcelableExtra<Parcelable>("pesanan") as Pesanan
        pesanan = data

        for (item in pesanan.pesanan.detailPesanan!!)
            items.add(item)

        setupView()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)

        tvOrderTitle = findViewById(R.id.tv_order_detail_nota_title)

        tvOrderDate = findViewById(R.id.tv_order_detail_tglorder_date)
        tvProcessDate = findViewById(R.id.tv_order_detail_tglsetuju_date)
        tvDoneDate = findViewById(R.id.tv_order_detail_tglselesai_date)

        tvOrderStatus = findViewById(R.id.tv_order_detail_detail_desc_status)
        btnUploadBukti = findViewById(R.id.btn_order_detail_upload_bukti)
        llOrderStatus = findViewById(R.id.ll_order_detail_status_order)

        rvOrderDetail = findViewById(R.id.rv_order_detail_product_list)
        tvOrderTotalPayment = findViewById(R.id.tv_order_detail_total_payment)

        cvOrderPay = findViewById(R.id.cv_order_detail_bayar)
        btnCancel = findViewById(R.id.btn_order_detail_batal)
        btnPay = findViewById(R.id.btn_order_detail_bayar)
        rvOrderDetail.setHasFixedSize(true)
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        setupAppBar()

        rvOrderDetail.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@OrderDetailActivity)
            rvOrderDetailAdapter = OrderDetailAdapter(items)
            adapter = rvOrderDetailAdapter
        }

        var totalPayment = 0
        for (item in items)
            totalPayment += item.jumlah * item.harga

        tvOrderTotalPayment.text = "Rp. $totalPayment"
        tvOrderDate.text = pesanan.pesanan.tanggalPesan
        tvOrderStatus.text = pesanan.pesanan.statusPesan

        if(pesanan.pesanan.statusPesan == OrderStateModel.ORDER_PENDING.str)
        {
            cvOrderPay.visibility = View.VISIBLE
            btnUploadBukti.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED
                    ) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                        requestPermissions(
                            permissions,
                            PERMISSION_REQUEST
                        );
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }
            }
        } else {
            cvOrderPay.visibility = View.INVISIBLE
            btnUploadBukti.text = "Lihat Bukti Pembayaran"
            btnUploadBukti.setOnClickListener {
                if (pesanan != null) {
                    val intent = Intent(this@OrderDetailActivity, ImagePreviewActivity::class.java)
                    intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE_TYPE, ImagePreviewActivity.IMG_URL)
                    intent.putExtra(ImagePreviewActivity.IMAGE_SOURCE, pesanan.pesanan.fotoBayar)
                    startActivity(intent)
                }
            }
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this@OrderDetailActivity, OrderListActivity::class.java)
            startActivity(intent)
        }

        btnPay.setOnClickListener {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Akses ditolak", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_PHOTO_REQUEST) {
            val imageUri: Uri = data?.data!!
            btnUploadBukti.text = imageUri.toString()
            paymentImage = imageUri
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            PICK_PHOTO_REQUEST
        )
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}