package app.siakad.siakadtkadmin.presentation.screens.order.detail.helper

interface OrderDetailHelper {
  fun checkTheItem(pos: Int)
  fun uncheckTheItem(pos: Int)

  fun confirmOrderDate(start: String, end: String)
}