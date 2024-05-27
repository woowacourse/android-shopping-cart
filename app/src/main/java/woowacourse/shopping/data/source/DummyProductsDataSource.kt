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
                    "https://s3-alpha-sig.figma.com/img/05ef/e578/d81445480aff1872344a6b1b35323488?Expires=1717977600&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=jjZ9gCGElFMx8druqQBDkJzs4DH63phHkPxed4C9L3zVCoTV7XpxN58haKoLSFn3QIplsaREj2dUxlfCtym-x5edhFH078DeazrunG99WoKeYnuu2xmxDdSoJ7bckyLltypAUxYF0HQhRobKtSnIuWUQpHpu27lYSuTxsmWmmTrmg1waiPMnZHwaMgFU71Cb54OGn1SvK3Q1dasdhwsELC0wXdqb81wjFQ8fjjiYgfMJ4makKT3Ia6rAC1VF5dnRbHsL1FpGni3RrQ6nxMYjCzp7LVDaa5PCm8g9rGgEGm-AbMwXdenh7ZbZe3W2mbhfmve1V9RcHwSoXqAwD16zWQ__",
                    "$i 번째 상품 이름",
                    i * 100,
                )
            }

        private const val COUNT_PER_LOAD = 20
    }
}
