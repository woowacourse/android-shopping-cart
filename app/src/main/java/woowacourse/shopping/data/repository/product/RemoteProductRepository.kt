package woowacourse.shopping.data.repository.product

import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.remote.api.ProductService
import woowacourse.shopping.data.remote.dto.toProductResponseDto
import woowacourse.shopping.data.remote.dto.toProductResponseDtos
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
        try {
            productService
                .getProduct(id)
                .toProductResponseDto()
                .toDomain()
        } catch (exception: IOException) {
            if (exception.message?.startsWith("HTTP 404") == true) {
                null
            } else {
                throw exception
            }
        }

    private suspend fun fetchAllProducts(): Products =
        Products(
            productService
                .getProducts()
                .toProductResponseDtos()
                .map { it.toDomain() },
        )
}
