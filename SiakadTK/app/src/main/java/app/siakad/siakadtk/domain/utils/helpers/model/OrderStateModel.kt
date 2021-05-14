package app.siakad.siakadtk.domain.utils.helpers.model

enum class OrderStateModel(val str: String) {
    ORDER_PENDING("Dipesan"),
    ORDER_PROCESS("Diproses"),
    ORDER_DONE("Selesai")
}