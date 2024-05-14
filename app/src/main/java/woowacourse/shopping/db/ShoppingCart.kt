package woowacourse.shopping.db

object ShoppingCart {

    private val _productIds = mutableListOf<Int>()
    val productIds = _productIds.toList()

    fun addProductToCart(productId: Int) {
        _productIds.add(productId)
    }
}
