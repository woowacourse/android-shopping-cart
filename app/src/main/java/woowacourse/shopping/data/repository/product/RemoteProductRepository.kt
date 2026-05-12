package woowacourse.shopping.data.repository.product

import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.remote.api.ProductApi

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.Products
import woowacourse.shopping.domain.repository.ProductRepository

class RemoteProductRepository(
    private val productApi: ProductApi,
) : ProductRepository {

    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> = fetchAllProducts().getPage(page, pageSize)

    override suspend fun getProduct(id: String): Product? =
        productApi.getProduct(id)?.toDomain()

    private suspend fun fetchAllProducts(): Products =
        Products(
            productApi.getProducts().map{it.toDomain()}
        )
}
