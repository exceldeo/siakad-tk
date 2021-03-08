package app.siakad.siakadtkadmin.infrastructure

data class DataContainer<T>(
    var status: ModelState,
    var message: String? = null,
    var data: T? = null
) {
    companion object {
        fun<K> getSuccesModel(item: K): DataContainer<K> {
            return DataContainer(status = ModelState.SUCCESS, data = item)
        }

        fun<K> getSuccesModelWithMsg(item: K, msg: String): DataContainer<K> {
            return DataContainer(status = ModelState.SUCCESS, message = msg, data = item)
        }

        fun<K> getFailModel(msg: String): DataContainer<K> {
            return DataContainer(status = ModelState.ERROR, message = msg)
        }
    }
}