package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun fetchProducts(
        count: Int,
        lastId: Int,
    ): List<Product>

    fun fetchProductDetail(id: Int): Product?

    fun fetchIsProductsLoadable(lastId: Int): Boolean
}
