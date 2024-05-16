package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {

    fun loadPagingProducts(offset: Int,pagingSize:Int): List<Product>

    fun getProduct(productId: Long): Product

    fun addCartItem(product: Product): Long

    fun loadPagingCartItems(offset: Int,pagingSize:Int): List<CartItem>

    fun deleteCartItem(itemId: Long)
}
