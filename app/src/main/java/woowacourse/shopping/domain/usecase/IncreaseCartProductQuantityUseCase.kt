package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class IncreaseCartProductQuantityUseCase(
    private val repository: CartRepository,
) {
    operator fun invoke(
        productId: Int,
        quantity: Int = DEFAULT_QUANTITY_STEP,
        callback: (Int) -> Unit,
    ) {
        thread {
            val existing = repository.fetchCartProductDetail(productId)
            val newQuantity = (existing?.quantity ?: 0) + quantity

            repository.saveCartProduct(productId, newQuantity)
            callback(newQuantity)
        }
    }

    companion object {
        private const val DEFAULT_QUANTITY_STEP: Int = 1
    }
}
