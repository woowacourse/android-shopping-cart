package woowacourse.shopping.database

import woowacourse.shopping.productdetail.ProductUiModel

interface ShoppingRepository {

    fun loadProducts(): List<ProductUiModel>

    fun loadShoppingCartProducts(): List<ProductUiModel>

    fun findProductById(id: Int): ProductUiModel

    fun addToShoppingCart(id: Int)

    fun deleteFromShoppingCart(id: Int)
}
