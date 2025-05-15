package woowacourse.shopping.data

import woowacourse.shopping.domain.Price
import woowacourse.shopping.domain.Product

@Suppress("ktlint:standard:max-line-length")
class FakeCartStorage : CartStorage {
    private val cart: MutableSet<Product> =
        mutableSetOf(
            Product(
                1L,
                "마리오 그린올리브 300g",
                Price(3980),
                "https://images.emarteveryday.co.kr/images/app/webapps/evd_web2/share/SKU/mall/27/41/8412707034127_1.png",
            ),
        )

    override fun insert(item: Product) {
        cart.add(item)
    }

    override fun getAll(): List<Product> = cart.map { it.copy() }

    override fun deleteProduct(id: Long) {
        cart.removeIf { it.id == id }
    }

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = minOf(fromIndex + pageSize, cart.size)
        if (fromIndex < 0) return emptyList()
        return cart.toList().subList(fromIndex, toIndex)
    }

    override fun notHasNextPage(
        page: Int,
        pageSize: Int,
    ): Boolean {
        val fromIndex = page * pageSize
        return fromIndex >= cart.size
    }
}
