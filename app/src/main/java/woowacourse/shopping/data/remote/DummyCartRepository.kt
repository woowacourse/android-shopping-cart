package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.domain.Product
import kotlin.math.min

object DummyCartRepository : CartRepository {
    private val cartMap: MutableMap<Product, Int> = mutableMapOf()

    override fun addData(product: Product): Result<Long> =
        runCatching {
            cartMap[product] = (cartMap[product] ?: 0) + 1
            product.id
        }

    override fun delete(product: Product): Result<Long> =
        runCatching {
            cartMap.remove(product) ?: throw NoSuchElementException()
            product.id
        }

    override fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Cart>> =
        runCatching {
            val carts = cartMap.map { Cart(it.key, it.value) }.toList()
            val startIndex = startPage * pageSize
            val endIndex = min(startIndex + pageSize, carts.size)
            carts.subList(startIndex, endIndex)
        }

    override fun getMaxPage(pageSize: Int): Result<Int> =
        runCatching {
            ((cartMap.size + (pageSize - 1)) / pageSize - 1).coerceAtLeast(0)
        }
}
