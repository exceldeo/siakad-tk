package app.siakad.siakadtkadmin.presentation.product

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import app.siakad.siakadtkadmin.R

class UniformProductDialog: DialogFragment() {

    private lateinit var etUkuran: TextView
    private lateinit var etJumlah: TextView
    private lateinit var etHarga: TextView
    private lateinit var btnTambahkan: CardView
    private lateinit var btnBatal: CardView

    private var customDialog: Dialog? =  null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customDialog = Dialog(context)
    }

    override fun onDetach() {
        super.onDetach()
        if (customDialog != null) {
            customDialog = null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setupDialogItemView()
        setupDialogView()

        return customDialog!!
    }

    private fun setupDialogItemView() {
        customDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_uniform_detail_add)
            setCancelable(true)
        }

        if (customDialog != null) {
            etUkuran = customDialog!!.findViewById(R.id.et_dialog_uniform_add_ukuran)
            etJumlah = customDialog!!.findViewById(R.id.et_dialog_uniform_add_jumlah)
            etHarga = customDialog!!.findViewById(R.id.et_dialog_uniform_add_harga)
            btnTambahkan = customDialog!!.findViewById(R.id.btn_dialog_uniform_add_tambahkan)
            btnBatal = customDialog!!.findViewById(R.id.btn_dialog_uniform_add_batal)
        }
    }

    private fun setupDialogView() {
        btnTambahkan.setOnClickListener {
            customDialog?.dismiss()
        }

        btnBatal.setOnClickListener {
            customDialog?.dismiss()
        }
    }
}