package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.model.CartItem
import kotlin.math.min

// TODO 삭제 시 발생할 수 있는 예외 처리 추가
object DefaultCart : CartDataSource {
    private val cartItems: MutableList<CartItem> = mutableListOf()
    private var id: Long = 1

    override fun addCartItem(
        productId: Long,
        quantity: Int,
    ): Long {
        cartItems.add(
            CartItem(
                id = id,
                productId = productId,
                quantity = quantity,
            ),
        )
        return id++
    }

    override fun deleteCartItem(cartItemId: Long): Long {
        cartItems.removeIf { it.id == cartItemId }
        return cartItemId
    }

    override fun getCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, cartItems.size)

        if (fromIndex > toIndex) return emptyList()

        return cartItems.subList(fromIndex, toIndex)
    }
}
