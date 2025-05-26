package woowacourse.shopping.fixture

import woowacourse.shopping.data.product.ProductsDataSource
import woowacourse.shopping.product.catalog.ProductUiModel

class FakeCatalogItemRepository(
    private val size: Int,
) : ProductsDataSource {
    private val fakeProducts: List<ProductUiModel> =
        List(size) { index ->
            ProductUiModel(
                id = (index + 1).toLong(),
                name = "${index + 1} 아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 1000 * (index + 1),
            )
        }

    override fun getProducts(): List<ProductUiModel> = fakeProducts

    override fun getSubListedProducts(
        startIndex: Int,
        lastIndex: Int,
    ): List<ProductUiModel> =
        fakeProducts.subList(
            startIndex.coerceAtLeast(0),
            lastIndex.coerceAtMost(fakeProducts.size),
        )

    override fun getProductsSize(): Int = fakeProducts.size
}
