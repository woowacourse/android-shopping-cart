package woowacourse.shopping.data.source.remote

import woowacourse.shopping.domain.product.model.Product
import woowacourse.shopping.domain.product.repository.ProductRepository

class ProductRepositoryRemoteImpl(
    private val remoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    private var cachedProducts: List<Product>? = null

    private suspend fun fetchAllProducts(): List<Product> =
        cachedProducts ?: remoteDataSource
            .getProducts()
            .map { it.toDomain() }
            .also { cachedProducts = it }

    override suspend fun getProductsSize(): Int = fetchAllProducts().size

    override suspend fun getProduct(id: String): Product =
        fetchAllProducts().find { it.id == id }
            ?: throw NoSuchElementException("상품을 찾을 수 없습니다: $id")

    override suspend fun isProductExist(productId: String): Boolean = fetchAllProducts().any { it.id == productId }

    override suspend fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val allProducts = fetchAllProducts()
        val fromIndex = page * pageSize

        if (fromIndex >= allProducts.size) return emptyList()

        return allProducts.drop(fromIndex).take(pageSize)
    }
}
