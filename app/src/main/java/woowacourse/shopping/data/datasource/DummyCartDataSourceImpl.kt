package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.dto.request.CartItemRequestDto
import woowacourse.shopping.data.dto.response.CartItemResponseDto

object DummyCartDataSourceImpl : CartDataSource {
    // 키: productId, 값: quantity
    private val cartItems = mutableMapOf<String, Int>()

    override fun getCartItems(): List<CartItemResponseDto> {
        return cartItems.mapNotNull { (productId, quantity) ->
            val productEntity = DummyProductDataSourceImpl.products.find { it.id == productId }

            if (productEntity != null) {
                CartItemResponseDto(
                    productId = productEntity.id,
                    imageUrl = productEntity.imageUrl,
                    name = productEntity.name,
                    price = productEntity.price,
                    quantity = quantity
                )
            } else {
                null
            }
        }
    }

    override fun saveCartItems(cartItems: List<CartItemRequestDto>) {
        this.cartItems.clear()
        cartItems.forEach { dto ->
            this.cartItems[dto.productId] = dto.quantity
        }
    }
}
