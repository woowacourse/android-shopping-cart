package woowacourse.shopping.database

import woowacourse.shopping.productdetail.ProductUiModel

interface ShoppingRepository {

    fun loadProducts(): List<ProductUiModel>
}
