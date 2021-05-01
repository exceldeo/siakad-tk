package app.siakad.siakadtk.presentation.screens.main.product.detail.book

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.siakad.siakadtk.R
import app.siakad.siakadtk.infrastructure.data.product.Buku
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_product_detail.view.*


//NEED TO BE FIXED
class ProductBookDetailActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val pageTitle = "Produk Buku"

    private lateinit var toolbar: Toolbar
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductOrderDeadline: TextView
    private lateinit var etProductSum: EditText
    private lateinit var ivProductImage: ImageView
    private lateinit var tvProductTotalPayment: TextView
    private lateinit var btnProductAddToBasket: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_book_detail)
        setupItemView()
        setupView()

        val data = intent.getParcelableExtra<Parcelable>("buku") as Buku
        Picasso.get().load(data.fotoProduk).into(ivProductImage)
        tvProductName.text = data.namaProduk
        tvProductPrice.text = data.harga.toString()
        etProductSum.setText(data.jumlah.toString())
        tvProductTotalPayment.text = "Total : Rp " + (data.harga * Integer.valueOf(etProductSum.text.toString())).toString()
    }

    private fun setupItemView() {
        toolbar = findViewById(R.id.toolbar_main)
        ivProductImage = findViewById(R.id.iv_product_bookdetail_img)
        tvProductName = findViewById(R.id.tv_product_bookdetail_nama)
        tvProductPrice = findViewById(R.id.tv_product_bookdetail_harga)
        tvProductOrderDeadline = findViewById(R.id.tv_product_bookdetail_batas_pesan)
        etProductSum = findViewById(R.id.et_product_bookdetail_jumlah)
        tvProductTotalPayment = findViewById(R.id.tv_product_bookdetail_total_harga)
        btnProductAddToBasket = findViewById(R.id.btn_product_bookdetail_tambah_ke_basket)

        etProductSum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                tvProductTotalPayment.text = "Total : Rp " + (Integer.valueOf(tvProductPrice.text.toString()) * Integer.valueOf(etProductSum.text.toString())).toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupView() {
        setupAppBar()
    }

    private fun setupAppBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = pageTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun validateInput(): Boolean {
        return true
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            Toast.makeText(
                this,
                "OnItemSelectedListener : " + p0.selectedItem.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

