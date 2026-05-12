package woowacourse.shopping.data.repository.product

import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.remote.api.ProductService

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.product.Products
import woowacourse.shopping.domain.repository.ProductRepository
import java.io.IOException

class RemoteProductRepository(
    private val productService: ProductService,
) : ProductRepository {

    override suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> = fetchAllProducts().getPage(page, pageSize)

    override suspend fun getProduct(id: String): Product? =
        productService.getProduct(id)?.toDomain()

    private suspend fun fetchAllProducts(): Products =
        Products(
            productService.getProducts().map{it.toDomain()}
        )
}
