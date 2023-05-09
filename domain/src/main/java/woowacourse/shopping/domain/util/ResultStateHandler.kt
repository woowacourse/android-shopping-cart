package woowacourse.shopping.domain.util

sealed class WoowaResult<out T : Any> {
    data class SUCCESS<out T : Any>(val data: T) : WoowaResult<T>()
    data class FAIL(val error: Error) : WoowaResult<Nothing>()
}

sealed class Error(val errorMessage: String) {
    object NoSuchId : Error("해당 ID가 없습니다")
}
