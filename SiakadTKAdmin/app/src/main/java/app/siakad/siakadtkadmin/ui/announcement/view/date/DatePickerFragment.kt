package app.siakad.siakadtkadmin.ui.announcement.view.date

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import app.siakad.siakadtkadmin.ui.announcement.view.date.DateListener
import java.util.*

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var dateListener: DateListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)

        return DatePickerDialog(activity as Context, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        dateListener.onDataSet(year,month, day)
    }

    fun setDateListener(listener: DateListener) {
        dateListener = listener
    }
}