package woowacourse.shopping.domain

import woowacourse.shopping.domain.util.CountUpdateType

data class PurchaseProduct(
    private val product: Product,
    val count: Int = 1
) {
    init {
        require(count > 0) { "구매할 상품의 개수는 1개 이상이어야 합니다." }
    }

    fun updateCount(type: CountUpdateType): PurchaseProduct {
        val newCount = when(type) {
            CountUpdateType.INCREASE -> count + 1
            CountUpdateType.DECREASE -> count - 1
        }
        return copy(count = newCount)
    }

    fun totalPrice() = product.price * count
}
