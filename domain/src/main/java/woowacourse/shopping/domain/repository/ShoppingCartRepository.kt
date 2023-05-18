package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.util.WoowaResult

interface ShoppingCartRepository {

    fun getShoppingCart(unit: Int, pageNumber: Int): List<ProductInCart>
    fun insertProductInCart(productInCart: ProductInCart): Long
    fun deleteProductInCart(id: Long): Boolean
    fun getShoppingCartSize(): Int
    fun getProductInCartCount(): Int
    fun updateProductCount(productId: Long, count: Int): WoowaResult<Int>
}
