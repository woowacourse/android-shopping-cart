package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductDataSourceImpl
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

class ProductRepositoryImpl(
    productDataSource: ProductDataSource = ProductDataSourceImpl,
) : ProductRepository {
    private val products = productDataSource.products
    private var offset = 0
    override val hasNext get() = offset < products.size

    override fun getProductById(id: String): Product =
        products.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다. 삐용삐용")

    override suspend fun getProducts(): List<Product> {
        val fromIndex = offset
        offset = min(offset + PAGE_SIZE, products.size)
        return products.subList(fromIndex, offset)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
