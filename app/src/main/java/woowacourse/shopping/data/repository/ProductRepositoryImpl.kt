package woowacourse.shopping.data.repository

import woowacourse.shopping.data.source.ProductDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    productDataSource: ProductDataSource,
) : ProductRepository {
    private val products = productDataSource.products
    override val productSize get() = products.size

    override fun getProductById(id: String): Product =
        products.firstOrNull { it.id == id }
            ?: throw IllegalArgumentException("존재하지 않는 상품입니다. 삐용삐용")

    override suspend fun getProducts(
        startIndex: Int,
        count: Int,
    ): List<Product> {
        require(startIndex in 0..productSize) { "시작 인덱스가 올바르지 않습니다." }

        val endIndex = minOf(startIndex + count, productSize)

        return products.subList(startIndex, endIndex)
    }
}
