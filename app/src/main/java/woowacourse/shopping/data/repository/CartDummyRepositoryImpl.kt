package woowacourse.shopping.data.repository

import woowacourse.shopping.data.dummyProducts
import woowacourse.shopping.domain.model.Product

object CartDummyRepositoryImpl : CartRepository {
    private const val COLLECTION_POSITION_OFFSET: Int = 1
    private const val LOAD_ITEM_COUNT: Int = 5
    private const val PAGE_COUNT_OFFSET: Int = 1
    private const val CART_PRODUCT_ID_INCREMENT: Int = 1
    private const val INITIAL_CART_PRODUCT_ID: Int = 0
    private val cart: MutableList<Product> = dummyProducts.take(7).toMutableList()

    override fun fetchCartProducts(page: Int): List<Product> =
        cart
            .drop(((page - COLLECTION_POSITION_OFFSET) * LOAD_ITEM_COUNT).coerceAtLeast(0))
            .take(LOAD_ITEM_COUNT)

    override fun fetchMaxPageCount(): Int =
        if (cart.size % LOAD_ITEM_COUNT == 0) {
            cart.size / LOAD_ITEM_COUNT
        } else {
            (cart.size / LOAD_ITEM_COUNT) + PAGE_COUNT_OFFSET
        }

    override fun addCartProduct(product: Product) {
        cart.add(product.copy(id = (cart.maxOfOrNull { it.id } ?: INITIAL_CART_PRODUCT_ID) + CART_PRODUCT_ID_INCREMENT))
    }

    override fun removeCartProduct(id: Int) {
        cart.removeIf { it.id == id }
    }

    override fun clearCart() {
        cart.clear()
    }
}
