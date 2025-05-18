package woowacourse.shopping.data

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.mapper.toCartItem
import woowacourse.shopping.data.mapper.toProductEntity
import woowacourse.shopping.data.util.runCatchingInThread
import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override fun getCartItems(
        limit: Int,
        offset: Int,
        onResult: (Result<PageableItem<CartItem>>) -> Unit,
    ) {
        runCatchingInThread(
            block = {
                val products = cartDao.getCartItemPaged(limit, offset)
                PageableItem(products.map { it.toCartItem() }, products.isHasMore())
            },
            callback = onResult,
        )
    }

    override fun deleteCartItem(
        id: Long,
        onResult: (Result<Long>) -> Unit,
    ) {
        runCatchingInThread(
            block = { cartDao.delete(id).let { id } },
            callback = onResult,
        )
    }

    override fun addCartItem(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatchingInThread(
            block = { cartDao.insertOrUpdate(product.toProductEntity()) },
            callback = onResult,
        )
    }

    private fun List<CartEntity>.isHasMore(): Boolean {
        val lastCreatedAt = this.lastOrNull()?.createdAt
        return lastCreatedAt != null && cartDao.existsItemCreatedBefore(lastCreatedAt)
    }
}
