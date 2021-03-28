package app.siakad.siakadtk.presentation.views.date

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import app.siakad.siakadtk.R
import java.util.*

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var dateListener: DateListener? = null

    companion object {
        const val YEAR_ARG = "YEAR"
        const val MONTH_ARG = "MONTH"
        const val DAY_ARG = "DAY"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DATE)

        if (arguments != null) {
            year = requireArguments().getInt(YEAR_ARG)
            month = requireArguments().getInt(MONTH_ARG)
            day = requireArguments().getInt(DAY_ARG)
        }

        return DatePickerDialog(activity as Context, R.style.DialogTheme, this, year, month, day)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dateListener = context as DateListener?
    }

    override fun onDetach() {
        super.onDetach()
        if (dateListener != null) {
            dateListener = null
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        dateListener?.onDataSet(year,month, day)
    }
}