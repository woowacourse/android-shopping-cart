package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.CartDao
import woowacourse.shopping.data.util.toDomain
import woowacourse.shopping.domain.CartItems
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val cartDao: CartDao,
) : CartRepository {
    override suspend fun addItem(
        productId: String,
        amount: Int,
    ) {
        require(amount > 0) { "상품 추가 개수는 양수여야 합니다." }

        cartDao.addItem(productId, amount)
    }

    override suspend fun deleteItem(productId: String) {
        cartDao.deleteItem(productId)
    }

    override suspend fun minusItemAmount(productId: String) {
        cartDao.minusItem(productId, 1)
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
