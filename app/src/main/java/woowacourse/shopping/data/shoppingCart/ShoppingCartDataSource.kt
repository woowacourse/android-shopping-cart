package woowacourse.shopping.data.shoppingCart

import woowacourse.shopping.domain.util.WoowaResult

interface ShoppingCartDataSource {
    fun getProductsInShoppingCart(unit: Int, pageNumber: Int): List<ProductInCartEntity>
    fun deleteProductInShoppingCart(productId: Long): Boolean
    fun addProductInShoppingCart(productId: Long, quantity: Int): Long
    fun getShoppingCartSize(): Int
    fun getTotalQuantity(): Int
    fun updateProductCount(productId: Long, updatedQuantity: Int): WoowaResult<Int>
    fun getAllEntities(): List<ProductInCartEntity>
}
