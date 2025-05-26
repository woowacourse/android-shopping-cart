package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.model.CartProducts
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class GetCartProductsUseCase(
    private val repository: CartRepository,
) {
    operator fun invoke(
        page: Int,
        size: Int,
        callback: (CartProducts) -> Unit,
    ) {
        thread {
            val products = repository.fetchCartProducts(page, size)
            val totalCount = repository.fetchCartItemCount()
            val totalPage = (totalCount + size - 1) / size

            callback(CartProducts(products, totalPage))
        }
    }
}
