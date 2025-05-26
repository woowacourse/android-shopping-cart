package woowacourse.shopping.data

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.fixture.productFixture1

class FakeProductRepository : ProductRepository {
    private val products = mutableListOf(
        Product(1L, "맥북", Price(1000), ""),
        Product(2L, "아이폰", Price(2000), ""),
        Product(3L, "에어팟", Price(3000), ""),
        Product(4L, "매직키보드", Price(4000), ""),
        Product(5L, "에어팟맥스", Price(5000), ""),
        Product(6L, "에어팟깁스", Price(6000), ""),
    )


    fun setProducts(newList: List<Product>) {
        products.clear()
        products.addAll(newList)
    }

    override fun getById(id: Long): Product = products.find { it.id == id }!!

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
