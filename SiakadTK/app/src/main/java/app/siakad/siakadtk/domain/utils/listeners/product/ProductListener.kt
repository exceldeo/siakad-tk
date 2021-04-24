package app.siakad.siakadtkadmin.domain.utils.listeners.product

import app.siakad.siakadtk.domain.utils.helpers.container.ModelContainer
import app.siakad.siakadtk.infrastructure.data.product.Buku
import app.siakad.siakadtk.infrastructure.data.product.Seragam

interface ProductListener {
    fun notifyInsertDataStatus(status: ModelContainer<String>)
    fun selectedBook(book: Buku)
    fun selectedUniform(uniform: Seragam)
}