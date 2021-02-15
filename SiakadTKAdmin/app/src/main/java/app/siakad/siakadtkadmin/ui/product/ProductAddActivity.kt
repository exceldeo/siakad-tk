package app.siakad.siakadtkadmin.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import app.siakad.siakadtkadmin.R

class ProductAddActivity : AppCompatActivity() {

    private lateinit var btnAddPhoto: RelativeLayout
    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var etNum: EditText
    private lateinit var btnAddData: RelativeLayout
    private lateinit var btnCancel: CardView
    private lateinit var btnSave: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_add)
    }

    private fun setupItemView() {
        btnAddPhoto = findViewById(R.id.iv_product_add_foto)
        etName = findViewById(R.id.et_product_add_nama)
        etPrice = findViewById(R.id.et_product_add_harga)
        etNum = findViewById(R.id.et_product_add_jumlah)
        btnAddData = findViewById(R.id.iv_product_add_tambah_data)
        btnCancel = findViewById(R.id.btn_product_add_batal)
        btnSave = findViewById(R.id.btn_product_add_simpan)
    }

    private fun setupView() {}
}