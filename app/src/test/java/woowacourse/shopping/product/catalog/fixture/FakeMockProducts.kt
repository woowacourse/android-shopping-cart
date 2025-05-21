package woowacourse.shopping.product.catalog.fixture

import woowacourse.shopping.data.ProductsDataSource
import woowacourse.shopping.product.catalog.ProductUiModel

class FakeMockProducts(
    private val size: Int,
) : ProductsDataSource {
    private val mockProducts: List<ProductUiModel> = generateMockProducts(size)

    override fun getProducts(): List<ProductUiModel> = mockProducts

    override fun getSubListedProducts(
        startIndex: Int,
        lastIndex: Int,
    ): List<ProductUiModel> = mockProducts.subList(startIndex, lastIndex.coerceAtMost(mockProducts.size))

    override fun getProductsSize(): Int = mockProducts.size

    private fun generateMockProducts(count: Int): List<ProductUiModel> =
        (1..count).map {
            ProductUiModel(
                name = "상품$it",
                imageUrl = "https://example.com/image$it.jpg",
                price = it * 1000,
            )
        }
}
