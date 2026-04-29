package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.dto.request.CartItemRequestDto
import woowacourse.shopping.data.dto.response.CartItemResponseDto

interface CartDataSource {
    fun getCartItems(): List<CartItemResponseDto>

    fun saveCartItems(cartItems: List<CartItemRequestDto>)
}
