package app.siakad.siakadtkadmin.presentation.main.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.infrastructure.viewmodels.main.product.ProductViewModel
import app.siakad.siakadtkadmin.presentation.product.ProductAddActivity

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var svProduct: SearchView
    private lateinit var btnUniform: CardView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        productViewModel =
                ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        setupItemView(view)
        setupView()

        return view
    }

    private fun setupItemView(v: View?) {
        if (v != null) {
            svProduct = v.findViewById(R.id.sv_product_cari_produk)
            btnUniform = v.findViewById(R.id.btn_product_uniform)
        }
    }

    private fun setupView() {
        btnUniform.setOnClickListener {
            startActivity(Intent(this@ProductFragment.activity, ProductAddActivity::class.java))
        }
    }
}