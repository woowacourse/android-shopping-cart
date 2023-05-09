package woowacourse.shopping.data.shoppingCart

interface ShoppingCartDataSource {
    fun getProductsInShoppingCart(unit: Int, pageNumber: Int): List<ProductInCartEntity>
    fun deleteProductInShoppingCart(id: Long): Boolean
}
