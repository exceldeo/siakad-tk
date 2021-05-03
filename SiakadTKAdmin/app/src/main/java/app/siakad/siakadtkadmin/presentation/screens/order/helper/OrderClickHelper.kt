package app.siakad.siakadtkadmin.presentation.screens.order.helper

import app.siakad.siakadtkadmin.infrastructure.data.Pesanan

interface OrderClickHelper {
  fun navigateToOrderDetail(pesanan: Pesanan)
}