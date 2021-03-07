package app.siakad.siakadtkadmin.domain

data class DataModel<T>(
    var status: ModelState,
    var message: String? = null,
    var data: T? = null
) {
    companion object {
        fun<K> getSuccesModel(item: K): DataModel<K> {
            return DataModel(status = ModelState.SUCCESS, data = item)
        }

        fun<K> getSuccesModelWithMsg(item: K, msg: String): DataModel<K> {
            return DataModel(status = ModelState.SUCCESS, message = msg, data = item)
        }

        fun<K> getFailModel(msg: String): DataModel<K> {
            return DataModel(status = ModelState.ERROR, message = msg)
        }
    }
}