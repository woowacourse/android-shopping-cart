package woowacourse.shopping.data.remote

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartRepository

object DummyCartRepository : CartRepository {
    private val carts: MutableList<Cart> = mutableListOf()

    override fun addData(productId: Long): Result<Long> =
        runCatching {
            carts.add(Cart(productId, 1))
//            if (carts.any { it.productId == productId }) {
//                carts.add(Cart(productId, 1))
//            }
            productId
        }

    override fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Cart>> =
        runCatching {
            val startIndex = pageOffset * pageSize
            val endIndex =
                if (carts.size < startIndex * pageSize) startIndex * pageSize else carts.size
            carts.subList(startIndex, endIndex)
        }
}
