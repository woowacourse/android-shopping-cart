package woowacourse.shopping.data.remote

import woowacourse.shopping.data.datasource.ProductDataSource
import woowacourse.shopping.data.mapper.toProductEntity
import woowacourse.shopping.data.model.ProductEntity
import woowacourse.shopping.domain.model.ProductWithQuantity

class ProductRemoteDataSource(private val productApiService: ProductApiService) :
    ProductDataSource {
    override fun insertProducts(products: List<ProductEntity>) {
        // Do nothing
    }

    override fun productWithQuantityItem(productId: Long): Result<ProductWithQuantity> {
        return runCatching {
            val product = productApiService.loadById(productId)
            ProductWithQuantity(product.toProductEntity(), 1)
                ?: throw Exception("Product not found")
        }
    }

    override fun findProductWithQuantityItemsByPage(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductWithQuantity>> {
        return runCatching {
            productApiService.load(page, pageSize).map { ProductWithQuantity(it.toProductEntity(), 1) }
        }
    }
}
