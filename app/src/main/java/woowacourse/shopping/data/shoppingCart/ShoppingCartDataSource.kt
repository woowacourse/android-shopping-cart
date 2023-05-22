package woowacourse.shopping.data.shoppingCart

import woowacourse.shopping.domain.util.WoowaResult

interface ShoppingCartDataSource {
    fun getProductsInShoppingCart(unit: Int, pageNumber: Int): List<ProductInCartEntity>
    fun deleteProductInShoppingCart(productId: Long): Boolean
    fun addProductInShoppingCart(productId: Long, quantity: Int): Long
    fun getShoppingCartSize(): Int
    fun getTotalQuantity(): Int
    fun updateProductQuantity(productId: Long, updatedQuantity: Int): WoowaResult<Int>
    fun increaseProductQuantity(productId: Long, plusQuantity: Int): WoowaResult<Int>
    fun getAllEntities(): List<ProductInCartEntity>
}
