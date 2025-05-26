package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CatalogProduct

interface ProductRepository {
    fun fetchCatalogProduct(productId: Int): CatalogProduct?

    fun fetchCatalogProducts(productIds: List<Int>): List<CatalogProduct>

    fun fetchProducts(
        lastId: Int,
        count: Int,
    ): List<CatalogProduct>

    fun hasMoreProducts(lastId: Int): Boolean
}
