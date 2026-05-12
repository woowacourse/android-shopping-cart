package woowacourse.shopping.data.repository

import woowacourse.shopping.model.Cart

sealed interface CartResult {
    data class Success(
        val cart: Cart,
    ) : CartResult

    data class Failure(
        val cause: Throwable,
    ) : CartResult
}
