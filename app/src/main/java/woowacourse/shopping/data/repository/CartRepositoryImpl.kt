package woowacourse.shopping.data.repository

import woowacourse.shopping.data.storage.CartStorage
import woowacourse.shopping.domain.cart.Cart
import woowacourse.shopping.domain.cart.CartResult
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(private val storage: CartStorage) : CartRepository {
    override fun insert(productId: Long) = storage.insert(Cart(productId = productId))

    override fun get(id: Long): Cart = storage[id]

    override fun delete(id: Long) = storage.delete(id)

    override fun loadSinglePage(
        page: Int,
        pageSize: Int,
    ): CartResult {
        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize
        val result = storage.singlePage(fromIndex, toIndex)

        return result
    }
}
