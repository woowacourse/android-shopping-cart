package woowacourse.shopping.data.cart

import woowacourse.shopping.data.cart.entity.CartItem
import woowacourse.shopping.model.Quantity
import java.lang.IllegalArgumentException
import kotlin.concurrent.Volatile

interface CartRepository {
    fun increaseQuantity(productId: Long)

    fun decreaseQuantity(productId: Long)

    fun changeQuantity(
        productId: Long,
        quantity: Quantity,
    )

    fun deleteCartItem(productId: Long)

    fun find(productId: Long): CartItem

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun totalCartItemCount(): Int

    companion object {
        private const val NOT_INITIALIZE_INSTANCE_MESSAGE = "초기화된 인스턴스가 없습니다."

        @Volatile
        private var instance: CartRepository? = null

        fun setInstance(cartRepository: CartRepository) {
            synchronized(this) {
                instance = cartRepository
            }
        }

        fun getInstance(): CartRepository {
            return instance ?: throw IllegalArgumentException(NOT_INITIALIZE_INSTANCE_MESSAGE)
        }
    }
}
