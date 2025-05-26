package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class DecreaseCartProductQuantityUseCase(
    private val repository: CartRepository,
) {
    operator fun invoke(
        productId: Int,
        quantity: Int = DEFAULT_QUANTITY_STEP,
        callback: (Int) -> Unit,
    ) {
        thread {
            val existing = repository.fetchCartProductDetail(productId)
            val result =
                if (existing != null) {
                    val newQuantity = existing.quantity - quantity
                    if (newQuantity <= 0) {
                        repository.deleteCartProduct(productId)
                        0
                    } else {
                        repository.saveCartProduct(productId, newQuantity)
                        newQuantity
                    }
                } else {
                    0
                }

            callback(result)
        }
    }

    companion object {
        private const val DEFAULT_QUANTITY_STEP: Int = 1
    }
}
