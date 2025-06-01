package woowacourse.shopping.fixture

import woowacourse.shopping.data.recent.ViewedItemRepository
import woowacourse.shopping.product.catalog.ProductUiModel

class FakeViewedItemRepository(
    private val size: Int,
) : ViewedItemRepository {
    private val viewed =
        MutableList(size) { index ->
            ProductUiModel(
                id = (index + 1).toLong(),
                name = "${index + 1} 아이스 카페 아메리카노",
                imageUrl = "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg",
                price = 1000 * (index + 1),
            )
        }

    override fun insertViewedItem(
        product: ProductUiModel,
        onComplete: () -> Unit,
    ) {
        viewed.add(0, product)
        onComplete()
    }

    override fun getViewedItems(callback: (List<ProductUiModel>) -> Unit) {
        callback(viewed.toList().take(10))
    }

    override fun getLastViewedItem(callback: (ProductUiModel?) -> Unit) {
        callback(viewed.firstOrNull())
    }
}
