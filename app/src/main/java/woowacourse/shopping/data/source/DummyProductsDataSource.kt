package woowacourse.shopping.data.source

import woowacourse.shopping.NumberPagingStrategy
import woowacourse.shopping.data.model.ProductData

class DummyProductsDataSource(
    private val pagingStrategy: NumberPagingStrategy<ProductData> = NumberPagingStrategy(COUNT_PER_LOAD),
) :
    ProductDataSource {
    override fun findByPaged(page: Int): List<ProductData> = pagingStrategy.loadPagedData(page, allProducts)

    override fun findById(id: Long): ProductData =
        allProducts.find { it.id == id }
            ?: throw NoSuchElementException("there is no product with id: $id")

    override fun isFinalPage(page: Int): Boolean = pagingStrategy.isFinalPage(page, allProducts)

    override fun shutDown(): Boolean {
        println("shutDown")
        return true
    }

    companion object {
        private val allProducts =
            List(60) { i ->
                ProductData(
                    i.toLong(),
                    "https://zrr.kr/dw6j",
                    "$i 번째 상품 이름",
                    i * 100,
                )
            }

        private const val COUNT_PER_LOAD = 20
    }
}
