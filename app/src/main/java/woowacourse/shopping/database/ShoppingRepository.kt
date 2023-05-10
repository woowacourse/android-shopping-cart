package woowacourse.shopping.database

import woowacourse.shopping.productdetail.ProductUiModel

interface ShoppingRepository {

    fun selectProducts(): List<ProductUiModel>

    fun selectShoppingCartProducts(): List<ProductUiModel>

    fun selectProductById(id: Int): ProductUiModel

    fun insertToShoppingCart(id: Int)

    fun deleteFromShoppingCart(id: Int)

    fun insertToRecentViewedProducts(id: Int)

    fun selectRecentViewedProducts(): List<ProductUiModel>
}
