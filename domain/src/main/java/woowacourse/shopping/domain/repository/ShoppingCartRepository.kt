package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.util.WoowaResult

interface ShoppingCartRepository {

    fun getAll(): List<ProductInCart>
    fun getShoppingCart(unit: Int, pageNumber: Int): List<ProductInCart>
    fun insertProductInCart(productInCart: ProductInCart): Long
    fun deleteProductInCart(id: Long): Boolean
    fun getShoppingCartSize(): Int
    fun getTotalQuantity(): Int
    fun updateProductQuantity(productId: Long, count: Int): WoowaResult<Int>
    fun increaseProductQuantity(productId: Long, plusCount: Int)
}
