package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.ProductData
import woowacourse.shopping.domain.model.Product

interface ShoppingProductsRepository {
    fun loadPagedItems(page: Int): List<ProductData>

    fun findById(findId: Int): ProductData

    fun isFinalPage(page: Int): Boolean
}

interface ShoppingProductsRepository2 {
    fun loadPagedItems(page: Int): List<Product>

    fun findById(id: Int): Product

    fun isFinalPage(page: Int): Boolean
}
