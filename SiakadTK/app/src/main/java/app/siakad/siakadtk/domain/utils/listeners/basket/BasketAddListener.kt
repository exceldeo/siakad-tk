package app.siakad.siakadtk.domain.utils.listeners.basket

import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface BasketAddListener {
    fun addCheckedItem(pos: Int)
    fun addUncheckedItem(pos: Int)
    fun getStatusAllChecked() : Boolean
}