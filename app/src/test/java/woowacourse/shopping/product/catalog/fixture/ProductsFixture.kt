package woowacourse.shopping.product.catalog.fixture

import woowacourse.shopping.data.ProductsDataSource
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.Product
import woowacourse.shopping.product.catalog.ProductUiModel

object ProductsFixture : ProductsDataSource {
    override fun getAllProductsSize(): Int = mockProducts.size

    override fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
    ): List<ProductUiModel> = mockProducts.subList(startIndex, endIndex).map { it.toUiModel() }

    var count = 1

    val mockProducts =
        listOf(
            Product(
                name = "${count++}아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 10000,
            ),
        )
}
