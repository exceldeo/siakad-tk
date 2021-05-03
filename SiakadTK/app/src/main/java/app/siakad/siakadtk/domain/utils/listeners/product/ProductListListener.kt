package app.siakad.siakadtkadmin.domain.utils.listeners.product

import app.siakad.siakadtk.domain.models.product.BukuModel
import app.siakad.siakadtk.domain.models.product.SeragamModel
import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer

interface ProductListListener {
    fun setUniformList(product: ModelContainer<ArrayList<SeragamModel>>)
    fun setBookList(product: ModelContainer<ArrayList<BukuModel>>)
}