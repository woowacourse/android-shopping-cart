package woowacourse.shopping.domain.exception

sealed class ShoppingException(
    message: String,
) : Exception(message) {
    class ServerException(
        message: String,
    ) : ShoppingException(message)

    class NotFoundException(
        message: String,
    ) : ShoppingException(message)

    class ConnectionException(
        message: String,
    ) : ShoppingException(message)
}
