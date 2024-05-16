package woowacourse.shopping.db

object ShoppingCart {

    private val _productIds = mutableListOf<Int>()
    val productIds get() = _productIds.toList()

    fun addProductToCart(productId: Int) {
        _productIds.add(productId)
    }

    fun delete(productId: Int) {
        _productIds.remove(productId)
    }
}
