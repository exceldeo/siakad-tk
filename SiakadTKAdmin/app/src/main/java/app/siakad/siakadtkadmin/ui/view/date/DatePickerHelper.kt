package app.siakad.siakadtkadmin.ui.view.date

import androidx.fragment.app.FragmentManager
import java.text.SimpleDateFormat
import java.util.*

class DatePickerHelper(fm: FragmentManager):
    DateListener {

    private val supportFragmentManager = fm

    private var datePicker: DatePickerFragment
    private var calendar: Calendar

    private var date: String = ""

    init {
        datePicker =
            DatePickerFragment()
        calendar = Calendar.getInstance()
        datePicker.setDateListener(this)
    }

    override fun onDataSet(year: Int, month: Int, day: Int) {
        calendar.set(year, month, day)
        setDate()
    }

    fun setDate() {
        date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
    }

    fun showDialog() {
        datePicker.show(supportFragmentManager, null)
    }

    fun getDate(): String {
        return date
    }
}