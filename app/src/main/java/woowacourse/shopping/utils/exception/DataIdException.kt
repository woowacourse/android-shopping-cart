package woowacourse.shopping.utils.exception

class DataIdException(message: String = DATA_ID_MESSAGE): Exception(message) {
    companion object {
        private const val DATA_ID_MESSAGE = "No such data ID"
    }
}
