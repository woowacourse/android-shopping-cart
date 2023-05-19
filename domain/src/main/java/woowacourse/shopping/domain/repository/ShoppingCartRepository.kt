package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ProductInCart

interface ShoppingCartRepository {

    fun getShoppingCart(): List<ProductInCart>
    fun getShoppingCartByPage(unit: Int, pageNumber: Int): List<ProductInCart>
    fun addProductInCart(productInCart: ProductInCart): Long
    fun deleteProductInCart(id: Long): Boolean

    fun getShoppingCartSize(): Int
}
