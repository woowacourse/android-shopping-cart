package woowacourse.shopping.data.repository

import woowacourse.shopping.data.remote.datasource.ProductRemoteDataSource
import woowacourse.shopping.data.remote.mapper.toDomain
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.Products
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

class ProductRepositoryImpl(
    private val productRemoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    override suspend fun getProducts(): Products =
        Products(
            productRemoteDataSource
                .getProducts()
                .map { it.toDomain() },
        )

    override suspend fun getPagingProducts(
        page: Int,
        pageSize: Int,
    ): Products {
        if (page < 0 || pageSize <= 0) return Products()

        val products = getProducts().productItems
        val fromIndex = page * pageSize

        if (fromIndex >= products.size) {
            return Products()
        }

        val toIndex = min(fromIndex + pageSize, products.size)
        return Products(products.subList(fromIndex, toIndex))
    }

    override suspend fun hasNextPage(
        currentPage: Int,
        pageSize: Int,
    ): Boolean {
        val products = getProducts().productItems
        val nextPageStartIndex = (currentPage + 1) * pageSize
        return nextPageStartIndex < products.size
    }

    override suspend fun findProductById(productId: Int): Product? =
        productRemoteDataSource
            .getProduct(productId)
            .toDomain()
}
