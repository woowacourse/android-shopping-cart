package woowacourse.shopping.data.repository

import woowacourse.shopping.data.api.ProductApi
import woowacourse.shopping.data.dao.ProductDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.CatalogProduct
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepository(
    private val dao: ProductDao,
    private val api: ProductApi,
) : ProductRepository {
    override fun fetchCatalogProduct(productId: Int): CatalogProduct? = dao.getProduct(productId)?.toDomain()

    override fun fetchCatalogProducts(productIds: List<Int>): List<CatalogProduct> = dao.getProducts(productIds).map { it.toDomain() }

    override fun fetchProducts(
        lastId: Int,
        count: Int,
    ): List<CatalogProduct> =
        api.getProducts(lastId, count).execute().body()?.map {
            it.toDomain()
        } ?: emptyList()

    override fun hasMoreProducts(lastId: Int): Boolean = dao.getMaxId() > lastId
}
