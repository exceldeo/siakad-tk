package app.siakad.siakadtkadmin.presentation.screens.order.detail.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import app.siakad.siakadtkadmin.R
import app.siakad.siakadtkadmin.presentation.screens.order.detail.helper.OrderDetailHelper
import app.siakad.siakadtkadmin.presentation.views.date.DateListener
import app.siakad.siakadtkadmin.presentation.views.date.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class OrderConfirmDialog(private val fm: FragmentManager) : DialogFragment() {

  private lateinit var ivStartDate: ImageView
  private lateinit var etStartDate: EditText
  private lateinit var ivEndDate: ImageView
  private lateinit var etEndDate: EditText
  
  private lateinit var btnConfirm: CardView
  private lateinit var btnBatal: CardView

  private lateinit var datePicker: DatePickerFragment
  private lateinit var calendar: Calendar

  private var customDialog: Dialog? = null
  private var orderDialogListener: OrderDetailHelper? = null

  companion object {
    const val TAG_START = "Start Date"
    const val TAG_END = "End Date"
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    customDialog = Dialog(context)
    orderDialogListener = context as OrderDetailHelper
  }

  override fun onDetach() {
    super.onDetach()

    if (customDialog != null) {
      customDialog = null
    }

    if (orderDialogListener != null) {
      orderDialogListener = null
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
      setContentView(R.layout.dialog_order_process_confirm)
      setCancelable(true)
    }

    if (customDialog != null) {
      etStartDate = customDialog?.findViewById(R.id.et_dialog_confirm_start_tanggal)!!
      etEndDate = customDialog?.findViewById(R.id.et_dialog_confirm_end_tanggal)!!
    }
  }

  private fun setupDialogButtons() {
    datePicker = DatePickerFragment()
    calendar = Calendar.getInstance()

    if (customDialog != null) {
      ivStartDate = customDialog!!.findViewById(R.id.iv_dialog_confirm_start_tanggal)
      ivStartDate.setOnClickListener {
        val arg = Bundle()

        arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
        arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
        arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
        datePicker.arguments = arg

        datePicker.show(fm, TAG_START)
      }

      ivEndDate = customDialog!!.findViewById(R.id.iv_dialog_confirm_end_tanggal)
      ivEndDate.setOnClickListener {
        val arg = Bundle()

        arg.putInt(DatePickerFragment.YEAR_ARG, calendar.get(Calendar.YEAR))
        arg.putInt(DatePickerFragment.MONTH_ARG, calendar.get(Calendar.MONTH))
        arg.putInt(DatePickerFragment.DAY_ARG, calendar.get(Calendar.DATE))
        datePicker.arguments = arg

        datePicker.show(fm, TAG_END)
      }
      
      btnConfirm = customDialog!!.findViewById(R.id.btn_dialog_uniform_add_tambahkan)
      btnConfirm.setOnClickListener {
        if (validateInput()) {
          orderDialogListener?.confirmOrderDate(etStartDate.text.toString(), etEndDate.text.toString())
          customDialog?.dismiss()
        }
      }
      
      btnBatal = customDialog!!.findViewById(R.id.btn_dialog_uniform_add_batal)
      btnBatal.setOnClickListener {
        customDialog?.dismiss()
      }
    }
  }

  private fun setupDate(type: String) {
    val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
    if (type == "Mulai") {
      etStartDate.text = SpannableStringBuilder(date)
    } else {
      etEndDate.text = SpannableStringBuilder(date)
    }
  }

  private fun validateInput(): Boolean {
    var returnState = true

    if (etStartDate.text.isEmpty()) {
      etStartDate.error = getString(R.string.empty_input)
      returnState = false
    }

    if (etEndDate.text.isEmpty()) {
      etEndDate.error = getString(R.string.empty_input)
      returnState = false
    }

    return returnState
  }


  fun onDateSet(year: Int, month: Int, day: Int, tag: String) {
    calendar.set(year, month, day)
    if (tag == TAG_START) {
      setupDate("Mulai")
    } else if (tag == TAG_END) {
      setupDate("Selesai")
    }
  }
}