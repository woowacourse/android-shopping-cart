package woowacourse.shopping.feature.cart.model

import woowacourse.shopping.core.model.Money
import woowacourse.shopping.core.model.Product

data class Cart(
    val items: List<CartItem> = emptyList(),
) {
    fun addItem(product: Product): Cart {
        require(items.none { it.product == product }) { "이미 장바구니에 있는 상품입니다." }
        val newItems = items + CartItem(product = product, quantity = 1)
        return copy(items = newItems)
    }

    fun deleteItem(product: Product): Cart {
        val newItems = items.filter { it.product != product }
        return copy(items = newItems)
    }

    fun calculateTotalPrice(): Money {
        val totalPrice = items.sumOf { it.getTotalPrice().amount }
        return Money(totalPrice)
    }
}
