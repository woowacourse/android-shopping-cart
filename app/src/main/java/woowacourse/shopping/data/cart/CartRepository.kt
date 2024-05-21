package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import java.lang.IllegalArgumentException
import kotlin.concurrent.Volatile

interface CartRepository {
    fun increaseQuantity(product: Product)

    fun decreaseQuantity(product: Product)

    fun changeQuantity(
        product: Product,
        quantity: Quantity,
    )

    fun deleteCartItem(cartItem: CartItem)

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun totalProductCount(): Int

    fun totalQuantityCount(): Int

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
