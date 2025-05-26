package woowacourse.shopping.domain.usecase

import woowacourse.shopping.domain.repository.CartRepository
import kotlin.concurrent.thread

class RemoveCartProductUseCase(
    private val repository: CartRepository,
) {
    operator fun invoke(productId: Int) {
        thread {
            repository.deleteCartProduct(productId)
        }
    }
}
