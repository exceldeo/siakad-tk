package app.siakad.siakadtkadmin.presentation.screens.product.uniform.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import app.siakad.siakadtkadmin.R

class UniformProductDialog : DialogFragment() {

    private lateinit var etUkuran: TextView
    private lateinit var etJumlah: TextView
    private lateinit var etHarga: TextView
    private lateinit var btnTambahkan: CardView
    private lateinit var btnBatal: CardView

    private var customDialog: Dialog? = null
    private var uniformDialogListener: UniformProductListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customDialog = Dialog(context)
        uniformDialogListener = context as UniformProductListener
    }

    override fun onDetach() {
        super.onDetach()

        if (customDialog != null) {
            customDialog = null
        }

        if (uniformDialogListener != null) {
            uniformDialogListener = null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setupDialog()
        setupDialogButtons()

        return customDialog!!
    }

    private fun setupDialog() {
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

    private fun setupDialogButtons() {
        btnTambahkan.setOnClickListener {
            if (validateInput()) {
                uniformDialogListener?.insertData(
                    etUkuran.text.toString(),
                    Integer.parseInt(etJumlah.text.toString()),
                    Integer.parseInt(etHarga.text.toString())
                )
                customDialog?.dismiss()
            }
        }

        btnBatal.setOnClickListener {
            customDialog?.dismiss()
        }
    }

    private fun validateInput(): Boolean {
        var returnState = true

        if (etUkuran.text.isEmpty()) {
            etUkuran.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etJumlah.text.isEmpty()) {
            etJumlah.error = getString(R.string.empty_input)
            returnState = false
        }

        if (etHarga.text.isEmpty()) {
            etHarga.error = getString(R.string.empty_input)
            returnState = false
        }

        return returnState
    }
}