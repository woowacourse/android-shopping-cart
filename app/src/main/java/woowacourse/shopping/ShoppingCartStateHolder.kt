package woowacourse.shopping

import androidx.compose.runtime.mutableStateListOf

class ShoppingCartStateHolder(
    vararg products: Product
) {
    private val shoppingCartItems = mutableStateListOf<ShoppingCartItem>()
    private var nextShoppingCartItemId: Long = 0L

    init {
        products.forEach { product ->
            add(product)
        }
    }

    fun add(product: Product) {
        shoppingCartItems.add(ShoppingCartItem(
            id = nextShoppingCartItemId++,
            product = product
        ))
    }

    fun getAllProducts(): List<ShoppingCartItem> = shoppingCartItems.toList()
}
