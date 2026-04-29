package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.CartDataSource
import woowacourse.shopping.data.dto.request.toRequestDto
import woowacourse.shopping.data.dto.response.toDomainModel
import woowacourse.shopping.domain.model.cart.CartItems
import woowacourse.shopping.domain.repository.CartRepository

class CartRepositoryImpl(
    private val dataSource: CartDataSource
): CartRepository {
    override fun getCartItems(): CartItems {
        val result = dataSource.getCartItems()
        val cartItems = result.map { it.toDomainModel() }
        return CartItems(cartItems)
    }

    override fun saveCartItems(cartItems: CartItems) {
        val cartItemRequestDtos = cartItems.items.map { it.toRequestDto() }
        dataSource.saveCartItems(cartItemRequestDtos)
    }
}
