package app.siakad.siakadtkadmin.presentation.screens.product.listener

import app.siakad.siakadtkadmin.domain.models.product.BukuModel
import app.siakad.siakadtkadmin.domain.models.product.SeragamModel

interface ProductListListener {
    fun navigateToUniformEdit(uniform: SeragamModel)
    fun navigateToBookEdit(book: BukuModel)
}