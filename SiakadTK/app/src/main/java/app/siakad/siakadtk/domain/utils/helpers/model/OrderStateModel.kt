package app.siakad.siakadtk.domain.utils.helpers.model

enum class OrderStateModel(val str: String) {
    WAITPAYMENT("Menunggu Pembayaran"),
    PROSES("Proses Verifikasi"),
    SETUJU("Disetujui"),
}