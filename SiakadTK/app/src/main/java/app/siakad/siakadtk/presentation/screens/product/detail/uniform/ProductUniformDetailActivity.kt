package app.siakad.siakadtk.presentation.screens.main.product.detail.uniform

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import app.siakad.siakadtk.R
import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.models.product.DetailSeragamModel
import app.siakad.siakadtk.domain.models.product.SeragamModel
import app.siakad.siakadtk.infrastructure.data.product.Seragam
import app.siakad.siakadtk.infrastructure.viewmodels.screens.basket.KeranjangViewModel
import app.siakad.siakadtk.infrastructure.viewmodels.utils.factory.ViewModelFactory
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_product_detail.view.*

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
    private lateinit var ivProductImage: ImageView
    private lateinit var tvProductTotalPayment: TextView
    private lateinit var btnProductAddToBasket: TextView

    private lateinit var vmBasket: KeranjangViewModel
    private var item = DetailKeranjangModel()
    private var limitOrder = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_uniform_detail)
        setupItemView()
        setupView()

        val data = intent.getParcelableExtra<Parcelable>("seragam") as SeragamModel
        if (data != null) {
            item.nama = data.namaProduk
            tvProductJenisKelamin.text = data.jenisKelamin
            item.gambar = data.fotoProduk
            item.produkId = data.produkId
            for (it in data.detailSeragam) {
                sizes.add(it.ukuran)
                prices[it.ukuran] = it.harga
                totals[it.ukuran] = it.jumlah
            }
        }

        tvProductName.text = item.nama
        Picasso.with(this.applicationContext).load(item.gambar).into(ivProductImage)
        setupAdapterListener()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        ivProductImage = findViewById(R.id.iv_product_unidetail_img)
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
        (ddProductSize.editText as MaterialAutoCompleteTextView).setText("Pilih ukuran")
        (ddProductSize.editText as MaterialAutoCompleteTextView).setAdapter(adapter)
        (ddProductSize.editText as MaterialAutoCompleteTextView).addTextChangedListener(object :
            TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(str: Editable?) {
                item.ukuran = str.toString()
                totals[str.toString()].toString().let {
                    etProductSum.setText(it)
                    if (it.isEmpty() || it == "") {
                        item.jumlah = 0
                    } else {
                        item.jumlah = Integer.valueOf(it)
                        limitOrder = Integer.valueOf(it)
                    }
                }
                prices[str.toString()].toString().let {
                    tvProductPrice.text = it
                    item.harga = Integer.valueOf(it)
                }
                tvProductTotalPayment.text = "Total : Rp " + (Integer.valueOf(tvProductPrice.text.toString()) * Integer.valueOf(etProductSum.text.toString())).toString()
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
        etProductSum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (etProductSum.text.isEmpty() || etProductSum.text.toString() == "") {
                    item.jumlah = 0
                } else {
                    item.jumlah = Integer.valueOf(etProductSum.text.toString())
                }
                tvProductTotalPayment.text = "Total : Rp " + (Integer.valueOf(item.jumlah.toString()) * Integer.valueOf(item.harga.toString())).toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupView() {
        setupAppBar()

        vmBasket = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                this
            )
        ).get(KeranjangViewModel::class.java)

        var isClicked = false
        btnProductAddToBasket.setOnClickListener{
            if (validateInput() && limitOrder > 0) {
                if (!isClicked) {
                    isClicked = true
                    vmBasket.insertItemBasket(
                        name = tvProductName.text.toString(),
                        image = item.gambar,
                        ukuran = item.ukuran,
                        jumlah = item.jumlah,
                        harga = item.jumlah * item.harga,
                        produkId = item.produkId
                    )
                } else {
                    showToast(application.applicationContext.getString(R.string.wait_process))
                }
            } else if (!validateInput() && limitOrder > 0) {
                showToast(application.applicationContext.getString(R.string.wrong_input))
            }
        }
    }

    private fun validateInput(): Boolean {
        var returnState = true
        if (limitOrder == 0) {
            showToast(application.applicationContext.getString(R.string.sold_out))
            returnState = false
        } else if (Integer.valueOf(etProductSum.text.toString()) > limitOrder) {
            etProductSum.error = getString(R.string.over_order)
            returnState = false
        } else if (Integer.valueOf(etProductSum.text.toString()) <= 0) {
            etProductSum.error = getString(R.string.min_order)
            returnState = false
        }
        return returnState
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}