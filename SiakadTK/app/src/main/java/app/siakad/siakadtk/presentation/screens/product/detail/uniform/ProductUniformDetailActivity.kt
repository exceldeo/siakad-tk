package app.siakad.siakadtk.presentation.screens.main.product.detail.uniform

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.product.Seragam
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class ProductUniformDetailActivity : AppCompatActivity() {
    private val pageTitle = "Produk Seragam"

    private val sizes = arrayListOf<String>()
    private val prices = mutableMapOf<String, Int>()
    private val totals = mutableMapOf<String, Int>()

    private lateinit var toolbar: Toolbar
    private lateinit var tvProductName: TextView
//    private lateinit var tvProductOrderDeadline: TextView
    private lateinit var tvProductJenisKelamin: TextView
    private lateinit var tvProductPrice: TextView
//    private lateinit var spProductSize: Spinner
    private lateinit var ddProductSize: TextInputLayout
    private lateinit var etProductSum: EditText
    private lateinit var tvProductTotalPayment: TextView
    private lateinit var btnProductAddToBasket: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_uniform_detail)
        setupItemView()
        setupView()

        val data = intent.getParcelableExtra<Parcelable>("seragam") as Seragam
        if (data != null) {
            tvProductName.text = data.namaProduk
            tvProductJenisKelamin.text = data.jenisKelamin
            for (item in data.detailSeragam) {
                sizes.add(item.ukuran)
                prices[item.ukuran] = item.harga
                totals[item.ukuran] = item.jumlah
            }
        }
        setupAdapterListener()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        tvProductName = findViewById(R.id.tv_product_unidetail_nama)
//        tvProductOrderDeadline = findViewById(R.id.tv_product_unidetail_batas_pesan)
        tvProductJenisKelamin = findViewById(R.id.tv_product_unidetail_jenis_kelamin)
        tvProductPrice = findViewById(R.id.tv_product_unidetail_price)
        etProductSum = findViewById(R.id.et_product_unidetail_jumlah)
        tvProductTotalPayment = findViewById(R.id.tv_product_unidetail_total_harga)
        btnProductAddToBasket = findViewById(R.id.btn_product_unidetail_tambah_ke_basket)
    }

    private fun setupAdapterListener() {
        val adapter = ArrayAdapter(this.applicationContext, R.layout.item_dropdown, sizes)

        ddProductSize = findViewById(R.id.dd_product_unidetail_ukuran)
        (ddProductSize.editText as MaterialAutoCompleteTextView).setText(sizes[0])
        (ddProductSize.editText as MaterialAutoCompleteTextView).setAdapter(adapter)
        (ddProductSize.editText as MaterialAutoCompleteTextView).addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(str: Editable?) {
                totals[str.toString()].toString().let { etProductSum.setText(it) }
                prices[str.toString()].toString().let { tvProductPrice.text = it }
            }

            override fun beforeTextChanged(
                str: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(str: CharSequence?, start: Int, before: Int, count: Int) {}
        })
//        val dataAdapter = ArrayAdapter(this.applicationContext, android.R.layout.simple_spinner_item, sizes)
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spProductSize.adapter = dataAdapter
//        spProductSize?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                if (parent != null) {
//                    totals[parent.selectedItem.toString()]?.let { etProductSum.setText(it) }
//                    prices[parent.selectedItem.toString()]?.let { tvProductPrice.setText(it) }
//                }
//            }
//
//        }
    }

    private fun setupView() {
        setupAppBar()
        btnProductAddToBasket.setOnClickListener {

        }
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}