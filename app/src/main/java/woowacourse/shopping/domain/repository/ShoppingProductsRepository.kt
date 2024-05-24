package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.ProductData

interface ShoppingProductsRepository {
    fun loadPagedItems(page: Int): List<ProductData>

    fun findById(findId: Int): ProductData

    fun isFinalPage(page: Int): Boolean
}
