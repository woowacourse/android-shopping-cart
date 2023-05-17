package woowacourse.shopping.data.shoppingCart

interface ShoppingCartDataSource {
    fun getProductsInShoppingCart(unit: Int, pageNumber: Int): List<ProductInCartEntity>
    fun deleteProductInShoppingCart(productId: Long): Boolean
    fun addProductInShoppingCart(productId: Long, productQuantity: Int): Long
    fun getShoppingCartSize(): Int
}
