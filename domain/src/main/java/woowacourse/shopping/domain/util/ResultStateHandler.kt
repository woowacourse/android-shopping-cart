package woowacourse.shopping.domain.util

sealed class WoowaResult<out T : Any> {
    data class SUCCESS<out T : Any>(val data: T) : WoowaResult<T>()
    data class FAIL(val error: Error) : WoowaResult<Nothing>()
}

sealed class Error(val errorMessage: String) {
    object NoSuchId : Error("해당 ID가 없습니다")
    object DataBaseError : Error("디비에서 오류가 발생했습니다")
    object DataBaseEmpty : Error("디비에 아이템이 없습니다")
}
