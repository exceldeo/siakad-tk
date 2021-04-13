package app.siakad.siakadtkadmin.domain.utils.listeners.product

import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel
import app.siakad.siakadtkadmin.domain.utils.helpers.container.ModelContainer

interface ProductListListener {
    fun setUniformList(product: ModelContainer<ArrayList<SeragamModel>>)
    fun setBookList(product: ModelContainer<ArrayList<BukuModel>>)
}