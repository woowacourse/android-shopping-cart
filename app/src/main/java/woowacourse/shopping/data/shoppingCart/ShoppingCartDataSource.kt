package woowacourse.shopping.data.shoppingCart

interface ShoppingCartDataSource {

    fun getShoppingCart(): List<ProductInCartEntity>
    fun getShoppingCartByPage(unit: Int, pageNumber: Int): List<ProductInCartEntity>
    fun deleteProductInShoppingCart(productId: Long): Boolean
    fun addProductInShoppingCart(productId: Long, productQuantity: Int): Long
    fun getShoppingCartSize(): Int
}
