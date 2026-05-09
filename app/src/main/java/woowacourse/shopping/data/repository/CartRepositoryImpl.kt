package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.local.CartEntity
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.CartItems
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override suspend fun addItem(
        productId: String,
        amount: Int,
    ) {
        val item = cartDao.getCartItem(productId)

        if (item == null) {
            cartDao.insert(CartEntity(productId, amount))
            return
        }

        cartDao.update(CartEntity(productId, item.amount + amount))
    }

    override suspend fun deleteItem(productId: String) {
        cartDao.deleteItem(productId)
    }

    override suspend fun minusItemAmount(productId: String) {
        val item = cartDao.getCartItem(productId) ?: return

        if (item.amount - 1 <= 0) {
            deleteItem(productId)
            return
        }
        cartDao.update(CartEntity(productId, item.amount - 1))
    }

    override suspend fun getCartItemByPage(page: Int): CartItems {
        require(page > 0) { "-거절(사유: ${page}pg가 말이 되는가)-" }

        val offset = (page - 1) * PAGE_SIZE

        val result = cartDao.getCartItems(PAGE_SIZE + 1, offset)

        return result.take(PAGE_SIZE).toDomain(result.size <= PAGE_SIZE)
    }

    override fun getAllCartItems(): Flow<CartItems> =
        cartDao.getAllCartItems().map { result ->
            CartItems(
                items = result.map { it.toDomain() },
                isLast = true,
            )
        }

    companion object {
        private const val PAGE_SIZE = 5
    }
}

fun CartEntity.toDomain(): CartItem =
    CartItem(
        productId = productId,
        amount = amount,
    )

fun List<CartEntity>.toDomain(isLast: Boolean): CartItems =
    CartItems(
        items = map { it.toDomain() },
        isLast = isLast,
    )
