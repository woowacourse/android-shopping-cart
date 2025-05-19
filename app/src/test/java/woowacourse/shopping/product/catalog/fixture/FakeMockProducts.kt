package woowacourse.shopping.product.catalog.fixture

import woowacourse.shopping.data.ProductsDataSource
import woowacourse.shopping.mapper.toUiModel
import woowacourse.shopping.product.catalog.Product
import woowacourse.shopping.product.catalog.ProductUiModel

class FakeMockProducts(
    private val size: Int,
) : ProductsDataSource {
    override fun getProducts(): List<ProductUiModel> = List(size) { mockProducts.map { it.toUiModel() } }.flatten()

    override fun getSubListedProducts(
        startIndex: Int,
        lastIndex: Int,
    ): List<ProductUiModel> = emptyList()

    override fun getProductsSize(): Int = 1

    val mockProducts =
        listOf(
            Product(
                name = "아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 카라멜 마키아또",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110582]_20210415142706229.jpg",
                price = 10000,
            ),
            Product(
                name = "아이스 카푸치노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110601]_20210415143400922.jpg",
                price = 10000,
            ),
            Product(
                name = "딸기 콜드폼 딸기 라떼",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003659]_20210428134252286.jpg",
                price = 10000,
            ),
            Product(
                name = "바닐라 크림 콜드 브루",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000000487]_20210430112319174.jpg",
                price = 10000,
            ),
        )
}
