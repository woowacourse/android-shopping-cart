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

    override suspend fun getCartItemByPage(page: Int, pageSize: Int): CartItems {
        require(page > 0) { "-거절(사유: ${page}pg가 말이 되는가)-" }

        val offset = (page - 1) * pageSize

        val result = cartDao.getCartItems(pageSize + 1, offset)

        return result.take(pageSize).toDomain(result.size <= pageSize)
    }

    override fun getAllCartItems(): Flow<CartItems> =
        cartDao.getAllCartItems().map { result ->
            CartItems(
                items = result.map { it.toDomain() },
                isLast = true,
            )
        }
}
