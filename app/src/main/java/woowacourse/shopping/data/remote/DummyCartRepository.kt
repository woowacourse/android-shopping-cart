package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartRepository
import woowacourse.shopping.presentation.ui.Product
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
            cartMap.remove(product)
            product.id
        }

    override fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Cart>> =
        runCatching {
            val carts = cartMap.map { Cart(it.key, it.value) }.toList()
            val startIndex = pageOffset * pageSize
            val endIndex = min(startIndex + pageSize, carts.size)
            carts.subList(startIndex, endIndex)
        }

    override fun getMaxOffset(): Result<Int> =
        runCatching {
            (cartMap.size + 4) / 5 - 1
        }
}
