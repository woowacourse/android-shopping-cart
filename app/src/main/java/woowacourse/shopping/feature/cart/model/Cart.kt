package woowacourse.shopping.feature.cart.model

import woowacourse.shopping.core.model.Money
import woowacourse.shopping.core.model.Product

data class Cart(
    val items: List<CartItem> = emptyList(),
) {
    val size = items.size

    fun addItem(product: Product): AddItemResult {
        val existItem = items.firstOrNull { it.product == product }
        return if (existItem == null) {
            AddItemResult.NewAdded(
                copy(
                    items =
                        items +
                            CartItem(
                                product = product,
                                quantity = 1,
                            ),
                ),
            )
        } else {
            AddItemResult.DuplicateItem
        }
    }

    fun deleteItem(id: String): Cart {
        val newItems = items.filter { it.product.id != id }
        return copy(items = newItems)
    }

    fun calculateTotalPrice(): Money {
        val totalPrice = items.sumOf { it.getTotalPrice().amount }
        return Money(totalPrice)
    }
}
