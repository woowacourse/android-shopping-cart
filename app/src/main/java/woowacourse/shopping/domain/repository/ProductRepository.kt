package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun loadProducts(): List<Product>

    fun loadPagingProducts(offset:Int): List<Product>

    fun getProduct(productId: Long) : Product

    fun addCartItem(product: Product) : CartItem

    fun loadCartItems(): List<CartItem>

    fun loadPagingCartItems(offset: Int):List<CartItem>

    fun deleteCartItem(itemId: Long)
}
