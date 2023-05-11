package woowacourse.shopping.data.shoppingCart

import woowacourse.shopping.domain.model.ProductInCart

interface ShoppingCartDataSource {
    fun getProductsInShoppingCart(unit: Int, pageNumber: Int): List<ProductInCartEntity>
    fun deleteProductInShoppingCart(productId: Long): Boolean
    fun addProductInShoppingCart(productInCart: ProductInCart): Long
    fun getShoppingCartSize(): Int
}
