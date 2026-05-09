package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.data.source.ProductDataSourceImpl
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.math.min

class ProductRepositoryImpl(
    productDataSource: ProductDataSource = ProductDataSourceImpl,
) : ProductRepository {
    private val products = productDataSource.products

    override fun getProductById(id: String): Product =
        products.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다. 삐용삐용")

    override fun getProductsByIds(ids: List<String>): List<Product> = products.filter { ids.contains(it.id) }

    override suspend fun getProducts(offset: Int): Products {
        val toIdx = min(offset + PAGE_SIZE, products.size)
        val hasNext = offset + PAGE_SIZE < products.size

        return Products(products.subList(offset, toIdx), hasNext = hasNext)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
