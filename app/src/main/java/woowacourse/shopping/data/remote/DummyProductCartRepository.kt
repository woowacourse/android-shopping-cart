package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.ProductCartRepository
import woowacourse.shopping.domain.Product
import woowacourse.shopping.presentation.ui.cart.CartViewModel
import kotlin.math.min

object DummyProductCartRepository : ProductCartRepository {
    private val cartMap: MutableMap<Product, Int> = mutableMapOf()
    override fun save(product: Product): Result<Long> =
        runCatching {
            cartMap[product] = (cartMap[product] ?: 0) + 1
            product.id
        }

    override fun delete(product: Product): Result<Long> =
        runCatching {
            cartMap.remove(product)
            product.id
        }

    override fun deleteAll(): Result<Boolean> =
        runCatching {
            cartMap.clear()
            true
        }

    override fun findByPaging(
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
            ((cartMap.size + CartViewModel.PAGE_UPPER_BOUND) / CartViewModel.PAGE_SIZE - 1).coerceAtLeast(0)
        }
}
