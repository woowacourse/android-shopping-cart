package woowacourse.shopping.repository.http

sealed class ProductRemoteException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause)

class ProductNetworkException(
    message: String,
    cause: Throwable,
) : ProductRemoteException(message, cause)

class ProductResponseException(
    val code: Int,
    message: String,
) : ProductRemoteException(message)

class ProductParsingException(
    message: String,
    cause: Throwable,
) : ProductRemoteException(message, cause)
