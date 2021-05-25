package app.siakad.siakadtkadmin.presentation.views.date

interface DateListener {
    fun onDataSet(year: Int, month: Int, day: Int, tag: String)
}