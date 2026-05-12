package woowacourse.shopping.domain.exception

sealed class ProductException(
    message:String,
    cause: Throwable? = null
): Exception(message, cause){

    data class ServerError(val code: Int, val serverMessage: String) :
        ProductException("Server error $code: $serverMessage")

    data class NetworkError(val throwable: Throwable): ProductException("Network error", throwable)

    data class NotFound(
        val path: String,
    ): ProductException("Resource not found at path: $path")

    data object EmptyBody : ProductException(
        "Response body is empty",
    )
}
