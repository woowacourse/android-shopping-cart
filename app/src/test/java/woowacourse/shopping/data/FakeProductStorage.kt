package woowacourse.shopping.data

import woowacourse.shopping.domain.Product
import woowacourse.shopping.fixture.productFixture1

class FakeProductStorage : ProductStorage {
    private val products = mutableListOf<Product>()

    fun setProducts(newList: List<Product>) {
        products.clear()
        products.addAll(newList)
    }

    override fun get(id: Long): Product = productFixture1

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val from = page * pageSize
        val to = minOf(from + pageSize, products.size)
        if (from >= products.size) return emptyList()
        return products.subList(from, to)
    }

    override fun notHasMoreProduct(
        page: Int,
        pageSize: Int,
    ): Boolean {
        val from = page * pageSize
        return from >= products.size
    }
}
