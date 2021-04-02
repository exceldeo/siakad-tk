package app.siakad.siakadtkadmin.presentation.screens.main.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.product.ProductListActivity

class ProductFragment : Fragment() {

    private lateinit var svProduct: SearchView
    private lateinit var btnUniform: CardView
    private lateinit var btnBook: CardView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        svProduct = view.findViewById(R.id.sv_product_cari_produk)

        setupButtons(view)

        return view
    }

    private fun setupButtons(v: View?) {
        if (v != null) {
            btnUniform = v.findViewById(R.id.btn_product_uniform)
            btnBook.setOnClickListener {
                val intent = Intent(this@ProductFragment.activity, ProductListActivity::class.java)
                intent.putExtra(ProductListActivity.PAGE_TYPE, ProductListActivity.UNIFORM_PAGE)
                startActivity(intent)
            }

            btnBook = v.findViewById(R.id.btn_product_buku)
            btnBook.setOnClickListener {
                val intent = Intent(this@ProductFragment.activity, ProductListActivity::class.java)
                intent.putExtra(ProductListActivity.PAGE_TYPE, ProductListActivity.BOOK_PAGE)
                startActivity(intent)
            }
        }
    }
}