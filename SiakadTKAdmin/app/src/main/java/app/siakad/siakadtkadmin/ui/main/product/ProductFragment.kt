package app.siakad.siakadtkadmin.ui.main.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import app.siakad.siakadtkadmin.R

class ProductFragment : Fragment() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var svProduct: SearchView
    private lateinit var rvProduct: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        productViewModel =
                ViewModelProviders.of(this).get(ProductViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        setupItemView(view)
        return view
    }

    private fun setupItemView(v: View?) {
        if (v != null) {
            svProduct = v.findViewById(R.id.sv_product_cari_produk)
            rvProduct = v.findViewById(R.id.rv_product_daftar_produk)
        }
    }
}