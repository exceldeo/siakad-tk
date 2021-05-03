package app.siakad.siakadtk.domain.utils.listeners.basket

import app.siakad.siakadtk.domain.models.DetailKeranjangModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface BasketListener {
    fun notifyInsertDataStatus(status: ModelContainer<String>)
    fun setBasketList(basket: ModelContainer<ArrayList<DetailKeranjangModel>>)

    fun addBasketItem(basket: ModelContainer<ArrayList<DetailKeranjangModel>>)
    fun removeBasketItem(basket: ModelContainer<ArrayList<DetailKeranjangModel>>)
}