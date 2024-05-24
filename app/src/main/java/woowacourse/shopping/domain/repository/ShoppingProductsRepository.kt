package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductData

interface ShoppingProductsRepository {
    fun loadAllProducts(page: Int): List<ProductData>

    fun loadProduct(findId: Int): ProductData

    fun isFinalPage(page: Int): Boolean
}