package woowacourse.shopping.repository

import woowacourse.shopping.db.Product

class DummyProductStore : ProductStore {
    private val products = List(10000) { i ->
        Product(i, "상품$i", i.toString(), i * 100)
    }

    override fun loadData(currentIndex: Int): List<Product> {
        return products.subList(currentIndex, currentIndex + COUNT_PER_LOAD)
    }

    companion object {
        private const val COUNT_PER_LOAD = 20
    }
}
