package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class UpdateCartProductUseCase(
    private val repository: CartRepository,
) {
    operator fun invoke(cartProduct: CartProduct) {
        thread {
            repository.saveCartProduct(cartProduct.product.id, cartProduct.quantity)
        }
    }
}
