package woowacourse.shopping.data.repsoitory

import woowacourse.shopping.data.datasource.local.ShoppingCartDataSource
import woowacourse.shopping.data.datasource.remote.ProductDataSource
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource,
    private val shoppingCartDataSource: ShoppingCartDataSource,
) : ProductRepository {
    override fun findProductById(id: Long): Result<Product> =
        productDataSource.findProductById(id).mapCatching { productDto ->
            val carProduct = shoppingCartDataSource.findCartProduct(id).getOrNull()
            carProduct?.toDomain() ?: productDto.copy(quantity = INIT_QUANTITY).toDomain()
        }

    override fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        productDataSource.getPagingProduct(page, pageSize)
            .mapCatching { result -> result.map { it.toDomain() } }

    override fun shutdown(): Result<Unit> = productDataSource.shutdown()

    companion object {
        const val INIT_QUANTITY = 1
    }
}
