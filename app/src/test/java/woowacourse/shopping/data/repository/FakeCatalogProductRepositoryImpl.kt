package woowacourse.shopping.data.repository

import woowacourse.shopping.product.catalog.ProductUiModel

class FakeCatalogProductRepositoryImpl(
    size: Int,
) : CatalogProductRepository {
    override fun getAllProductsSize(callback: (Int) -> Unit) {
        callback(dummyProducts.size)
    }

    override fun getProductsInRange(
        startIndex: Int,
        endIndex: Int,
        callback: (List<ProductUiModel>) -> Unit,
    ) {
        callback(dummyProducts.subList(startIndex, endIndex))
    }

    override fun getCartProductsByUids(
        uids: List<Int>,
        callback: (List<ProductUiModel>) -> Unit,
    ) {
        callback(
            uids.mapNotNull { uid ->
                dummyProducts.find { product -> product.id == uid }
            },
        )
    }

    val dummyProducts =
        MutableList(size) {
            ProductUiModel(
                id = size,
                name = "아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 10000,
            )
        }
}
